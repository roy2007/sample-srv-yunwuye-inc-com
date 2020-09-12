package com.yunwuye.sample.dal.handler;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import com.yunwuye.sample.dal.entity.TUser;

/**
 *
 * @author Roy
 *
 * @date 2020年6月27日-下午6:20:42
 */
public class MyObjectFactory extends DefaultObjectFactory {

  /**
   * MyObjectFactory.java -long
   */
  private static final long serialVersionUID = -905897218167697443L;

  @SuppressWarnings("unchecked")
  @Override
  public Object create(@SuppressWarnings("rawtypes") Class type) {
    System.out.println("创建对象方法：" + type);
    if (type.equals(TUser.class)) {
      TUser user = (TUser) super.create(type);
      user.setName("object factory");
      user.setAge(2222);
      return user;
    }
    Object result = super.create(type);
    return result;
  }
}
