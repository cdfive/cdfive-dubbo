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
        print(map);
//        map.get(2);
        map.put(2, 22);
        print(map);
        map.put(5, 5);
        print(map);
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

    public LinkedHashMap<K, V> getMap() {
        return map;
    }

    private static void print(LRULinkedHashMapDemo2<Integer, Integer> map) {
        System.out.println(StringUtils.center("分隔线", 30, "-"));
        System.out.println("size=" + map.size());

        /*
        for (Integer key : map.keySet()) {
//            Exception in thread "main" java.util.ConcurrentModificationException
//            at java.util.LinkedHashMap$LinkedHashIterator.nextNode(LinkedHashMap.java:719)
//            at java.util.LinkedHashMap$LinkedKeyIterator.next(LinkedHashMap.java:742)
            System.out.println(key + "=>" + map.get(key));
//            System.out.println(key);// OK
        }*/

        System.out.println(map.getMap()); // OK
    }
}
