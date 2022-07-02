package com.yunwuye.sample.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yunwuye.sample.dal.entity.ext.BaseEntity;
import com.yunwuye.sample.dal.mapper.ext.BaseMapper;
import com.yunwuye.sample.dal.mapper.ext.cluster.StudentMapper;
import com.yunwuye.sample.dao.IStudentDAO;

/**
 *
 * @author Roy
 *
 * @date 2022年7月2日-下午9:54:29
 */
@Component
public class StudentDAOImpl extends BaseDAOImpl implements IStudentDAO{

    @Autowired
    private StudentMapper studentMapper;

    @Override
    protected BaseMapper<? extends BaseEntity> getMapper () {
        return this.studentMapper;
    }
}
