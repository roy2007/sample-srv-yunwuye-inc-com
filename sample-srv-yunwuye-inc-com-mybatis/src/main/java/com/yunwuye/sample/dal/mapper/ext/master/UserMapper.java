package com.yunwuye.sample.dal.mapper.ext.master;

import org.apache.ibatis.annotations.Mapper;
import com.yunwuye.sample.dal.entity.ext.UserEntity;
import com.yunwuye.sample.dal.mapper.ext.BaseMapper;


/**
 * UserMapper
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
