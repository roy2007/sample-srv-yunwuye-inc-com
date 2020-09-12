package com.yunwuye.sample.service;

import java.util.List;

import com.yunwuye.sample.common.base.dto.BaseDTO;
import com.yunwuye.sample.common.base.result.PageResult;
import com.yunwuye.sample.common.base.result.Result;
import com.yunwuye.sample.dto.UserDTO;
import com.yunwuye.sample.entity.BaseEntity;
import com.yunwuye.sample.param.user.UserParam;

public interface UserService extends BaseService<BaseEntity, BaseDTO> {
  /**
   *
   * @param userParam
   * @return
   */
  public UserDTO getUserById(Long id);

  /**
   * 根据主键查询用户
   * 
   * @param id
   * @return
   */
  public Result<UserDTO> findById(Long id);

  /**
   * 根据查询条件分页获取用户信息
   * 
   * @param userParam
   * @return
   */
  public PageResult<List<UserDTO>> queryByConditions(UserParam userParam);

}