package com.yunwuye.sample.common.base.enums;

public enum CommonResultCode implements BaseEnum<String> {
    SYS_SUCCESS("200", ServiceTypeEnum.SYS, "系统服务请求成功"),
    BIZ_SUCCESS("201", ServiceTypeEnum.BIZ, "业务服务请求成功"),
    REF_SUCCESS("202", ServiceTypeEnum.REF, "第三方服务请求成功"),

    SYS_FAIL("500", ServiceTypeEnum.SYS, "系统服务请求失败"),
    BIZ_FAIL("501", ServiceTypeEnum.BIZ, "业务服务请求失败"),
    REF_FAIL("502", ServiceTypeEnum.REF, "第三方服务请求失败"),
    CHECK_BASIC_PARAM_ERROR("503",ServiceTypeEnum.BIZ, "数据校验失败"),
    PARAM_IS_NOT_NULL("504",ServiceTypeEnum.BIZ, "请求参数  %s 不能为空"),
    BIZ_SEREVICE_FAIL("505", ServiceTypeEnum.BIZ, "业务服务运行时失败: %s"),
    UNFORESEEN_EXCEPTION("506", ServiceTypeEnum.BIZ, "无法捕获异常: %s")

    /**
     * 其他业务响应返回代码请设置 从 1000 开始往上，一个业务模块对应四位数
     * 如：订单 管理：1000，创建 1001, 修改1002.
     *    用户管理： 2000，创建 2001，修改2002
     */




    ;
    private ServiceTypeEnum serviceType;
    private String resultCode;
    private String resultDesc;

    CommonResultCode(String resultCode, ServiceTypeEnum serviceType, String resultDesc) {
        this.resultCode = resultCode;
        this.serviceType = serviceType;
        this.resultDesc = resultDesc;
    }

    /**
     * @return the serviceType
     */
    public ServiceTypeEnum getServiceType() {
        return serviceType;
    }

    /**
     * @return the resultCode
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * @return the resultDesc
     */
    public String getResultDesc() {
        return resultDesc;
    }

    @Override
    public String getCode() {
        return resultCode;
    }

    @Override
    public String getDesc() {
        return resultDesc;
    }
}
