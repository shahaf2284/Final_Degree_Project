import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        System.out.println(cache); // Output: {1=One, 2=Two, 3=Three}

        cache.get(2); // Accessing 2, making it the most recently used

        System.out.println(cache); // Output: {1=One, 3=Three, 2=Two}

        cache.put(4, "Four"); // Adding a new item, evicting the least recently used (1)

        System.out.println(cache); // Output: {3=Three, 2=Two, 4=Four}
    }
}
// In this policies:
// 1. We create an LRUCache class that extends LinkedHashMap, which is a data structure that maintains the order of elements based on their access.
// 2. In the constructor, we specify the cache capacity, and we call the super constructor with parameters to initialize the LinkedHashMap.
// 3.We override the removeEldestEntry method to control when to remove the least recently used entry. When the cache size exceeds the specified capacity, this method returns true, which triggers the removal of the eldest (least recently used) entry.
// 4.In the main method, we demonstrate how to use the LRUCache class by adding, accessing, and removing items from the cache.

