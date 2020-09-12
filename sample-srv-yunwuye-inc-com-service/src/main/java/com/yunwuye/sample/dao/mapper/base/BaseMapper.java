package com.yunwuye.sample.dao.mapper.base;

import java.util.List;

import com.yunwuye.sample.entity.BaseEntity;

/**
 * BaseMapper
 */
public interface BaseMapper<E extends BaseEntity> {

  /**
   * 单条新增插入数据
   */
  int insert(BaseEntity entity) throws Exception;

  /**
   * 批量新增据插入数据
   */
  int insertBatch(List<E> entityList) throws Exception;

  /**
   * 更新数据
   */
  void update(BaseEntity entity) throws Exception;

  /**
   * 根据ID删除数据
   */
  void deleteByPrimaryKey(Long id) throws Exception;

  /**
   * 删除数据
   */
  void delete(BaseEntity entity) throws Exception;

  /**
   * 根据id查询单个记录
   */
  E findByPrimaryKey(Long id);

  /**
   * 根据对象查询单个记录
   */
  E findByEntity(BaseEntity entity);

  /**
   * 根据对象查询多条记录
   */
  List<E> findByListEntity(BaseEntity entity);

  /**
   * 查询所有记录
   */
  List<E> findAll();

  /**
   * 根据对象查询信息
   */
  Object findByObject(Object obj);
}
