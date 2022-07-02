package com.yunwuye.sample.inner;

import java.util.List;
import com.yunwuye.sample.dto.BaseDTO;


/**
 *
 * 数据层公共实现接口
 */
public interface IBaseInnerService<D extends BaseDTO> {


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
    List<? extends BaseDTO> findByList (BaseDTO dto);

    /**
     * 查询所有记录
     */
    List<? extends BaseDTO> findAll ();

    /**
     * 根据对象查询信息
     */
    Object findByObject (Object obj);

}
