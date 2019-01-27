package jp.llv.atcoder.lib.meta;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface Verified {

    String[] value() default {};

}
