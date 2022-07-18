package com.yunwuye.sample.service;

import com.yunwuye.sample.common.base.result.Result;
import com.yunwuye.sample.dto.AccountUserDTO;

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

    public Result<AccountUserDTO> findByUsername (String loginUserName);

    public Result<AccountUserDTO> findOneWithAuthoritiesByEmailIgnoreCase (String loginUserName);

    public Result<AccountUserDTO> findOneWithAuthoritiesByUsername (String lowercaseLoginUserName);
}
