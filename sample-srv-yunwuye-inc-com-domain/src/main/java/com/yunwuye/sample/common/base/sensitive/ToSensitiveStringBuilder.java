/**
 *
 */
package com.yunwuye.sample.common.base.sensitive;

import java.lang.reflect.Field;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yunwuye.sample.common.util.SensitiveFormatUtil;

/**
 *
 * 转换敏感字符串构建器 ,现支持前六后四及全字符屏蔽
 *
 * @author Roy
 *
 */
public class ToSensitiveStringBuilder {
    /**
     *
     * 反射字符串,封装org.apache.commons.lang.builder.ToStringBuilder类
     *
     * @param object
     * @return
     */
    public static String reflectionToString(Object object) {
        // 负责如果有被反射的对象
        ToStringBuilder toStringBuilder = (new ReflectionToStringBuilder(object, ToStringStyle.SHORT_PREFIX_STYLE) {
            /**
             * @see ReflectionToStringBuilder#accept(Field)
             */
            @Override
            protected boolean accept(Field field) {
                // 判断是否是敏感字段
                if (!field.isAnnotationPresent(Sensitive.class)) {
                    return super.accept(field);
                }
                Sensitive sensitive = field.getAnnotation(Sensitive.class);
                if (sensitive == null) {
                    return super.accept(field);
                }
                if (sensitive.ignore()) {
                    return false;
                }
                return super.accept(field);
            }

            /**
             * @see ReflectionToStringBuilder#getValue(Field)
             */
            @Override
            protected Object getValue(Field field) throws IllegalArgumentException, IllegalAccessException {
                // 判断是否是敏感字段
                if (!field.isAnnotationPresent(Sensitive.class)) {
                    return super.getValue(field);
                }
                // 如果反射值对象为NULL, 则直接返回
                if (!(super.getValue(field) instanceof String)) {
                    return super.getValue(field);
                }
                // 获取敏感字段模型
                Sensitive sensitive = field.getAnnotation(Sensitive.class);
                if (sensitive == null) {
                    return super.getValue(field);
                }
                // 屏蔽敏感信息
                String value = String.valueOf(super.getValue(field));
                return SensitiveFormatUtil.shield(sensitive.format(), value);
            }
        });
        return toStringBuilder.toString();
    }
}
