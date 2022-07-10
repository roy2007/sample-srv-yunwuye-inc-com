package com.yunwuye.sample.inner.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yunwuye.sample.dao.IBaseDAO;
import com.yunwuye.sample.dao.IUserDAO;
import com.yunwuye.sample.dto.BaseDTO;
import com.yunwuye.sample.inner.IUserInnerService;


/**
 *
 * @author Roy
 *
 * @date 2022年7月2日-下午8:57:57
 */
@Service
public class UserInnerServiceImpl extends BaseInnerServiceImpl implements IUserInnerService{

    @Autowired
    private IUserDAO dao;

    @Override
    protected IBaseDAO<BaseDTO> getDAO () {
        return dao;
    }
}
