package com.yunwuye.sample.common.validator.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yunwuye.sample.common.validator.AbstractBaseValidator;

/**
 *
 * @author Roy
 *
 * @date 2020年6月23日-下午4:30:56
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Repeatable(Validators.class)
public @interface Validator {

    String name() default "";

    String errorMessage() default "";

    ValidateClass[] value() default {};

    public @interface ValidateClass {
        Class<? extends AbstractBaseValidator<? extends Annotation>> validator();
    }
}
