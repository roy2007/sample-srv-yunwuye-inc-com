package com.yunwuye.sample.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Service;
import com.yunwuye.sample.client.service.AccountUserService;
import com.yunwuye.sample.dao.IAccountUserDAO;
import com.yunwuye.sample.dto.AccountUserDTO;
import com.yunwuye.sample.result.Result;
import com.yunwuye.sample.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Roy
 *
 * @date 2020年8月15日-下午3:26:29
 */
@Slf4j
@Service(group = "accountUserService", interfaceClass = AccountUserService.class, version = "1.0")
@Component
public class AccountUserServiceImpl implements AccountUserService {

  @Resource
    private IAccountUserDAO dao;

  @Override
  public Result<AccountUserDTO> findById(Long id) {
    log.info("AccountUserServiceImpl.findById, the parameters id: {}", id);
        AccountUserDTO targetDTO = dao.findById (id);
    return ResultUtil.createSuccessResult(targetDTO);
  }

}
