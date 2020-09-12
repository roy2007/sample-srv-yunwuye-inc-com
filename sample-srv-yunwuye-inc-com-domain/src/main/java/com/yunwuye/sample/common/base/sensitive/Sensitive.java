/**
 *
 */
package com.yunwuye.sample.common.base.sensitive;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yunwuye.sample.common.base.enums.SensitiveFormatEnum;

/**
 * @author Roy
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sensitive {
    /**
     *
     * 敏感信息格式化,默认为全部屏蔽
     *
     * @return
     */
    SensitiveFormatEnum format() default SensitiveFormatEnum.SHIELDED_FULLY;

    /**
     *
     * 字段忽略,默认不忽略
     *
     * @return
     */
    boolean ignore() default false;
}
