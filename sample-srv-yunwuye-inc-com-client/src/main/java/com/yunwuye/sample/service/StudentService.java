package com.yunwuye.sample.service;

import com.yunwuye.sample.common.base.dto.BaseDTO;
import com.yunwuye.sample.dto.StudentDTO;
import com.yunwuye.sample.entity.BaseEntity;

/**
 * 学生接口
 */
public interface StudentService extends BaseService<BaseEntity, BaseDTO> {

  /**
   * 根据主键查询用户
   * 
   * @param id
   * @return
   */
  public StudentDTO findById(Long id);
}
