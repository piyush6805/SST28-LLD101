import java.util.Iterator;
import java.util.LinkedHashSet;

public class FIFOEvictionPolicy<K> implements EvictionPolicy<K> {

    private final LinkedHashSet<K> set = new LinkedHashSet<>();

    @Override
    public void keyAccessed(K key) {
        set.add(key);
    }

    @Override
    public K evictKey() {
        Iterator<K> it = set.iterator();
        if (!it.hasNext()) {
            return null;
        }
        K oldest = it.next();
        it.remove();
        return oldest;
    }
}