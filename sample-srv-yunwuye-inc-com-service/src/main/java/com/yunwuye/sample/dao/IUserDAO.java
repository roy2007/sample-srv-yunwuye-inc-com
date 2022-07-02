package com.yunwuye.sample.dao;

import java.util.List;
import com.yunwuye.sample.dto.BaseDTO;
import com.yunwuye.sample.dto.UserDTO;

/**
 *
 * @author Roy
 *
 * @date 2022年7月2日-下午3:50:46
 */
public interface IUserDAO extends IBaseDAO<BaseDTO>{

    /**
    * 根据查询条件分页获取用户信息
    * 
    * @param userParam
    * @return
    */
    public List<UserDTO> queryByConditions (UserDTO userDTO);

    /**
    * 根据主键更新用户信息
    * 
    * @param userDto
    * @return
    */
    public int modifyUserById (UserDTO userDTO);
}
