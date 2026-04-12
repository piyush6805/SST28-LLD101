public class LoadBalancer<K> {

    private final DistributionStrategy<K> distributionStrategy;

    public LoadBalancer(DistributionStrategy<K> distributionStrategy) {
        this.distributionStrategy = distributionStrategy;
    }

    public int pickNodeIndex(K key, int totalNodes) {
        return distributionStrategy.getNodeIndex(key, totalNodes);
    }
}