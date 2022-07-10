package com.yunwuye.sample.client.service;

import com.yunwuye.sample.dto.AccountUserDTO;
import com.yunwuye.sample.result.Result;

/**
 *
 * @author Roy
 *
 * @date 2020年8月15日-下午3:17:07
 */
public interface AccountUserService {

  /**
   *
   * @param userParam
   * @return
   */
  public Result<AccountUserDTO> findById(Long id);

    /**
     * 
     * @param userName
     * @return
     */
    public Result<AccountUserDTO> findByUsername (String userName);

    /***
     * 判断用户名重复
     * @param loginName
     * @return
     */
    public Integer existsByUsername (String loginName);

    /**
     * 保存数据
     * @param dto
     * @return
     */
    public Integer save (AccountUserDTO dto);

    /**
     * 
     * @param username
     */
    public void deleteByUsername (String username);

    /**
     * 根据邮箱查询
     * 
     * @param email
     * @return
     */
    public Result<AccountUserDTO> findOneWithAuthoritiesByEmailIgnoreCase (String email);

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    public Result<AccountUserDTO> findOneWithAuthoritiesByUsername (String username);
}
