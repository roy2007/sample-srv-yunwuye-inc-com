package com.yunwuye.sample.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yunwuye.sample.common.base.dto.BaseDTO;
import com.yunwuye.sample.configuration.DataSourceContext;
import com.yunwuye.sample.dao.entity.StudentEntity;
import com.yunwuye.sample.dao.mapper.base.BaseMapper;
import com.yunwuye.sample.dao.mapper.cluster.StudentMapper;
import com.yunwuye.sample.dto.StudentDTO;
import com.yunwuye.sample.dto.UserDTO;
import com.yunwuye.sample.entity.BaseEntity;
import com.yunwuye.sample.service.StudentService;
import com.yunwuye.sample.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户操作实现类
 */
@Slf4j
@Service (group = "studentService", interfaceClass = StudentService.class, version = "1.0")
@Component
public class StudentServiceImpl extends BaseServiceImpl implements StudentService{

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private Executor      threadPoolTaskExecutor;

    @Resource
    private UserService   userService;

    @Override
    protected BaseMapper<StudentEntity> getMapper () {
        return this.studentMapper;
    }

    @Override
    public StudentEntity asEntity (BaseDTO dto) {
        StudentEntity e = new StudentEntity ();
        BeanUtils.copyProperties (dto, e);
        return e;
    }

    @Override
    public StudentDTO asDTO (BaseEntity entity) {
        StudentDTO dto = new StudentDTO ();
        BeanUtils.copyProperties (entity, dto);
        return dto;
    }

    @Override
    public StudentDTO findById (Long id) {
        DataSourceContext.setRouterKey ("second");
        StudentEntity e = studentMapper.findByPrimaryKey (id);
        log.info ("return StudentEntity:{}", e);
        return asDTO (e);
    }

    @Transactional (rollbackFor = Exception.class)
    @Override
    public Integer batchAsyncUpdateStudentById (List<StudentDTO> dtos) {
        int count = 0;
        try {
            UserDTO userDto = new UserDTO();
            userDto.setId (2L);
            userDto.setName ("user name");
            userDto.setAge (99);
            this.modifyUserInfoById (userDto);

            @SuppressWarnings ("unchecked")
            CompletableFuture<Integer>[] fs = dtos.stream ().map (dto -> CompletableFuture.supplyAsync ( () -> {
                return modifyStudentById (dto);
            }, threadPoolTaskExecutor).whenComplete ( (r, e) -> {
                if (e != null) {
                    log.error ("The update student info fail :{}, the DTO:{}", e.toString (),
                                    JSON.toJSONString (dto));
                } else {
                    log.info ("---------------------------------The update sucess student dto:{}",
                                    JSON.toJSONString (dto));
                }
            })).toArray (CompletableFuture[]::new);
            CompletableFuture.allOf (fs).join ();
        } catch (Exception e) {
            log.error ("The update student info fail :{}", e.toString ());
            throw new RuntimeException (e);
        }
        return count;
    }

    @Transactional (rollbackFor = Exception.class)
    @Override
    public Integer batchSyncUpdateStudentById (List<StudentDTO> dtos) {
        int count = 0;
        try {
            UserDTO userDto = new UserDTO ();
            userDto.setId (2L);
            userDto.setName ("user name");
            userDto.setAge (99);
            this.modifyUserInfoById (userDto);

            for (StudentDTO dto : dtos) {
                int currentCout = modifyStudentById (dto);
                count = count + currentCout;
            }
        } catch (Exception e) {
            log.error ("The update student info fail :{}", e.toString ());
            throw new RuntimeException (e);
        }
        return count;
    }

    // @Transactional (rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public Integer modifyStudentById (StudentDTO dto) {
        /**如果需要切换数据源，可以打开以下代码*/
        DataSourceContext.setRouterKey ("second");
        BaseEntity entity = asEntity (dto);
        try {
            studentMapper.update (entity);
        } catch (Exception e) {
            e.printStackTrace ();
            throw new RuntimeException (e);
        }
        return 1;
    }

    private Integer modifyUserInfoById (UserDTO userDto) {
        return userService.modifyUserById (userDto);
    }
}
