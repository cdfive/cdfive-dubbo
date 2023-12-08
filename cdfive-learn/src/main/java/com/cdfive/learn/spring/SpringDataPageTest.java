package com.cdfive.learn.spring;

import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cdfive
 */
public class SpringDataPageTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        System.out.println(list);

        Pageable pageable = PageRequest.of(0, 10);
        Page<String> page = new PageImpl<>(list, pageable, list.size());
        List<String> content = page.getContent();
        // org.springframework.data.domain.Chunk#getContent
        // Collections.unmodifiableList
        // java.lang.UnsupportedOperationException
        content.removeIf(o -> "a".equals(o));
        System.out.println(list);
    }
}
