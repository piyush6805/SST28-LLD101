import java.util.*;
public class InMemoryDatabase<K, V> implements ScanCapableDataStore<K, V> {

    private final Map<K, V> db = new HashMap<>();

    @Override
    public V get(K key) {
        synchronized (db) {
            return db.get(key);
        }
    }

    @Override
    public void put(K key, V value) {
        synchronized (db) {
            db.put(key, value);
        }
    }

    @Override
    public Map<K, V> entries() {
        synchronized (db) {
            return new HashMap<>(db);
        }
    }
}