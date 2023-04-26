package com.cdfive.learn.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * List->Map
 * duplicate key
 *
 * @author cdfive
 */
public class StreamToMapTest1 {

    public static void main(String[] args) {
        Person p1 = new Person("aa", 18);
        Person p2 = new Person("bb", 20);
        Person p3 = new Person("cc", 18);
        List<Person> list = Stream.of(p1, p2, p3).collect(Collectors.toList());

        /**
         * Exception in thread "main" java.lang.IllegalStateException: Duplicate key StreamToMapTest1.Person(name=aa, age=18)
         * 	at java.util.stream.Collectors.lambda$throwingMerger$0(Collectors.java:133)
         * 	at java.util.HashMap.merge(HashMap.java:1254)
         * 	at java.util.stream.Collectors.lambda$toMap$58(Collectors.java:1320)
         * 	at java.util.stream.ReduceOps$3ReducingSink.accept(ReduceOps.java:169)
         * 	at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1382)
         * 	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)
         * 	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:472)
         * 	at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:708)
         * 	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
         * 	at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:499)
         */
        Map<Integer, Person> map = list.stream().collect(Collectors.toMap(p -> p.getAge(), p -> p));

        // OK
//        Map<Integer, Person> map = list.stream().collect(Collectors.toMap(p -> p.getAge(), p -> p, (o, n) -> n));
        System.out.println(map);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class Person {

        private String name;

        private Integer age;
    }
}
