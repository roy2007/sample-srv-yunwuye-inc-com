package com.yunwuye.sample.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yunwuye.sample.dal.entity.AccountUser;
import com.yunwuye.sample.dal.mapper.AccountUserMapper;
import com.yunwuye.sample.dao.AccountUserDAO;

/**
 *
 * @author Roy
 *
 * @date 2020年8月15日-下午3:30:54
 */
@Component
public class AccountUserDAOImpl implements AccountUserDAO {

  @Autowired
  private AccountUserMapper accountUserMapper;

  @Override
  public AccountUser findById(Long id) {
    return accountUserMapper.selectByPrimaryKey(id);
  }

}
