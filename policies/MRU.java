import java.util.LinkedHashMap;
import java.util.Map;

public class MRUCache<K, V> {
    private final int capacity;
    private final Map<K, V> cache;

    public MRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true);
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void put(K key, V value) {
        if (cache.size() >= capacity) {
            // If the cache is full, remove the most recently used entry
            K mruKey = cache.keySet().iterator().next();
            cache.remove(mruKey);
        }
        cache.put(key, value);
    }

    public static void main(String[] args) {
        MRUCache<Integer, String> cache = new MRUCache<>(3);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        System.out.println(cache.get(1)); // Output: One (1 is the most recently used)

        cache.put(4, "Four"); // Adding a new item, evicting the most recently used (1)

        System.out.println(cache.get(1)); // Output: null (evicted)
    }
}
