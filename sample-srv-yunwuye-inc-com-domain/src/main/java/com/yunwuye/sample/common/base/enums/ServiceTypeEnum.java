package com.yunwuye.sample.common.base.enums;

public enum ServiceTypeEnum implements BaseEnum<String> {
    SYS("SYS", "系统服务"), BIZ("BIZ", "业务服务"), REF("REF", "第三方服务"),
   ;

    ServiceTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    private String code;
    private String desc;
}
