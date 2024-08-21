package com.cdfive.common.util;


import org.apache.commons.collections4.map.LRUMap;

/**
 * @author cdfive
 */
public class TimeoutLRUMap<K, V> {

    private final LRUMap<K, TimeoutValue<V>> lruMap;

    private final long timeoutMs;

    public TimeoutLRUMap(int maxSize, long timeoutMs) {
        this.lruMap = new LRUMap<>(maxSize);
        this.timeoutMs = timeoutMs;
    }

    public void put(K key, V value) {
        this.lruMap.put(key, new TimeoutValue<>(value, System.currentTimeMillis()));
    }

    public V get(K key) {
        TimeoutValue<V> timeoutValue = this.lruMap.get(key);
        V value = timeoutValue.getValue();
        if (value == null) {
            return null;
        }

        if (System.currentTimeMillis() > timeoutValue.getTimestamp() + this.timeoutMs) {
            this.lruMap.remove(key);
            return null;
        }

        return value;
    }

    private static class TimeoutValue<V> {
        private final V value;
        private final long timestamp;

        public TimeoutValue(V value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }

        public V getValue() {
            return value;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}
