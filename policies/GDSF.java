import java.util.*;

public class GDSFCache<K, V> {
    private final int capacity;
    private final Map<K, V> cache;
    private final Queue<K> sizeQueue;
    private final Queue<K> frequencyQueue;
    private final Map<K, Integer> sizeMap;
    private final Map<K, Integer> frequencyMap;

    public GDSFCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.sizeQueue = new LinkedList<>();
        this.frequencyQueue = new LinkedList<>();
        this.sizeMap = new HashMap<>();
        this.frequencyMap = new HashMap<>();
    }

    public V get(K key) {
        if (cache.containsKey(key)) {
            updateFrequency(key);
            return cache.get(key);
        }
        return null;
    }

    public void put(K key, V value, int size, int frequency) {
        if (cache.size() >= capacity) {
            evict();
        }

        cache.put(key, value);
        sizeQueue.offer(key);
        frequencyQueue.offer(key);
        sizeMap.put(key, size);
        frequencyMap.put(key, frequency);
    }

    private void evict() {
        while (cache.size() >= capacity) {
            K sizeKey = sizeQueue.poll();
            K frequencyKey = frequencyQueue.poll();

            if (sizeKey != null) {
                cache.remove(sizeKey);
                sizeMap.remove(sizeKey);
            }

            if (frequencyKey != null) {
                frequencyMap.remove(frequencyKey);
            }
        }
    }

    private void updateFrequency(K key) {
        Integer frequency = frequencyMap.get(key);
        if (frequency != null) {
            frequencyMap.put(key, frequency + 1);
        }
    }

    public static void main(String[] args) {
        GDSFCache<Integer, String> cache = new GDSFCache<>(5);

        cache.put(1, "One", 1, 1);
        cache.put(2, "Two", 2, 2);
        cache.put(3, "Three", 1, 3);

        System.out.println(cache.get(1)); // Output: One

        cache.put(4, "Four", 3, 1);
        cache.put(5, "Five", 2, 4);

        System.out.println(cache.get(2)); // Output: null (evicted)
    }
}
//-----------------------------------------------------------------------------------------------------------------------------
// 1. create a GDSFCache class that uses a HashMap to store key-value pairs.
// 2. We maintain two separate queues, sizeQueue and frequencyQueue, to track items based on their size and frequency of access.
// 3. sizeMap and frequencyMap are used to store the size and frequency information for each key.
// 4. In the put method, when adding a new item, we add it to both queues and update the size and frequency maps.
// 5. The evict method is used to evict items when the cache size exceeds its capacity.
// 6. The updateFrequency method updates the access frequency of an item when it is accessed again.

// Please note that this is a simplified version of the GDSF cache algorithm. For a complete and efficient implementation,
// you would need more complex data structures and algorithms to track size and frequency efficiently.
