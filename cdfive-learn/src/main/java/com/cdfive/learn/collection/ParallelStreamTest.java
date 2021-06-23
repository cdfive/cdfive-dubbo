package com.cdfive.learn.collection;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author cdfive
 */
public class ParallelStreamTest {

    public static void main(String[] args) {
        int min = 1;
        int max = 300;
        List<Integer> list = IntStream.range(min, max + 1).mapToObj(o -> o).collect(Collectors.toList());
        System.out.println("size=" + list.size());

        int batchSize = 20;
        List<List<Integer>> listParts = Lists.partition(list, batchSize);
        int index = 0;
        for (List<Integer> listPart : listParts) {
            index++;
            int finalIndex = index;
//            listPart.stream().forEach(o -> {
            listPart.parallelStream().forEach(o -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(finalIndex + "=>" + o);
            });

            // If making a breakpoint here, we can see that it executed after all listPart items completed
            System.out.println(StringUtils.center("cdfive", 30, "-"));
        }
    }
}
