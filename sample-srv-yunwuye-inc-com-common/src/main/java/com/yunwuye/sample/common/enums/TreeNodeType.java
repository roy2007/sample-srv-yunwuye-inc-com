package com.yunwuye.sample.common.enums;
import com.yunwuye.sample.common.BaseEnum;
import com.yunwuye.sample.common.utils.BaseEnumUtils;
/**
 *
 * @author Roy
 *
 * @date 2020年7月5日-上午11:42:35
 */
public enum TreeNodeType implements BaseEnum<String> {

  TITLE("1", "标题"), DOCUMENT("2", "文档"),
  ;
  /**
   * 枚举CODE
   */
  private String code;
  /**
   * 枚举描述
   */
  private String desc;

  /**
   * 构造函数
   * 
   * @param code
   * @param desc
   */
  private TreeNodeType(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  /**
   * 根据code获得枚举
   */
  public static TreeNodeType getByCode(String code) {
    return BaseEnumUtils.getByCode(code, values());
  }

  /**
   * Getter method for property code.
   *
   * @return property value of code
   */
  public String getCode() {
    return code;
  }

  /**
   * Setter method for property code.
   *
   * @param code
   *          value to be assigned to property code
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Getter method for property desc.
   *
   * @return property value of desc
   */
  public String getDesc() {
    return desc;
  }

  /**
   * Setter method for property desc.
   *
   * @param desc
   *          value to be assigned to property desc
   */
  public void setDesc(String desc) {
    this.desc = desc;
  }
}
