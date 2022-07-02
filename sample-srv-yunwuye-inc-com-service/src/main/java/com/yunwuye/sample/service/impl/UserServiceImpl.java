package com.yunwuye.sample.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yunwuye.sample.client.service.UserService;
import com.yunwuye.sample.dto.BaseDTO;
import com.yunwuye.sample.dto.UserDTO;
import com.yunwuye.sample.inner.IUserInnerService;
import com.yunwuye.sample.param.user.UserParam;
import com.yunwuye.sample.result.PageResult;
import com.yunwuye.sample.result.Result;
import com.yunwuye.sample.util.ResultUtil;
import com.yunwuye.sample.vo.BaseVO;
import com.yunwuye.sample.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Roy
 *
 * @date 2020年5月10日-下午7:02:07
 */
@Slf4j
@Service (group = "userService", interfaceClass = UserService.class, version = "1.0")
public class UserServiceImpl implements UserService{

    @Autowired
    private Executor          threadPoolTaskExecutor;

    @Autowired
    private IUserInnerService innerService;

    @Override
    public Result<UserDTO> getUserById (Long id) {
        UserDTO userDTO = new UserDTO ();
        userDTO.setId (1000L);
        userDTO.setUsername ("roys-rui");
        userDTO.setAddress ("beijin");
        log.info ("return userDTO:{}", userDTO);
        UserDTO findedDTO = (UserDTO) innerService.findByPrimaryKey (id);
        return Result.with (findedDTO == null ? userDTO : findedDTO);
    }

    @Override
    public Result<UserDTO> findById (Long id) {
        UserDTO findedDTO = (UserDTO) innerService.findByPrimaryKey (id);
        log.info ("return UserEntity:{}", findedDTO);
        return ResultUtil.createSuccessResult (findedDTO);
    }

    @Override
    public PageResult<List<UserDTO>> queryByConditions (UserParam userParam) {
        // UserEntity target = new UserEntity();
        Set<CompletableFuture<Integer>> allIntTask = cancelAllTask ();
        log.info ("int set:" + JSON.toJSONString (allIntTask));
        Set<CompletableFuture<String>> allStringTask = getAllTaskResult ();
        log.info ("String set:" + JSON.toJSONString (allStringTask));
        return ResultUtil.createPageSuccess ();
    }

    private Set<CompletableFuture<Integer>> cancelAllTask () {
        Set<CompletableFuture<Integer>> tasks = new HashSet<> ();
        for (int i = 0; i < 100; i++) {
            final int id = i;
            CompletableFuture<Integer> c = CompletableFuture.supplyAsync ( () -> {
                System.out.println ("Running~~~~~~~~~: " + id);
                if (id == 40)
                    throw new RuntimeException ("Exception from: " + id);
                return id;
            }, threadPoolTaskExecutor).whenComplete ( (v, ex) -> {
                if (ex != null) {
                    System.out.println (Thread.currentThread ().getName () + " Shutting down. " + ex.getMessage ());
                    ((ExecutorService) threadPoolTaskExecutor).shutdownNow ();
                    System.out.println ("shutdown.");
                }
            });
            tasks.add (c);
        }
        try {
            CompletableFuture.allOf (tasks.stream ().toArray (CompletableFuture[]::new)).join ();
        } catch (Exception e) {
            System.out.println ("Got async exception: " + e);
        } finally {
            System.out.println ("DONE");
        }
        return tasks;
    }

    private Set<CompletableFuture<String>> getAllTaskResult () {
        @SuppressWarnings ("serial")
        List<UserDTO> userDTOs = new ArrayList<UserDTO> (){

            {
                UserDTO dto = new UserDTO ();
                dto.setAddress ("华强北");
                add (dto);
            }
            {
                UserDTO dto = new UserDTO ();
                dto.setAddress ("益田假日广场");
                add (dto);
            }
            {
                UserDTO dto = new UserDTO ();
                dto.setAddress ("香港九龙城");
                add (dto);
            }
            {
                UserDTO dto = new UserDTO ();
                dto.setAddress ("京东商城");
                add (dto);
            }
        };
        @SuppressWarnings ("unchecked")
        final CompletableFuture<String>[] completableFutureArray = userDTOs.stream ()
                        .map (dto -> CompletableFuture.supplyAsync ( () -> dto.getAddress (), threadPoolTaskExecutor))
                        .map (future -> future.thenApply (address -> {
                            System.out.println ("1111 " + address);
                            return future;
                        })).map (future -> future.thenCompose (address -> CompletableFuture.supplyAsync ( () -> {
                            String.format ("this address: %s", address);
                            return address;
                        }, threadPoolTaskExecutor))).map (f -> f.thenAccept (System.out::println))
                        .map (f -> f.thenApply (s -> s + " world")).toArray (size -> new CompletableFuture[size]);
        CompletableFuture.allOf (completableFutureArray).join ();
        Set<CompletableFuture<String>> tasks = new HashSet<> ();
        try {
            for (CompletableFuture<String> f : completableFutureArray) {
                System.out.println (f.get ());
                tasks.add (f);
            }
        } catch (InterruptedException e) {
            e.printStackTrace ();
        } catch (ExecutionException e) {
            e.printStackTrace ();
        }
        return tasks;
    }

    @Override
    public int modifyUserById (UserDTO userDto) {
        return innerService.update (userDto);
    }

    @Override
    public UserDTO asDTO (BaseVO VO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserVO asVO (BaseDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<Integer> update (BaseVO VO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<List<? extends BaseDTO>> findByList (BaseDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<Integer> insert (BaseVO VO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<Integer> deleteByPrimaryKey (Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<Integer> delete (BaseDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<UserDTO> findByPrimaryKey (Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<UserDTO> findByEntity (BaseDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<List<? extends BaseDTO>> findAll () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<Object> findByObject (Object obj) {
        // TODO Auto-generated method stub
        return null;
    }
}
