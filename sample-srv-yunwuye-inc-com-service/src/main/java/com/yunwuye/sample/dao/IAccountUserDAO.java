package com.yunwuye.sample.dao;

import com.yunwuye.sample.dto.AccountUserDTO;

/**
 *
 * @author Roy
 *
 * @date 2020年8月15日-下午3:28:40
 */
public interface IAccountUserDAO{

    /**
     * 根据主键查询用户账号信息
     * 
     * @param id
     * @return
     */
    public AccountUserDTO findById (Long id);

    /**
     * 根据用户名查询用户账号信息
     * 
     * @param username
     * @return
     */
    public AccountUserDTO findByUserName (String username);
}
