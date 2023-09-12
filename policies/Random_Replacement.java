import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomReplacementCache<K, V> {
    private final int capacity;
    private final Map<K, V> cache;
    private final Random random;

    public RandomReplacementCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.random = new Random();
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void put(K key, V value) {
        if (cache.size() >= capacity) {
            // If the cache is full, choose a random key to evict
            int randomIndex = random.nextInt(cache.size());
            K randomKey = cache.keySet().toArray(new Object[0])[randomIndex];
            cache.remove(randomKey);
        }
        cache.put(key, value);
    }

    public static void main(String[] args) {
        RandomReplacementCache<Integer, String> cache = new RandomReplacementCache<>(3);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        System.out.println(cache.get(1)); // Output: One

        cache.put(4, "Four"); // Adding a new item, evicting a random entry

        System.out.println(cache.get(2)); // Output: null (evicted)
    }
}
