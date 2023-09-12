// Creating a complete LFU (Least Frequently Used) cache implementation from scratch in Java can be a bit more complex than an LRU cache, 
// as it involves maintaining the access frequencies of items and selecting the least frequently used item for eviction.
// Here's a basic outline of how you can implement an LFU cache in Java

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class LFUCache<K, V> {
    private final int capacity;
    private final Map<K, V> cache;
    private final Map<K, Integer> frequency;
    private final Map<Integer, Set<K>> frequencyLists;
    private int minFrequency;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.frequency = new HashMap<>();
        this.frequencyLists = new HashMap<>();
        this.minFrequency = 0;
    }

    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }

        int freq = frequency.get(key);
        frequency.put(key, freq + 1);
        updateFrequencyList(key, freq);

        return cache.get(key);
    }

    public void put(K key, V value) {
        if (capacity <= 0) {
            return;
        }

        if (cache.size() >= capacity) {
            evict();
        }

        cache.put(key, value);
        frequency.put(key, 1);
        updateFrequencyList(key, 0);
        minFrequency = 0;
    }

    private void updateFrequencyList(K key, int oldFreq) {
        Set<K> oldFreqList = frequencyLists.getOrDefault(oldFreq, new LinkedHashSet<>());
        oldFreqList.remove(key);

        if (oldFreqList.isEmpty()) {
            if (oldFreq == minFrequency) {
                minFrequency++;
            }
            frequencyLists.remove(oldFreq);
        } else {
            frequencyLists.put(oldFreq, oldFreqList);
        }

        Set<K> newFreqList = frequencyLists.getOrDefault(oldFreq + 1, new LinkedHashSet<>());
        newFreqList.add(key);
        frequencyLists.put(oldFreq + 1, newFreqList);
    }

    private void evict() {
        Set<K> minFreqList = frequencyLists.get(minFrequency);
        K evictedKey = minFreqList.iterator().next();

        cache.remove(evictedKey);
        frequency.remove(evictedKey);
        minFreqList.remove(evictedKey);

        if (minFreqList.isEmpty()) {
            frequencyLists.remove(minFrequency);
        }
    }

    public static void main(String[] args) {
        LFUCache<Integer, String> cache = new LFUCache<>(2);

        cache.put(1, "One");
        cache.put(2, "Two");

        System.out.println(cache.get(1)); // Output: One

        cache.put(3, "Three");

        System.out.println(cache.get(2)); // Output: null (evicted due to low frequency)
    }
}

// This implementation maintains a cache for key-value pairs, a frequency map to track the access frequency of keys, and frequencyLists 
// to organize keys based on their frequencies. When adding or accessing items, it updates the frequencies and the frequency lists accordingly.
// Please note that this is a basic LFU cache implementation and may not be the most efficient or feature-rich. Depending on your specific use 
// case and requirements, you may need to enhance it furthe



