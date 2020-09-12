package com.yunwuye.sample.common;

import java.io.Serializable;

import com.yunwuye.sample.common.base.sensitive.ToSensitiveStringBuilder;

/**
 * @author Roy
 *
 */
public class ToString implements Serializable {
    private static final long serialVersionUID = 3145476996823776023L;

    @Override
    public String toString() {
        return ToSensitiveStringBuilder.reflectionToString(this);
    }

}
