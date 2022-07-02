package com.yunwuye.sample.param.user;

import java.util.Date;
import com.yunwuye.sample.param.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author Roy
 * @param <T>
 *
 * @date 2020年7月5日-下午4:28:40
 */
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
public class UserParam extends PageParam<UserParam> {

  /**
   * UserParam.java -long
   */
  private static final long serialVersionUID = -2090885893374426403L;
  private String name;
  private Date birthday;
  private String sex;
  private String address;
}
