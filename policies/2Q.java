import java.util.*;

public class TwoQueueCache<K, V> {
    private final int capacity;
    private Map<K, V> cache;
    private Queue<K> recentlyUsedQueue;
    private Queue<K> frequentlyUsedQueue;

    public TwoQueueCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.recentlyUsedQueue = new LinkedList<>();
        this.frequentlyUsedQueue = new LinkedList<>();
    }

    public V get(K key) {
        if (cache.containsKey(key)) {
            // Move the key to the frequently used queue
            recentlyUsedQueue.remove(key);
            frequentlyUsedQueue.offer(key);
            return cache.get(key);
        }
        return null;
    }

    public void put(K key, V value) {
        if (cache.size() >= capacity) {
            // Evict the least recently used item from the recently used queue
            K evictedKey = recentlyUsedQueue.poll();
            cache.remove(evictedKey);
        }

        // Add the new key-value pair to the cache and both queues
        cache.put(key, value);
        recentlyUsedQueue.offer(key);
        frequentlyUsedQueue.offer(key);
    }

    public static void main(String[] args) {
        TwoQueueCache<Integer, String> cache = new TwoQueueCache<>(3);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        System.out.println(cache.get(1)); // Output: One

        cache.put(4, "Four"); // Adding a new item, evicting the least recently used (2)

        System.out.println(cache.get(2)); // Output: null (evicted)
    }
}
//----------------------------------------------------------------------------------------------------------------
// 1. create a TwoQueueCache class that uses a HashMap as the underlying data structure to store key-value pairs.
// 2. maintain two separate queues: recentlyUsedQueue for recently accessed items and frequentlyUsedQueue for frequently accessed items.
// 3. In the get method, if the key exists in the cache, we move it to the frequentlyUsedQueue to indicate it was recently accessed.
// 4. In the put method, if the cache size exceeds the specified capacity, we evict the least recently used item from the recentlyUsedQueue.
// 5. then add the new key-value pair to the cache and both queues.
// This is a simplified version of the Two-Queue Algorithm (2Q) cache. Depending on your specific requirements and use case, 
// you may need to adapt and enhance the implementation further.


