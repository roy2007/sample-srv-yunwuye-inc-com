
package com.yunwuye.sample.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.yunwuye.sample.common.Processor;

/**
 *
 * @author Roy
 *
 * @date 2020年6月24日-下午12:21:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ParamProcess {
    Class<? extends Processor>[] value();
}
