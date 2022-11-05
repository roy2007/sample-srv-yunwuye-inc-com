package com.yunwuye.sample.dao;

import com.yunwuye.sample.dal.entity.AccountUser;
import com.yunwuye.sample.dto.AccountUserDTO;

/**
 *
 * @author Roy
 *
 * @date 2020年8月15日-下午3:28:40
 */
public interface AccountUserDAO{

    /**
     * 根据主键查询用户账号信息
     * 
     * @param id
     * @return
     */
    public AccountUser findById (Long id);

    public AccountUserDTO findByUserName (String lowercaseLoginUserName);
}
