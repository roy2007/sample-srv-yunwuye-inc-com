package com.yunwuye.sample.enums;

public enum CompositionTypeEnum implements BaseEnum<String> {
    GE_LE("1", "大于等于左，小于等于右"),
    GE_LT("2", "大于等 于左，小于右"),
    GT_LT("3", "大于左，小于右"),
    GT_LE("4", "大于左，小于等 于右"),

    ;
    CompositionTypeEnum(String code, String desc) {
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
