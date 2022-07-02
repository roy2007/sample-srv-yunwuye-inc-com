/**
 *
 */
package com.yunwuye.sample.enums;

/**
 * @author Roy
 *
 */
public enum OpActionEnum implements BaseEnum<String> {
    INSERT("1", "新增"), MODIFY("2", "修改"), QUERY("3", "查询"), ;
    OpActionEnum(String code, String desc) {
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
