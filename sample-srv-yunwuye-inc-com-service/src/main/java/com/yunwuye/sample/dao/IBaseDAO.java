package com.yunwuye.sample.dao;

import java.util.List;
import com.yunwuye.sample.dal.entity.ext.BaseEntity;
import com.yunwuye.sample.dto.BaseDTO;

/**
 *
 * @author Roy
 *
 * @date 2022年7月2日-下午4:03:51
 */
public interface IBaseDAO<D extends BaseDTO> {

    /**
     * DTO 转 entity
     * 
     * @param DTO
     * @return
     */
    BaseEntity asEntity (BaseDTO dto);

    /**
     * enitiy 转DTO
     * 
     * @param entity
     * @return
     */
    D asDTO (BaseEntity entity);

    /**
     * 插入数据
     */
    int insert (BaseDTO dto);

    /**
     * 更新数据
     */
    int update (BaseDTO dto);

    /**
     * 根据ID删除数据
     */
    int deleteByPrimaryKey (Long id);

    /**
     * 删除数据
     */
    int delete (BaseDTO dto);

    /**
     * 根据id查询单个记录
     */
    D findByPrimaryKey (Long id);

    /**
     * 根据对象查询单个记录
     */
    D findByEntity (BaseDTO dto);

    /**
     * 根据对象查询多条记录
     */
    List<? extends BaseDTO> findByListEntity (BaseDTO dto);

    /**
     * 查询所有记录
     */
    List<? extends BaseDTO> findAll ();

    /**
     * 根据对象查询信息
     */
    Object findByObject (Object obj);

}
