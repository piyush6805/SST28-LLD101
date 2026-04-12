import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class DistributedCache<K, V> {

    private final List<CacheNode<K, V>> nodes;
    private final LoadBalancer<K> loadBalancer;
    private final DataStore<K, V> database;
    private final RetryPolicy retryPolicy;
    private final ConcurrentHashMap<K, Object> keyLocks;
    private volatile boolean requestCollapsingEnabled;

    public DistributedCache(List<CacheNode<K, V>> nodes,
                            DistributionStrategy<K> strategy,
                            DataStore<K, V> database) {
        this(nodes, strategy, database, new RetryPolicy(2));
    }

    public DistributedCache(List<CacheNode<K, V>> nodes,
                            DistributionStrategy<K> strategy,
                            DataStore<K, V> database,
                            RetryPolicy retryPolicy) {
        if (nodes == null || nodes.isEmpty()) {
            throw new IllegalArgumentException("At least one cache node is required");
        }
        this.nodes = nodes;
        this.loadBalancer = new LoadBalancer<>(strategy);
        this.database = database;
        this.retryPolicy = retryPolicy;
        this.keyLocks = new ConcurrentHashMap<>();
        this.requestCollapsingEnabled = false;
    }

    public V get(K key) {
        CacheNode<K, V> node = routeToNode(key);
        V value = readNodeWithRetry(node, key);
        if (value != null) {
            return value;
        }

        if (!requestCollapsingEnabled) {
            return loadFromStoreAndFillNode(key, node);
        }

        Object lock = keyLocks.computeIfAbsent(key, ignored -> new Object());
        synchronized (lock) {
            V secondTry = readNodeWithRetry(node, key);
            if (secondTry != null) {
                return secondTry;
            }
            return loadFromStoreAndFillNode(key, node);
        }
    }

    public void put(K key, V value) {
        CacheNode<K, V> node = routeToNode(key);
        writeNodeWithRetry(node, key, value);
        database.put(key, value);
    }

    public void prefetch(Collection<K> keys) {
        for (K key : keys) {
            get(key);
        }
    }

    public void setRequestCollapsingEnabled(boolean enabled) {
        this.requestCollapsingEnabled = enabled;
    }

    public int getNodeCount() {
        return nodes.size();
    }

    public void setNodeAvailability(int nodeIndex, boolean available) {
        nodes.get(nodeIndex).setAvailable(available);
    }

    public void resizeNodes(int newCount,
                            int capacityPerNode,
                            Supplier<EvictionPolicy<K>> evictionPolicySupplier) {
        if (newCount <= 0) {
            throw new IllegalArgumentException("Node count must be greater than zero");
        }

        List<CacheNode<K, V>> rebuilt = new ArrayList<>();
        for (int i = 0; i < newCount; i++) {
            rebuilt.add(new CacheNode<>("node-" + i, capacityPerNode, evictionPolicySupplier.get()));
        }

        nodes.clear();
        nodes.addAll(rebuilt);

        if (database instanceof ScanCapableDataStore<K, V> scanStore) {
            for (Map.Entry<K, V> entry : scanStore.entries().entrySet()) {
                CacheNode<K, V> node = routeToNode(entry.getKey());
                node.put(entry.getKey(), entry.getValue());
            }
        }
    }

    private CacheNode<K, V> routeToNode(K key) {
        int index = loadBalancer.pickNodeIndex(key, nodes.size());
        return nodes.get(index);
    }

    private V loadFromStoreAndFillNode(K key, CacheNode<K, V> node) {
        V value = database.get(key);
        if (value != null) {
            writeNodeWithRetry(node, key, value);
        }
        return value;
    }

    private V readNodeWithRetry(CacheNode<K, V> node, K key) {
        int attempts = 0;
        while (true) {
            try {
                return node.get(key);
            } catch (NodeUnavailableException ex) {
                if (!retryPolicy.shouldRetry(attempts++)) {
                    return null;
                }
            }
        }
    }

    private void writeNodeWithRetry(CacheNode<K, V> node, K key, V value) {
        int attempts = 0;
        while (true) {
            try {
                node.put(key, value);
                return;
            } catch (NodeUnavailableException ex) {
                if (!retryPolicy.shouldRetry(attempts++)) {
                    return;
                }
            }
        }
    }
}