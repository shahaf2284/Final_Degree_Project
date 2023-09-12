import java.util.LinkedHashMap;
import java.util.Map;

public class FIFOCache<K, V> {
    private final int capacity;
    private final Map<K, V> cache;

    public FIFOCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true);
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void put(K key, V value) {
        if (cache.size() >= capacity) {
            // If the cache is full, remove the oldest entry
            K oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }
        cache.put(key, value);
    }

    public static void main(String[] args) {
        FIFOCache<Integer, String> cache = new FIFOCache<>(3);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        System.out.println(cache.get(1)); // Output: One

        cache.put(4, "Four"); // Adding a new item, evicting the oldest (2)

        System.out.println(cache.get(2)); // Output: null (evicted)
    }
}
