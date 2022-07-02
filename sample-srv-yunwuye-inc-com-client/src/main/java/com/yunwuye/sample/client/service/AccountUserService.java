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
}
