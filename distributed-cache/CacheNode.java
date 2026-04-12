import java.util.HashMap;
import java.util.Map;

public class CacheNode<K, V> {

    private final String nodeId;
    private int capacity;
    private final Map<K, V> map;
    private EvictionPolicy<K> evictionPolicy;
    private volatile boolean available = true;

    public CacheNode(String nodeId, int capacity, EvictionPolicy<K> evictionPolicy) {
        this.nodeId = nodeId;
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.evictionPolicy = evictionPolicy;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setEvictionPolicy(EvictionPolicy<K> evictionPolicy) {
        this.evictionPolicy = evictionPolicy;
    }

    public int size() {
        synchronized (map) {
            return map.size();
        }
    }

    public V get(K key) {
        ensureAvailable();

        synchronized (map) {
            if (!map.containsKey(key)) {
                return null;
            }

            evictionPolicy.keyAccessed(key);
            return map.get(key);
        }
    }

    public void put(K key, V value) {
        ensureAvailable();

        synchronized (map) {

            if (map.containsKey(key)) {
                map.put(key, value);
                evictionPolicy.keyAccessed(key);
                return;
            }

            if (map.size() >= capacity) {
                K evict = evictionPolicy.evictKey();
                if (evict != null) {
                    map.remove(evict);
                }
            }

            map.put(key, value);
            evictionPolicy.keyAccessed(key);
        }
    }

    private void ensureAvailable() {
        if (!available) {
            throw new NodeUnavailableException("Node unavailable: " + nodeId);
        }
    }
}