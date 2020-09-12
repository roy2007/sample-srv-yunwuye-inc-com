package com.yunwuye.sample.service.impl;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.yunwuye.sample.common.base.result.Result;
import com.yunwuye.sample.common.util.ResultUtil;
import com.yunwuye.sample.dal.entity.AccountUser;
import com.yunwuye.sample.dao.AccountUserDAO;
import com.yunwuye.sample.dto.AccountUserDTO;
import com.yunwuye.sample.service.AccountUserService;

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
  private AccountUserDAO dao;

  @Override
  public Result<AccountUserDTO> findById(Long id) {
    log.info("AccountUserServiceImpl.findById, the parameters id: {}", id);
    AccountUser entity = dao.findById(id);
    AccountUserDTO targetDTO = new AccountUserDTO();
    BeanUtils.copyProperties(entity, targetDTO);
    return ResultUtil.createSuccessResult(targetDTO);
  }

}
