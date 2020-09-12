
package com.yunwuye.sample.dto;

import com.yunwuye.sample.common.base.dto.BaseDTO;

/**
 * 学生表
 */
public class StudentDTO extends BaseDTO {

    /**
   * StudentDTO.java -long
   */
  private static final long serialVersionUID = -4381797062180787925L;
  /**
   * 姓名
   */
    private String name;
    /**
     * 年龄
     */
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}