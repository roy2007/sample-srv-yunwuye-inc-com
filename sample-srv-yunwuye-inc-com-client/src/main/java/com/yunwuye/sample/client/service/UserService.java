package com.yunwuye.sample.client.service;

import java.util.List;
import com.yunwuye.sample.dto.BaseDTO;
import com.yunwuye.sample.dto.UserDTO;
import com.yunwuye.sample.param.user.UserParam;
import com.yunwuye.sample.result.PageResult;
import com.yunwuye.sample.result.Result;
import com.yunwuye.sample.vo.BaseVO;

public interface UserService extends BaseService<BaseDTO, BaseVO>{

    /**
     *
     * @param userParam
     * @return
     */
    public Result<UserDTO> getUserById (Long id);

    /**
     * 根据主键查询用户
     * 
     * @param id
     * @return
     */
    public Result<UserDTO> findById (Long id);

    /**
     * 根据查询条件分页获取用户信息
     * 
     * @param userParam
     * @return
     */
    public PageResult<List<UserDTO>> queryByConditions (UserParam userParam);

    /**
     * 根据主键更新用户信息
     * 
     * @param userDto
     * @return
     */
    public int modifyUserById (UserDTO userDto);

}
