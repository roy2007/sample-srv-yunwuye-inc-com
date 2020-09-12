package com.yunwuye.sample.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

/**
 *
 * @author Roy
 * @date 2020-05-02
 */
public class ListUtil {
    private static final Logger logger = LoggerFactory.getLogger(ListUtil.class);

    /**
     * 将单个对象转换成列表 如果单个对象是null，则返回空列表，不会返回null
     *
     * @param t
     *            单个对象
     * @return 和对象同类型的列表
     */
    public static <T> List<T> convertBySingleObject(T t) {
        List<T> tList = new ArrayList<T>();
        if (t != null) {
            tList.add(t);
        }
        return tList;
    }

    /**
     * 将list中字符串转成逗号分隔字符串
     *
     * @param list
     * @return
     */
    public static String convertByListString(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        return String.join(",", list);
    }

    /**
     * 根据分割符，将字符串分割为 List
     *
     * @param list
     *            原始字符串
     * @param separator
     *            分割符
     * @return 分割后生成的List集合
     */
    public static List<String> splitToList(String list, String separator) {
        if (StringUtils.isBlank(list)) {
            return new ArrayList<String>();
        }
        String[] pieces = StringUtils.split(list, separator);
        return Arrays.asList(pieces);
    }

    /**
     * 默认根据逗号【,】将字符串分割为 List
     *
     * @param list
     *            原始字符串
     * @return 分割后生成的List集合
     */
    public static List<String> splitToList(String list) {
        if (StringUtils.isBlank(list)) {
            return new ArrayList<String>();
        }
        String[] pieces = StringUtils.split(list, ",");
        return Arrays.asList(pieces);
    }

    /**
     * 将List转换成目标列表
     *
     * @param sourceList
     * @param targetClass
     *            如果是内部类，需要传入外部类
     * @return
     */
    public static <F, T> List<T> copyProperties(List<F> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return null;
        }
        List<T> tList = new ArrayList<>();
        for (F f : sourceList) {
            T t = copyProperties(f, null, targetClass);
            tList.add(t);
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
    public static <F, T> List<T> copyProperties(List<F> sourceList, Class<?> outClass, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return null;
        }
        List<T> tList = new ArrayList<>();
        for (F f : sourceList) {
            T t = copyProperties(f, outClass, targetClass);
            tList.add(t);
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
    public static <F, T> T copyProperties(F source, Class<T> target) {
        if (source == null || target == null) {
            return null;
        }
        return copyProperties(source, null, target);
    }

    /**
     * description：内部类对应单个实体转换
     *
     * @param source
     * @param target
     * @param outClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <F, T> T copyProperties(F source, Class<?> outClass, Class<T> target) {
        Object targetInstance = null;
        if (source == null || target == null) {
            return null;
        }
        try {
            if (outClass == null) {
                targetInstance = target.newInstance();
            } else {
                targetInstance = target.getDeclaredConstructors()[0].newInstance(outClass.newInstance());
            }
        } catch (Exception e) {
            logger.warn("[copyProperties] 实体对象转换异常, 参数 source={},target={}", JSON.toJSONString(source),
                    JSON.toJSONString(targetInstance));
            throw new RuntimeException("实体对象转换异常,异常消息请查看日志记录！");
        }
        BeanUtils.copyProperties(source, targetInstance);
        return (T) targetInstance;
    }

    /**
     * 将大list 分批次成小list
     *
     * @param dataList
     *            大list
     * @param batchSize
     *            批次处理list条数
     * @return
     */
    public static <T> List<List<T>> listSplit(List<T> dataList, int batchSize) {
        List<List<T>> splitlist = new ArrayList<>();
        if (CollectionUtils.isEmpty(dataList)) {
            return splitlist;
        }
        if (dataList.size() <= batchSize) {
            splitlist.add(dataList);
            return splitlist;
        }
        List<T> batchList = new ArrayList<>();
        // 分批次处理
        for (int i = 0; i < dataList.size(); i++) {
            batchList.add(dataList.get(i));
            if (batchSize == batchList.size() || i == dataList.size() - 1) {
                splitlist.add(batchList);
                batchList = new ArrayList<>();
            }
        }
        return splitlist;
    }

    /**
     * 对list集合进行分页处理
     *
     * @param list
     *            总条数
     * @param rows
     *            每页行数，默认15行
     * @param pageSize
     *            当前页面，默认 1页
     * @return
     */
    public static <T> List<T> listPaging(List<T> list, int rows, int pageSize) {
        List<T> pageList = null;
        if (CollectionUtils.isEmpty(list)) {
            return pageList;
        }
        if (rows == 0) {
            rows = 15;
        }
        if (pageSize == 0) {
            pageSize = 1;
        }
        int total = list.size();
        pageList = list.subList(rows * (pageSize - 1), ((rows * pageSize) > total ? total : (rows * pageSize)));
        return pageList;
    }
}