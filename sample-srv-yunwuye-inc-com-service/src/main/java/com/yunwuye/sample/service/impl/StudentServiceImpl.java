package com.yunwuye.sample.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.yunwuye.sample.common.base.dto.BaseDTO;
import com.yunwuye.sample.configuration.DataSourceContext;
import com.yunwuye.sample.dao.entity.StudentEntity;
import com.yunwuye.sample.dao.mapper.base.BaseMapper;
import com.yunwuye.sample.dao.mapper.cluster.StudentMapper;
import com.yunwuye.sample.dto.StudentDTO;
import com.yunwuye.sample.entity.BaseEntity;
import com.yunwuye.sample.service.StudentService;

/**
 * 用户操作实现类
 */
@Slf4j
@Service(group = "studentService", interfaceClass = StudentService.class, version = "1.0")
@Component
public class StudentServiceImpl extends BaseServiceImpl implements StudentService {
  @Autowired
  private StudentMapper studentMapper;

  @Override
  protected BaseMapper<StudentEntity> getMapper() {
    return this.studentMapper;
  }

  @Override
  public StudentEntity asEntity(BaseDTO dto) {
    StudentEntity e = new StudentEntity();
    BeanUtils.copyProperties(dto, e);
    return e;
  }

  @Override
  public StudentDTO asDTO(BaseEntity entity) {
    StudentDTO dto = new StudentDTO();
    BeanUtils.copyProperties(entity, dto);
    return dto;
  }

  @Override
  public StudentDTO findById(Long id) {
    DataSourceContext.setRouterKey("second");
    StudentEntity e = studentMapper.findByPrimaryKey(id);
    log.info("return StudentEntity:{}", e);
    return asDTO(e);
  }

}
