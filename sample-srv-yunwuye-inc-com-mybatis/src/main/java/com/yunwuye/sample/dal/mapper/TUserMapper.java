package com.yunwuye.sample.dal.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yunwuye.sample.dal.entity.TUser;

@Mapper
public interface TUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);
}