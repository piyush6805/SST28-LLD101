import java.util.*;

public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {

    private final LinkedHashSet<K> set = new LinkedHashSet<>();

    @Override
    public void keyAccessed(K key) {
        if (set.contains(key)) {
            set.remove(key);
        }
        set.add(key);
    }

    @Override
    public K evictKey() {
        Iterator<K> it = set.iterator();
        if (!it.hasNext()) return null;

        K key = it.next();
        it.remove();
        return key;
    }
}