package com.yunwuye.sample.inner.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yunwuye.sample.dao.IBaseDAO;
import com.yunwuye.sample.dao.IStudentDAO;
import com.yunwuye.sample.dto.BaseDTO;
import com.yunwuye.sample.inner.IStudentInnerService;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Roy
 *
 * @date 2022年7月2日-下午9:51:56
 */
@Service
@Slf4j
public class StudentInnerServiceImpl extends BaseInnerServiceImpl implements IStudentInnerService{

    @Autowired
    private IStudentDAO dao;

    @Override
    protected IBaseDAO<? extends BaseDTO> getDAO () {
        return this.dao;
    }
}
