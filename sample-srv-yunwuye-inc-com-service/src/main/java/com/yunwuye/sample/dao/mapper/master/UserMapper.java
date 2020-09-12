package com.yunwuye.sample.dao.mapper.master;

import org.apache.ibatis.annotations.Mapper;

import com.yunwuye.sample.dao.entity.UserEntity;
import com.yunwuye.sample.dao.mapper.base.BaseMapper;

/**
 * UserMapper
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
