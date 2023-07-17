package com.cdfive.learn.stream;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class StreamToArrayTest {

    public static void main(String[] args) {
        List<ProductVo> productVos = Lists.newArrayList();
        productVos.add(new ProductVo("100001", "computer"));
        productVos.add(new ProductVo("100002", "keyboard"));

        String[] productCodes1 = productVos.stream().map(o -> o.getProductCode()).collect(Collectors.toList()).toArray(new String[0]);
        for (int i = 0; i < productCodes1.length; i++) {
            System.out.println(productCodes1[i]);
        }

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        String[] productCodes2 = productVos.stream().map(o -> o.getProductCode()).toArray(String[]::new);
        for (String productCode : productCodes2) {
            System.out.println(productCode);
        }
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class ProductVo implements Serializable {

        private static final long serialVersionUID = 7597614981404798228L;

        private String productCode;

        private String name;
    }
}
