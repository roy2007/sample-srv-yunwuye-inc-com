/**
 *
 */
package com.yunwuye.sample.enums;

import java.io.Serializable;

/**
 * @author Roy
 *
 */
public interface BaseEnum<K> extends Serializable {
    public K getCode();
    public String getDesc();
}
