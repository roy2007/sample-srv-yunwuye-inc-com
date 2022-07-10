package com.yunwuye.sample.enums;


/**
 *
 * @author Roy
 *
 * @date 2022年7月3日-上午10:00:46
 */
public enum VerificationCodeKeyEnum implements BaseEnum<String> {
    /**
    * Count
    */
    ARITHMETIC ("arithmetic", "算术"),
    /**
    * chinese
    */
    CHINESE ("chinese", "中文"),
    /**
    * Chinese Flash
    */
    CHINESE_GIF ("chineseGif", "中文flash"),
    /**
    * Flash chart
    */
    GIF ("gifChart", "flash图"),
    /**
     * spec
     */
    SPEC ("spec", "算术"),
    ;

    VerificationCodeKeyEnum (String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 枚举CODE
     */
    private String code;
    /**
     * 枚举描述
     */
    private String desc;

    @Override
    public String getCode () {
        return code;
    }

    @Override
    public String getDesc () {
        return desc;
    }
}
