package com.yunwuye.sample.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yunwuye.sample.common.base.dto.BaseDTO;
import com.yunwuye.sample.common.base.result.PageResult;
import com.yunwuye.sample.common.base.result.Result;
import com.yunwuye.sample.common.util.ResultUtil;
import com.yunwuye.sample.dao.entity.UserEntity;
import com.yunwuye.sample.dao.mapper.base.BaseMapper;
import com.yunwuye.sample.dao.mapper.master.UserMapper;
import com.yunwuye.sample.dto.UserDTO;
import com.yunwuye.sample.entity.BaseEntity;
import com.yunwuye.sample.param.user.UserParam;
import com.yunwuye.sample.service.UserService;

/**
 *
 * @author Roy
 *
 * @date 2020年5月10日-下午7:02:07
 */
@Slf4j
@Service(group = "userService", interfaceClass = UserService.class, version = "1.0")
@Component
public class UserServiceImpl extends BaseServiceImpl implements UserService {

  @Autowired
  private Executor threadPoolTaskExecutor;

  @Autowired
  private UserMapper userMapper;

  @Override
  protected BaseMapper<UserEntity> getMapper() {
    return this.userMapper;
  }

  @Override
  public UserDTO getUserById(Long id) {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(1000L);
    userDTO.setUsername("roys-rui");
    userDTO.setAddress("beijin");
    log.info("return userDTO:{}", userDTO);
    return userDTO;
  }

  @Override
  public UserEntity asEntity(BaseDTO dto) {
    UserEntity target = new UserEntity();
    BeanUtils.copyProperties(dto, target);
    return target;
  }

  @Override
  public UserDTO asDTO(BaseEntity entity) {
    UserDTO target = new UserDTO();
    BeanUtils.copyProperties(entity, target);
    return target;
  }

  @Override
  public Result<UserDTO> findById(Long id) {
    UserEntity e = userMapper.findByPrimaryKey(id);
    log.info("return UserEntity:{}", e);
    return ResultUtil.createSuccessResult(asDTO(e));
  }

  @Override
  public PageResult<List<UserDTO>> queryByConditions(UserParam userParam) {
    // UserEntity target = new UserEntity();
    Set<CompletableFuture<Integer>> allIntTask = cancelAllTask();
    log.info("int set:" + JSON.toJSONString(allIntTask));

    Set<CompletableFuture<String>> allStringTask = getAllTaskResult();
    log.info("String set:" + JSON.toJSONString(allStringTask));
    return ResultUtil.createPageSuccess();
  }

  private Set<CompletableFuture<Integer>> cancelAllTask() {
    Set<CompletableFuture<Integer>> tasks = new HashSet<>();

    for (int i = 0; i < 100; i++) {
      final int id = i;
      CompletableFuture<Integer> c = CompletableFuture.supplyAsync(() -> {
        System.out.println("Running~~~~~~~~~: " + id);
        if (id == 40)
          throw new RuntimeException("Exception from: " + id);
        return id;
      }, threadPoolTaskExecutor).whenComplete((v, ex) -> {
        if (ex != null) {
          System.out.println(Thread.currentThread().getName() + " Shutting down. " + ex.getMessage());
          ((ExecutorService) threadPoolTaskExecutor).shutdownNow();
          System.out.println("shutdown.");
        }
      });
      tasks.add(c);
    }

    try {
      CompletableFuture.allOf(tasks.stream().toArray(CompletableFuture[]::new)).join();
    } catch (Exception e) {
      System.out.println("Got async exception: " + e);
    } finally {
      System.out.println("DONE");
    }
    return tasks;
  }

  private Set<CompletableFuture<String>> getAllTaskResult() {
    @SuppressWarnings("serial")
    List<UserDTO> userDTOs = new ArrayList<UserDTO>() {
      {
        UserDTO dto = new UserDTO();
        dto.setAddress("华强北");
        add(dto);
      }
      {
        UserDTO dto = new UserDTO();
        dto.setAddress("益田假日广场");
        add(dto);
      }
      {
        UserDTO dto = new UserDTO();
        dto.setAddress("香港九龙城");
        add(dto);
      }
      {
        UserDTO dto = new UserDTO();
        dto.setAddress("京东商城");
        add(dto);
      }
    };
    @SuppressWarnings("unchecked")
    final CompletableFuture<String>[] completableFutureArray = userDTOs.stream()
        .map(dto -> CompletableFuture.supplyAsync(() -> dto.getAddress(), threadPoolTaskExecutor))
        .map(future -> future.thenApply(address -> {
          System.out.println("1111 " + address);
          return future;
        })).map(future -> future.thenCompose(address -> CompletableFuture.supplyAsync(() -> {
          String.format("this address: %s", address);
          return address;
        }, threadPoolTaskExecutor))).map(f -> f.thenAccept(System.out::println))
        .map(f -> f.thenApply(s -> s + " world")).toArray(size -> new CompletableFuture[size]);

    CompletableFuture.allOf(completableFutureArray).join();
    Set<CompletableFuture<String>> tasks = new HashSet<>();

    try {
      for (CompletableFuture<String> f : completableFutureArray) {
        System.out.println(f.get());
        tasks.add(f);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    return tasks;

  }
}
