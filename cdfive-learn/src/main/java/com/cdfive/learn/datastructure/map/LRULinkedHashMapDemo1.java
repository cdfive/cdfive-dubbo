package com.cdfive.learn.datastructure.map;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cdfive
 */
public class LRULinkedHashMapDemo1<K, V> extends LinkedHashMap<K, V> {

    protected int initialCapacity;

    public LRULinkedHashMapDemo1(int initialCapacity) {
        super(initialCapacity);
        this.initialCapacity = initialCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > initialCapacity;
    }

    public static void main(String[] args) {
        LRULinkedHashMapDemo1<Integer, Integer> map = new LRULinkedHashMapDemo1<>(3);
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

    private static void printSizeAndKey(LRULinkedHashMapDemo1<?, ?> map) {
        System.out.println(StringUtils.center("分隔线", 30, "-"));
        System.out.println("size=" + map.size());
        for (Object key : map.keySet()) {
            System.out.println(key);
        }
    }
}
