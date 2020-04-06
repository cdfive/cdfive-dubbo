package com.cdfive.common.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static List<Long> strToLong(List<String> strList) {
        if (isEmpty(strList)) {
            return Collections.emptyList();
        }

        List<Long> longList = strList.stream().map(o -> Long.valueOf(o)).collect(Collectors.toList());
        return longList;
    }

    public static List<Long> strToLongFilterBlank(List<String> strList) {
        if (isEmpty(strList)) {
            return Collections.emptyList();
        }

        List<Long> longList = strList.stream().filter(o -> StringUtil.isNotBlank(o)).map(o -> Long.valueOf(o)).collect(Collectors.toList());
        return longList;
    }
}
