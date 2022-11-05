package com.yunwuye.sample.dao.impl;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yunwuye.sample.dal.entity.AccountUser;
import com.yunwuye.sample.dal.entity.AccountUserExample;
import com.yunwuye.sample.dal.mapper.AccountUserMapper;
import com.yunwuye.sample.dao.AccountUserDAO;
import com.yunwuye.sample.dto.AccountUserDTO;

/**
 *
 * @author Roy
 *
 * @date 2020年8月15日-下午3:30:54
 */
@Component
public class AccountUserDAOImpl implements AccountUserDAO{

    @Autowired
    private AccountUserMapper accountUserMapper;

    @Override
    public AccountUser findById (Long id) {
        return accountUserMapper.selectByPrimaryKey (id);
    }

    @Override
    public AccountUserDTO findByUserName (String lowercaseLoginUserName) {
        AccountUserExample example = new AccountUserExample ();
        example.createCriteria ().andLoginNameEqualTo (lowercaseLoginUserName).andIsDeletedEqualTo (0L);
        List<AccountUser> entitys = accountUserMapper.selectByExample (example);
        if (entitys == null || entitys.isEmpty ()) {
            return null;
        }
        AccountUserDTO dto = new AccountUserDTO ();
        BeanUtils.copyProperties (entitys.get (0), dto);
        return dto;
    }
}
