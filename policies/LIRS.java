import java.util.LinkedHashMap;
import java.util.Map;

public class LIRSCache<K, V> {
    private final int capacity;
    private final Map<K, V> cache;
    private final int hSize;  // Size of the LIRS history set
    private int qSize;        // Size of the LIRS queue
    private int hQueueSize;   // Size of the LIRS history queue

    public LIRSCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true);
        this.hSize = capacity / 2;
        this.qSize = 0;
        this.hQueueSize = 0;
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void put(K key, V value) {
        if (qSize >= capacity) {
            evict();
        }

        if (qSize >= hSize) {
            if (hQueueSize > 0) {
                removeLIRSHistory();
            } else {
                removeLIRSQueue();
            }
        }

        cache.put(key, value);
        qSize++;

        if (qSize <= hSize) {
            hQueueSize++;
        }
    }

    private void evict() {
        while (qSize >= capacity) {
            K lirsKey = cache.keySet().iterator().next();
            cache.remove(lirsKey);
            if (hQueueSize > 0 && !cache.containsKey(lirsKey)) {
                hQueueSize--;
            }
            qSize--;
        }
    }

    private void removeLIRSQueue() {
        K lirsKey = cache.keySet().iterator().next();
        cache.remove(lirsKey);
        hQueueSize--;
        qSize--;
    }

    private void removeLIRSHistory() {
        K lirsKey = cache.keySet().stream()
                .skip(hQueueSize)
                .findFirst()
                .orElse(null);
        if (lirsKey != null) {
            cache.remove(lirsKey);
            hQueueSize--;
            qSize--;
        }
    }

    public static void main(String[] args) {
        LIRSCache<Integer, String> cache = new LIRSCache<>(5);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        System.out.println(cache.get(1)); // Output: One

        cache.put(4, "Four");
        cache.put(5, "Five");
        cache.put(6, "Six");

        System.out.println(cache.get(2)); // Output: null (evicted)
    }
}
// -------------------------------------------------------------------------------------------------------------------------------------
// 1. create a LIRSCache class that uses a LinkedHashMap to store key-value pairs.
// 2. We maintain hSize as half of the cache capacity for the LIRS history set and qSize to track the current queue size.
// 3. In the put method, when adding a new item, we check if the queue size exceeds the LIRS capacity (hSize). 
//    If it does, we remove items either from the LIRS history or the LIRS queue, based on their access patterns.
// 4. The evict method is used to evict items when the cache size exceeds its capacity.
// 5.removeLIRSQueue and removeLIRSHistory methods remove items from either the LIRS queue or the LIRS history.

// This is a simplified version of the LIRS cache algorithm. For a complete and efficient implementation, you would need more complex 
// data structures and algorithms to track access patterns and recency.
