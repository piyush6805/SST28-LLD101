import java.util.Map;

public interface ScanCapableDataStore<K, V> extends DataStore<K, V> {
    Map<K, V> entries();
}