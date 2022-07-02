/**
 *
 */
package com.yunwuye.sample.sensitive;

import com.yunwuye.sample.enums.BaseEnum;

/**
 * @author Roy
 *
 */
public enum SensitiveFormatEnum implements BaseEnum<String> {
    /** 全屏蔽 */
    SHIELDED_FULLY("SHIELDED_FULLY", "全屏蔽"),
    /** 前6后4屏蔽 */
    SHIELDED_6_4("SHIELDED_6_4", "前六后四屏蔽"),
    /** 后4位不屏蔽 */
    SHIELDED__4("SHIELDED__4", "后4位不屏蔽"),
    SHIELDED_3_4("SHIELDED_3_4", "前3后四屏蔽"),

    ;
    private SensitiveFormatEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /** 编码 */
    private String code;
    /** 说明 */
    private String desc;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
