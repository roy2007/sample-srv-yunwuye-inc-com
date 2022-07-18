package com.yunwuye.sample.common.utils;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import com.alibaba.fastjson.JSON;

/**
 *
 * @author Roy
 *
 * @date 2022年7月17日-上午9:17:13
 */
public class GenericObjectConversionUtils{

    private static final Logger logger = LoggerFactory.getLogger (GenericObjectConversionUtils.class);

    /**
     * 将List转换成目标列表
     *
     * @param sourceList
     * @param targetClass
     *            如果是内部类，需要传入外部类
     * @return
     */
    public static <F, T> List<T> copyProperties (List<F> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty (sourceList)) {
            return null;
        }
        List<T> tList = new ArrayList<> ();
        for (F f : sourceList) {
            T t = copyProperties (f, null, targetClass);
            tList.add (t);
        }
        return tList;
    }

    /**
     * 将List转换成目标列表
     *
     * @param sourceList
     * @param targetClass
     *            如果是内部类，需要传入外部类
     * @param outClass
     *            可以为空，如果targetClass是内部类，此参数不能为空
     * @return
     */
    public static <F, T> List<T> copyProperties (List<F> sourceList, Class<?> outClass, Class<T> targetClass) {
        if (CollectionUtils.isEmpty (sourceList)) {
            return null;
        }
        List<T> tList = new ArrayList<> ();
        for (F f : sourceList) {
            T t = copyProperties (f, outClass, targetClass);
            tList.add (t);
        }
        return tList;
    }

    /**
     * 单个实体转换
     *
     * @param source
     * @param target
     * @return
     */
    public static <F, T> T copyProperties (F source, Class<T> target) {
        if (source == null || target == null) {
            return null;
        }
        return copyProperties (source, null, target);
    }

    /**
     * description：内部类对应单个实体转换
     *
     * @param source
     * @param target
     * @param outClass
     * @return
     */
    @SuppressWarnings ("unchecked")
    public static <F, T> T copyProperties (F source, Class<?> outClass, Class<T> target) {
        Object targetInstance = null;
        if (source == null || target == null) {
            return null;
        }
        try {
            if (outClass == null) {
                targetInstance = target.newInstance ();
            } else {
                targetInstance = target.getDeclaredConstructors ()[0].newInstance (outClass.newInstance ());
            }
        } catch (Exception e) {
            logger.warn ("[copyProperties] 实体对象转换异常, 参数 source={},target={}", JSON.toJSONString (source),
                            JSON.toJSONString (targetInstance));
            throw new RuntimeException ("实体对象转换异常,异常消息请查看日志记录！");
        }
        BeanUtils.copyProperties (source, targetInstance);
        return (T) targetInstance;
    }
}
