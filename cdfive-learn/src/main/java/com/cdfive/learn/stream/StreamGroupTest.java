package com.cdfive.learn.stream;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class StreamGroupTest {

    public static void main(String[] args) {
        PromotionActivityVo activity1 = new PromotionActivityVo(1, PromotionType.FULL_MINUS, "满200减20");
        PromotionActivityVo activity2 = new PromotionActivityVo(2, PromotionType.FULL_MINUS, "满300减30");
        PromotionActivityVo activity3 = new PromotionActivityVo(3, PromotionType.BUY_GIFT, "买180赠10");
        PromotionActivityVo activity4 = new PromotionActivityVo(4, PromotionType.FULL_GIFT, "满150赠5");
        PromotionActivityVo activity5 = new PromotionActivityVo(5, PromotionType.FULL_GIFT, "满300赠25");
        List<PromotionActivityVo> activities = Lists.newArrayList(activity1, activity2, activity3, activity4, activity5);

        Map<PromotionType, PromotionActivityVo> promotionTypeMap = activities.stream().collect(Collectors.groupingBy(PromotionActivityVo::getType
                , Collectors.collectingAndThen(Collectors.reducing((o1, o2) ->
                        Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId())) > 0 ? o1 : o2), Optional::get)));

//        Map<PromotionType, PromotionActivityVo> promotionTypeMap = activities.stream().collect(Collectors.toMap(PromotionActivityVo::getType, Function.identity(), (o1, o2) -> Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId())) > 0 ? o1 : o2));

        List<String> labels = Arrays.stream(PromotionType.values()).map(promotionTypeMap::get).filter(Objects::nonNull).map(PromotionActivityVo::getLabel).collect(Collectors.toList());
        System.out.println(labels);
    }

    @Getter
    @AllArgsConstructor
    private static enum PromotionType {
        FULL_MINUS("FULL_MINUS", "满减"),
        FULL_GIFT("FULL_GIFT", "满赠"),
        BUY_GIFT("BUY_GIFT", "买赠"),
        COUPON("COUPON", "优惠券");

        private String name;
        private String description;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class PromotionActivityVo implements Serializable {

        private Integer id;

        private PromotionType type;

        private String label;
    }
}
