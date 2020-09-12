package com.yunwuye.sample.common.base.result;

import java.util.Map;

import com.yunwuye.sample.common.ToString;
import com.yunwuye.sample.common.base.enums.CommonResultCode;

/**
 *
 * @author Roy
 *
 */
public class Result<T> extends ToString {
    private static final long serialVersionUID = 3609592334841897595L;
    /** 业务结果，请求成功 */
    private boolean success;
    private String code;
    private String message;
    private T data;
    private Map<String, Object> extMap;

    public Result() {
        super();
        this.code = CommonResultCode.BIZ_SUCCESS.getCode();
        this.message = CommonResultCode.BIZ_SUCCESS.getDesc();
        this.success = Boolean.TRUE;
    }

    public Result(CommonResultCode commonEnum, boolean success) {
        super();
        this.code = commonEnum.getCode();
        this.message = commonEnum.getDesc();
        this.success = success;
    }

    public Result(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    /**
     * @return success
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * @return the extMap
     */
    public Map<String, Object> getExtMap() {
        return extMap;
    }

    /**
     * @param extMap
     *            the extMap to set
     */
    public void setExtMap(Map<String, Object> extMap) {
        this.extMap = extMap;
    }

}
