package com.yunwuye.sample.dal.entity.ext;

/**
 * 用户实体
 *
 * @author Roy
 *
 * @date 2022年7月2日-下午1:49:12
 */
public class UserEntity extends BaseEntity {

  /**
   * UserEntity.java -long
   */
  private static final long serialVersionUID = -8656231130232608690L;
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
