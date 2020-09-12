/**
 *
 */
package com.yunwuye.sample.common.base.enums;

import java.io.Serializable;

/**
 * @author Roy
 *
 */
public interface BaseEnum<K> extends Serializable {
    public K getCode();
    public String getDesc();
}
