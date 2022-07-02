package com.yunwuye.sample.exception;

import com.yunwuye.sample.enums.CommonResultCode;

/**
 * 服务异常
 * @author Roy
 *
 * @date 2020年5月3日-下午6:09:08
 */
public class ThirdServiceException extends CommonException {

    /**
     * ApplicationException.java -long
     */
    private static final long serialVersionUID = -1083753446286132674L;
    protected static final CommonResultCode code = CommonResultCode.REF_FAIL;

    public ThirdServiceException(){
        super(code);
    }
    public ThirdServiceException(CommonResultCode code) {
        super(code);
    }
}
