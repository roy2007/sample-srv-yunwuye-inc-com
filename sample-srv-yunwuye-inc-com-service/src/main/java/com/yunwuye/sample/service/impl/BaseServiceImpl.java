package com.yunwuye.sample.service.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yunwuye.sample.common.base.dto.BaseDTO;
import com.yunwuye.sample.common.util.ListUtil;
import com.yunwuye.sample.dao.entity.StudentEntity;
import com.yunwuye.sample.dao.entity.UserEntity;
import com.yunwuye.sample.dao.mapper.base.BaseMapper;
import com.yunwuye.sample.entity.BaseEntity;
import com.yunwuye.sample.service.BaseService;

/**
 * @Description: 数据层公共实现类
 *
 * @author xub
 * @param <E>
 * @param <E>
 * @param <D>
 * @date 2018/7/26 下午6:25
 */
public abstract class BaseServiceImpl implements BaseService<BaseEntity, BaseDTO> {
  private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

  protected abstract BaseMapper<? extends BaseEntity> getMapper();

  @Override
  public boolean insert(BaseDTO dto) {
    boolean falg = false;
    BaseEntity entity = asEntity(dto);
    try {
      getMapper().insert(entity);
      falg = true;
    } catch (Exception e) {
      logger.error("新增" + getClassName(entity) + "失败!原因是:", e);
    }
    return falg;
  }

  @Override
  public boolean update(BaseDTO dto) {
    boolean falg = false;
    BaseEntity entity = null;
    try {
      entity = asEntity(dto);
      getMapper().update(entity);
      falg = true;
    } catch (Exception e) {
      logger.error("更新" + getClassName(entity) + "失败!原因是:", e);
    }
    return falg;
  }

  @Override
  public boolean deleteByPrimaryKey(Long id) {
    boolean falg = false;
    try {
      getMapper().deleteByPrimaryKey(id);
      falg = true;
    } catch (Exception e) {
      logger.error("id:" + id + "删除失败!原因是:", e);
    }
    return falg;
  }

  @Override
  public boolean delete(BaseDTO dto) {
    boolean falg = false;
    BaseEntity entity = asEntity(dto);
    try {
      getMapper().delete(entity);
      falg = true;
    } catch (Exception e) {
      logger.error("删除" + getClassName(entity) + "失败!原因是:", e);
    }
    return falg;
  }

  @Override
  public BaseDTO findByPrimaryKey(Long id) {
    BaseEntity entity = null;
    try {
      entity = getMapper().findByPrimaryKey(id);
    } catch (Exception e) {
      logger.error("id:" + id + "查询失败!原因是:", e);
    }
    return this.asDTO(entity);
  }

  @Override
  public BaseDTO findByEntity(BaseDTO dto) {
    BaseEntity entity = asEntity(dto);
    BaseDTO rntDTO = null;
    try {
      rntDTO = this.asDTO(getMapper().findByEntity(entity));
    } catch (Exception e) {
      logger.error("查询" + getClassName(entity) + "失败!原因是:", e);
    }
    return rntDTO;
  }

  @Override
  public List<BaseDTO> findByListEntity(BaseDTO dto) {
    List<BaseDTO> list = null;
    BaseEntity entity = asEntity(dto);
    try {
      Page<?> page = PageHelper.startPage(1, 2);
      System.out.println(getClassName(entity) + "设置第一页两条数据!");
      List<? extends BaseEntity> entitys = getMapper().findByListEntity(entity);
      list = ListUtil.copyProperties(entitys, BaseDTO.class);
      System.out.println("总共有:" + page.getTotal() + "条数据,实际返回:" + list.size() + "两条数据!");
    } catch (Exception e) {
      logger.error("查询" + getClassName(entity) + "失败!原因是:", e);
    }
    return list;
  }

  @Override
  public List<? extends BaseDTO> findAll() {
    List<BaseDTO> list = null;
    try {
      List<? extends BaseEntity> entitys = getMapper().findAll();
      list = ListUtil.copyProperties(entitys, BaseDTO.class);
    } catch (Exception e) {
      logger.error("查询失败!原因是:", e);
    }
    return list;
  }

  @Override
  public Object findByObject(Object obj) {
    Object result = null;
    try {
      result = getMapper().findByObject(obj);
    } catch (Exception e) {
      logger.error("查询" + obj + "失败!原因是:", e);
    }
    return result;
  }

  private String getClassName(BaseEntity entity) {
    String str = "";
    if (entity instanceof UserEntity) {
      str = "User";
    } else if (entity instanceof StudentEntity) {
      str = "Studnet";
    }
    return str;
  }

  /**
   * 通过常规反射形式 DTO对象转换为实体对象。如命名不规范或其他原因导致失败。
   * 
   * @param t
   *          源转换的对象
   * @param e
   *          目标转换的对象
   */
  public static <T, E> void transalte(T t, E e) {
    Method[] tms = t.getClass().getDeclaredMethods();
    Method[] tes = e.getClass().getDeclaredMethods();
    for (Method m1 : tms) {
      if (m1.getName().startsWith("get")) {
        String mNameSubfix = m1.getName().substring(3);
        String forName = "set" + mNameSubfix;
        for (Method m2 : tes) {
          if (m2.getName().equals(forName)) {
            // 如果类型一致，或者m2的参数类型是m1的返回类型的父类或接口
            boolean canContinue = m2.getParameterTypes()[0].isAssignableFrom(m1.getReturnType());
            if (canContinue) {
              try {
                m2.invoke(e, m1.invoke(t));
                break;
              } catch (Exception ex) {
                logger.debug("DTO to Entity fail!");
                ex.printStackTrace();
              }
            }
          }
        }
      }
    }
  }
}
