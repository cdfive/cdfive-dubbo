package com.cdfive.es.annotation;

import java.lang.annotation.*;

/**
 * @author cdfive
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Document {

    String index();
}
