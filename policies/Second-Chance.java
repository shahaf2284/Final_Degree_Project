import java.util.LinkedHashMap;
import java.util.Map;

public class SecondChanceCache<K, V> {
    private final int capacity;
    private final Map<K, V> cache;
    private final Map<K, Boolean> secondChanceBits;
    private int hand;

    public SecondChanceCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true);
        this.secondChanceBits = new LinkedHashMap<>(capacity, 0.75f, true);
        this.hand = 0;
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void put(K key, V value) {
        if (cache.size() >= capacity) {
            evict();
        }
        cache.put(key, value);
        secondChanceBits.put(key, true);
    }

    private void evict() {
        while (true) {
            K candidate = cache.keySet().toArray(new Object[0])[hand];
            if (secondChanceBits.get(candidate)) {
                secondChanceBits.put(candidate, false);
                advanceHand();
            } else {
                cache.remove(candidate);
                secondChanceBits.remove(candidate);
                return;
            }
        }
    }

    private void advanceHand() {
        hand = (hand + 1) % capacity;
    }

    public static void main(String[] args) {
        SecondChanceCache<Integer, String> cache = new SecondChanceCache<>(3);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        System.out.println(cache.get(1)); // Output: One

        cache.put(4, "Four"); // Adding a new item, evicting an item based on second chance

        System.out.println(cache.get(2)); // Output: null (evicted)
    }
}
// 1. create a SecondChanceCache class that uses a LinkedHashMap to store key-value pairs.
// 2. maintain a secondChanceBits map to keep track of whether a key has received a second chance.
// 3. The hand variable is used to simulate the clock hand, which determines which item to consider for eviction.
// 4. In the put method, when adding a new item, we mark it with a second chance. If the cache is full, 
//    we perform eviction using the Clock algorithm, giving a second chance to items as needed.
// 5. The advanceHand method is used to move the clock hand to the next position.
// This is a simplified version of the Clock or Second-Chance Algorithm. Depending on your specific requirements and use case,
// you may need to adapt and enhance the implementation further.


