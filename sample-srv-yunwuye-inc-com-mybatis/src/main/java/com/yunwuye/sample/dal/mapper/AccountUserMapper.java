package com.yunwuye.sample.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yunwuye.sample.dal.entity.AccountUser;
import com.yunwuye.sample.dal.entity.AccountUserExample;

@Mapper
public interface AccountUserMapper {
    long countByExample(AccountUserExample example);

    int deleteByExample(AccountUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AccountUser record);

    int insertSelective(AccountUser record);

    List<AccountUser> selectByExample(AccountUserExample example);

    AccountUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AccountUser record, @Param("example") AccountUserExample example);

    int updateByExample(@Param("record") AccountUser record, @Param("example") AccountUserExample example);

    int updateByPrimaryKeySelective(AccountUser record);

    int updateByPrimaryKey(AccountUser record);
}