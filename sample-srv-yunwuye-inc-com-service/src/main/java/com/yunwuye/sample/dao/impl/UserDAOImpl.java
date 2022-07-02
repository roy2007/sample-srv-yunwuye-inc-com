package com.yunwuye.sample.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yunwuye.sample.dal.entity.ext.UserEntity;
import com.yunwuye.sample.dal.mapper.ext.BaseMapper;
import com.yunwuye.sample.dal.mapper.ext.master.UserMapper;
import com.yunwuye.sample.dao.IUserDAO;
import com.yunwuye.sample.dto.BaseDTO;
import com.yunwuye.sample.dto.UserDTO;
import com.yunwuye.sample.util.ListUtil;

/**
 *
 * @author Roy
 *
 * @date 2022年7月2日-下午3:53:34
 */
@Component
public class UserDAOImpl extends BaseDAOImpl implements IUserDAO{

    @Autowired
    private UserMapper userMapper;

    @Override
    protected BaseMapper<UserEntity> getMapper () {
        return this.userMapper;
    }

    @Override
    public List<UserDTO> queryByConditions (UserDTO userDTO) {
        List<BaseDTO> baseDTOs = super.findByListEntity (userDTO);
        List<UserDTO> dtos = ListUtil.copyProperties (baseDTOs, UserDTO.class);
        return dtos;
    }

    @Override
    public int modifyUserById (UserDTO userDTO) {
        return super.update (userDTO);
    }

}
