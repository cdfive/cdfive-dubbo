package com.cdfive.learn.stream;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * List->Map
 * null value
 *
 * @author cdfive
 */
public class StreamToMapTest2 {

    public static void main(String[] args) {
        Product p1 = new Product("1001", "aaa");
        Product p2 = new Product("1002", null);
        Product p3 = new Product("1003", "bbb");
        List<Product> list = Stream.of(p1, p2, p3).collect(Collectors.toList());

        /**
         * Exception in thread "main" java.lang.NullPointerException
         * 	at java.util.HashMap.merge(HashMap.java:1225)
         * 	at java.util.stream.Collectors.lambda$toMap$58(Collectors.java:1320)
         * 	at java.util.stream.ReduceOps$3ReducingSink.accept(ReduceOps.java:169)
         * 	at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1382)
         * 	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)
         * 	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:472)
         * 	at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:708)
         * 	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
         * 	at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:499)
         */
        Map<String, String> map = list.stream().collect(Collectors.toMap(p -> p.getProductCode(), p -> p.getBarCode(), (o, n) -> n));

        // OK
//        HashMap<Object, Object> map = list.stream().collect(HashMap::new, (m, p) -> m.put(p.getProductCode(), p.getBarCode()), HashMap::putAll);
        System.out.println(map);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class Product {

        private String productCode;

        private String barCode;
    }
}
