package com.yunwuye.sample.client.service;

import java.util.List;
import com.yunwuye.sample.dto.BaseDTO;
import com.yunwuye.sample.dto.StudentDTO;
import com.yunwuye.sample.vo.BaseVO;

/**
 * 学生接口
 */
public interface StudentService extends BaseService<BaseDTO, BaseVO>{

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
}
