public class ModuloDistributionStrategy<K> implements DistributionStrategy<K> {

    @Override
    public int getNodeIndex(K key, int totalNodes) {
        return Math.abs(key.hashCode()) % totalNodes;
    }
}