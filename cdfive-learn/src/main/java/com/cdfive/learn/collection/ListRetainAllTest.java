package com.cdfive.learn.collection;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author cdfive
 */
public class ListRetainAllTest {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(1, 2, 3);
        // [1, 2, 3]
        System.out.println(list);
        boolean result = list.retainAll(Lists.newArrayList(1, 2));
        // true
        System.out.println(result);
        // [1, 2] !!Note that the data of original list has been changed
        System.out.println(list);
        // false
        System.out.println(Lists.newArrayList(1, 2).retainAll(list));
        // false
        System.out.println(Lists.newArrayList().retainAll(list));
        // true
        System.out.println(Lists.newArrayList(1, 2, 3, 4).retainAll(list));
        // true
        System.out.println(Lists.newArrayList(1, 4).retainAll(list));

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        List<Integer> list2 = Lists.newArrayList(1, 2, 3);
        // [1, 2, 3]
        System.out.println(list2);
        // [1, 2]
        System.out.println(CollectionUtils.intersection(list2, Lists.newArrayList(1, 2)));
        // [1, 2, 3] !!Note that the data of original list has no change
        System.out.println(list2);
        // [1, 2, 3, 4]
        System.out.println(CollectionUtils.union(list2, Lists.newArrayList( 2, 4)));
    }
}
