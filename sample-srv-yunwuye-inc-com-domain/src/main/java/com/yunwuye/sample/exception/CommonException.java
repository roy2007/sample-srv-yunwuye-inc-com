package com.yunwuye.sample.exception;

import com.yunwuye.sample.enums.CommonResultCode;

/**
 * 所有业务异常抛出时基础，如果有特殊需求可以重新继承。方便在AOP中统一拦截使用
 *
 * @author Roy
 *
 */
public class CommonException extends RuntimeException {
    private static final long serialVersionUID = -3797684197708769433L;
    public final CommonResultCode code;
    public final Object[] args;

    public CommonException(CommonResultCode code, Object... args) {
        super(String.format(code.getResultDesc(), args));
        this.code = code;
        this.args = args;
    }

    public CommonException(CommonResultCode code) {
        super(code.getResultDesc());
        this.code = code;
        this.args = null;
    }

    public CommonException(Throwable cause) {
        super(cause);
        this.code = CommonResultCode.SYS_FAIL;
        this.args = new Object[] { cause.getMessage() };
    }

    public CommonException(String message) {
        super(message);
        this.code = CommonResultCode.SYS_FAIL;
        this.args = new Object[] { message };
    }
}
