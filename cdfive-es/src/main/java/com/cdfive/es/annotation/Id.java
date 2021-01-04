package com.cdfive.es.annotation;

import java.lang.annotation.*;

/**
 * @author cdfive
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Id {

}
