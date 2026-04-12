import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int nodeCount = 3;
        int capacityPerNode = 2;

        List<CacheNode<String, String>> nodes = buildNodes(nodeCount, capacityPerNode);
        DistributedCache<String, String> cache = new DistributedCache<>(
                nodes,
                new ModuloDistributionStrategy<>(),
                new InMemoryDatabase<>(),
                new RetryPolicy(2)
        );
        cache.setRequestCollapsingEnabled(true);

        cache.put("A", "Apple");
        cache.put("B", "Ball");
        cache.put("C", "Cat");
        cache.put("D", "Dog");

        System.out.println("get(A): " + cache.get("A"));
        System.out.println("get(C): " + cache.get("C"));
        System.out.println("get(X): " + cache.get("X"));

        cache.prefetch(List.of("A", "B"));

        cache.setNodeAvailability(1, false);
        System.out.println("node-1 down, get(B): " + cache.get("B"));
        cache.setNodeAvailability(1, true);

        cache.resizeNodes(4, capacityPerNode, LRUEvictionPolicy::new);
        System.out.println("resized node count: " + cache.getNodeCount());
        System.out.println("after resize get(D): " + cache.get("D"));

        cache.resizeNodes(2, capacityPerNode, FIFOEvictionPolicy::new);
        System.out.println("switched policy and resized to nodes: " + cache.getNodeCount());
        System.out.println("get(A): " + cache.get("A"));
    }

    private static List<CacheNode<String, String>> buildNodes(int count, int capacityPerNode) {
        List<CacheNode<String, String>> nodes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            nodes.add(new CacheNode<>("node-" + i, capacityPerNode, new LRUEvictionPolicy<>()));
        }
        return nodes;
    }
}