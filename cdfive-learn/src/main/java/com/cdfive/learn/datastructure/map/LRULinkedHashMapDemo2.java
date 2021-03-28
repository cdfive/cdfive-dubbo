package com.cdfive.learn.datastructure.map;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author cdfive
 */
public class LRULinkedHashMapDemo2<K, V> {

    public static void main(String[] args) {
        LRULinkedHashMapDemo2<Integer, Integer> map = new LRULinkedHashMapDemo2<>(3);
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        printSizeAndKey(map);
//        map.get(2);
        map.put(2, 22);
        printSizeAndKey(map);
        map.put(5, 5);
        printSizeAndKey(map);
    }

    private LinkedHashMap<K, V> map;

    private int capacity;

    public LRULinkedHashMapDemo2(int capacity) {
        this.capacity = capacity;
        this.map = new LinkedHashMap<>(capacity);
    }

    public V get(K key) {
        if (map.containsKey(key)) {
            V value = map.remove(key);
            map.put(key, value);
        }

        return map.get(key);
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            map.remove(key);
            map.put(key, value);
        } else if (map.size() < capacity) {
            map.put(key, value);
        } else if (map.size() == 0) {
            map.put(key, value);
        } else {
            map.remove(map.keySet().iterator().next());
            map.put(key, value);
        }
    }

    public int size() {
        return map.size();
    }

    public Set<K> keySet() {
        return map.keySet();
    }

    private static void printSizeAndKey(LRULinkedHashMapDemo2<?, ?> map) {
        System.out.println(StringUtils.center("分隔线", 30, "-"));
        System.out.println("size=" + map.size());
        for (Object key : map.keySet()) {
            System.out.println(key);
        }
    }
}
