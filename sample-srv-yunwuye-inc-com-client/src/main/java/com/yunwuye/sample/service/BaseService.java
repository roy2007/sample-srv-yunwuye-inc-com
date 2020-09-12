package com.yunwuye.sample.service;

import java.util.List;

import com.yunwuye.sample.common.base.dto.BaseDTO;
import com.yunwuye.sample.entity.BaseEntity;

/**
 *
 * 数据层公共实现接口
 */
public interface BaseService<E extends BaseEntity, D extends BaseDTO> {
  /**
   * DTO 转 entity
   * 
   * @param DTO
   * @return
   */
  E asEntity(D dto);

  /**
   * enitiy 转DTO
   * 
   * @param entity
   * @return
   */
  D asDTO(E entity);

  /**
   * 插入数据
   */
  boolean insert(D dto);

  /**
   * 更新数据
   */
  boolean update(D dto);

  /**
   * 根据ID删除数据
   */
  boolean deleteByPrimaryKey(Long id);

  /**
   * 删除数据
   */
  boolean delete(D dto);

  /**
   * 根据id查询单个记录
   */
  D findByPrimaryKey(Long id);

  /**
   * 根据对象查询单个记录
   */
  D findByEntity(D dto);

  /**
   * 根据对象查询多条记录
   */
  List<? extends BaseDTO> findByListEntity(D dto);

  /**
   * 查询所有记录
   */
  List<? extends BaseDTO> findAll();

  /**
   * 根据对象查询信息
   */
  Object findByObject(Object obj);
}
