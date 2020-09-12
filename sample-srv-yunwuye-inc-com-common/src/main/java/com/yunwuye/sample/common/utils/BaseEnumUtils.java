package com.yunwuye.sample.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import com.yunwuye.sample.common.BaseEnum;

/**
 *
 * @author Roy
 *
 * @date 2020年7月5日-上午11:34:58
 */
public class BaseEnumUtils {
  /**
   * 
   * 将BaseEnum.getCode()作为Key,BaseEnum.getDesc()作为value,存放在Map中并返回
   * 
   * @param <T>
   * 
   * @param values
   * 
   * @return
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static <T extends BaseEnum> LinkedHashMap toMap(T[] values) {
    LinkedHashMap map = new LinkedHashMap();
    for (BaseEnum item : values) {
      map.put(item.getCode(), item.getDesc());
    }
    return map;
  }

  /**
   * 
   * 获得枚举的code
   * 
   *
   * 
   * @param kv
   *          枚举值
   * 
   * @return code
   */
  @SuppressWarnings("rawtypes")
  public static <T extends BaseEnum> Object getCode(T kv) {
    if (kv == null) {
      return null;
    }
    return kv.getCode();
  }

  /**
   * 
   * 获得枚举列表的所有code
   * 
   *
   * 
   * @param kv
   * 
   * @return
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static <T extends BaseEnum> List getCodes(Iterable<T> kv) {
    if (kv == null) {
      return null;
    }
    List result = new ArrayList();
    for (BaseEnum e : kv) {
      if (e != null) {
        result.add(e.getCode());
      }
    }
    return result;
  }

  /**
   * 
   * 将枚举的数组转换成列表
   * 
   *
   * 
   * @param kv
   * 
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static <T extends BaseEnum> List getCodes(T[] kv) {
    if (kv == null) {
      return null;
    }
    return getCodes(Arrays.asList(kv));
  }

  /**
   * 
   * 获得枚举描述信息
   * 
   *
   * 
   * @param <T>
   * 
   * @param kv
   * 
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static <T extends BaseEnum> String getDesc(T kv) {
    if (kv == null) {
      return null;
    }
    return kv.getDesc();
  }

  /**
   * 
   * 获得枚举名称
   * 
   *
   * 
   * @param kv
   * 
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static <T extends Enum> String getName(T kv) {
    if (kv == null) {
      return null;
    }
    return kv.name();
  }

  /**
   * 
   * 根据code查找得到Enum
   * 
   * @param code
   * 
   * @return
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static <T extends BaseEnum> T getByCode(Object code, Class<? extends BaseEnum> enumClass) {
    return (T) getByCode(code, enumClass.getEnumConstants());
  }

  /**
   * 
   * 根据code查找得到Enum
   * 
   * @param code
   * 
   * @param values
   * 
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static <T extends BaseEnum> T getByCode(Object code, T[] values) {
    if (code == null) {
      return null;
    }
    for (T item : values) {
      if (item.getCode().equals(code)) {
        return item;
      }
    }
    return null;
  }

  /**
   * 
   * 根据code查找得到Enum
   * 
   * @param code
   * 
   * @param enumClass
   * 
   * @return
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static <T extends BaseEnum> T getRequiredByCode(Object code, Class<? extends BaseEnum> enumClass) {
    return (T) getRequiredByCode(code, enumClass.getEnumConstants());
  }

  /**
   * 
   * 根据code得到Enum,找不到则抛异常
   * 
   * @param <T>
   * 
   * @param code
   * 
   * @param values
   * 
   * @return
   * 
   * @throws IllegalArgumentException
   *           根据code得到Enum,找不到则抛异常
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static <T extends BaseEnum> T getRequiredByCode(Object code, T[] values) throws IllegalArgumentException {
    if (code == null) {
      return null;
    }
    BaseEnum v = getByCode(code, values);
    if (v == null) {
      if (values.length > 0) {
        String className = values[0].getClass().getName();
        throw new IllegalArgumentException("not found " + className + " value by code:" + code);
      } else {
        throw new IllegalArgumentException("not found Enum by code:" + code);
      }
    }
    return (T) v;
  }
}
