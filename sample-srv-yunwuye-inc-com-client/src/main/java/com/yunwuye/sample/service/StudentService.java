package com.yunwuye.sample.service;

import java.util.List;
import com.yunwuye.sample.common.base.dto.BaseDTO;
import com.yunwuye.sample.common.base.result.PageResult;
import com.yunwuye.sample.dto.StudentDTO;
import com.yunwuye.sample.entity.BaseEntity;

/**
 * 学生接口
 */
public interface StudentService extends BaseService<BaseEntity, BaseDTO>{

    /**
     * 根据主键查询用户
     * 
     * @param id
     * @return
     */
    public StudentDTO findById (Long id);

    /**
     * 批量更新学生,同步保持事务
     * 
     * @param dtos
     * @return
     */
    public Integer batchSyncUpdateStudentById (List<StudentDTO> dtos);

    /**
     * 批量更新学生,异步无法保证事务
     * 
     * @param dtos
     * @return
     */
    public Integer batchAsyncUpdateStudentById (List<StudentDTO> dtos);

    /**
     * 根据id更新学生
     * 
     * @param dto
     * @return
     */
    public Integer modifyStudentById (StudentDTO dto);

    /**
     * 批量更新学生,自定义事件
     * 
     * @param dtos
     * @return
     */
    public Integer batchUpdateStudentByIdForWithTransaction (List<StudentDTO> dtos);

    /**
     * 分页按条件模糊查询列表
     * @param dto
     * @param offset
     * @param limit
     * @return
     */
    public PageResult<List<StudentDTO>> queryPageByCondition (StudentDTO dto, int offset, int limit);

    /**
     * 根据条件查询总条数
     * 
     * @param dto
     * @return
     */
    public Integer countByDTO (StudentDTO dto);
}
