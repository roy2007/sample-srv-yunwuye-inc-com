
package com.yunwuye.sample.common.base.exception;

import com.yunwuye.sample.common.base.enums.CommonResultCode;

/**
 * 应用服务异常
 * 
 * @author Roy
 *
 * @date 2020年5月3日-下午6:09:08
 */
public class ValidateException extends CommonException {

    /**
     * ApplicationException.java
     */
    private static final long serialVersionUID = -1083753446286132674L;
    protected static final CommonResultCode code = CommonResultCode.CHECK_BASIC_PARAM_ERROR;

    public ValidateException() {
        super(code);
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(CommonResultCode code) {
        super(code);
    }

    public ValidateException(CommonResultCode code, Object... args) {
        super(code, args);
    }
}
