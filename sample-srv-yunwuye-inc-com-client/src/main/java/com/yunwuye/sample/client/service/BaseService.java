package com.yunwuye.sample.client.service;

import java.util.List;
import com.yunwuye.sample.dto.BaseDTO;
import com.yunwuye.sample.result.Result;
import com.yunwuye.sample.vo.BaseVO;

/**
 *
 * 数据层公共实现接口
 */
public interface BaseService<D extends BaseDTO, V extends BaseVO> {

    /**
     * VO 转 DTO
     * 
     * @param DTO
     * @return
     */
    BaseDTO asDTO (V VO);

    /**
     * DTO 转VO
     * 
     * @param entity
     * @return
     */
    V asVO (D dto);

    /**
     * 插入数据
     */
    Result<Integer> insert (V VO);

    /**
     * 更新数据
     */
    Result<Integer> update (V VO);

    /**
     * 根据ID删除数据
     */
    Result<Integer> deleteByPrimaryKey (Long id);

    /**
     * 删除数据
     */
    Result<Integer> delete (D dto);

    /**
     * 根据id查询单个记录
     */
    Result<? extends BaseDTO> findByPrimaryKey (Long id);

    /**
     * 根据对象查询单个记录
     */
    Result<? extends BaseDTO> findByEntity (D dto);

    /**
     * 根据对象查询多条记录
     */
    Result<List<? extends BaseDTO>> findByList (D dto);

    /**
     * 查询所有记录
     */
    Result<List<? extends BaseDTO>> findAll ();

    /**
     * 根据对象查询信息
     */
    Result<Object> findByObject (Object obj);
}
