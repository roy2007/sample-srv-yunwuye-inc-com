package com.yunwuye.sample.common;

import java.io.Serializable;

/**
 *
 * @author Roy
 *
 * @date 2020年7月5日-上午11:35:59
 */
public interface BaseEnum<K> extends Serializable {

  /**
   * 取得code
   * 
   * @return
   */

  public K getCode();

  /**
   * 取得描述
   * 
   * @return
   */

  public String getDesc();
}
