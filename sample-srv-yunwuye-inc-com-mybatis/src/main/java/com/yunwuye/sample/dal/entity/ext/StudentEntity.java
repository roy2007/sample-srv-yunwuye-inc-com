package com.yunwuye.sample.dal.entity.ext;

/**
 * 学生表
 *
 * @author Roy
 *
 * @date 2022年7月2日-下午1:49:37
 */
public class StudentEntity extends BaseEntity {

  /**
   * StudentEntity.java -long
   */
  private static final long serialVersionUID = -6862696780295580996L;

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