package com.yunwuye.sample.dao.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yunwuye.sample.dal.entity.AccountUser;
import com.yunwuye.sample.dal.mapper.AccountUserMapper;
import com.yunwuye.sample.dao.IAccountUserDAO;
import com.yunwuye.sample.dto.AccountUserDTO;

/**
 *
 * @author Roy
 *
 * @date 2020年8月15日-下午3:30:54
 */
@Component
public class AccountUserDAOImpl implements IAccountUserDAO{

  @Autowired
  private AccountUserMapper accountUserMapper;

  @Override
    public AccountUserDTO findById (Long id) {
        AccountUser entity = accountUserMapper.selectByPrimaryKey (id);
        AccountUserDTO dto = null;
        if (entity != null) {
            dto = new AccountUserDTO ();
            BeanUtils.copyProperties (entity, dto);
        }
        return dto;
    }

}
