package com.yunwuye.sample.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yunwuye.sample.annotation.FieldCNName;
import com.yunwuye.sample.enums.CommonResultCode;
import com.yunwuye.sample.exception.CommonException;
import com.yunwuye.sample.result.Result;

public class ParameterValidationUtil {
    private static final Logger logger = LoggerFactory.getLogger(ParameterValidationUtil.class);
    private final static String IS_EMPTY = " is empty !";
    private final static String NOT_EXIST = " properties that do not exist";
    private final static CommonResultCode CHECK_BASIC_PARAM_ERROR = CommonResultCode.CHECK_BASIC_PARAM_ERROR;

    /**
     * 判断对象是否为空，并且如果是必输项但值为空时将抛出异常，检查所有字段
     *
     * @param instance
     */
    private static void isNullThrowException(Object instance) {
        try {
            Field fields[] = instance.getClass().getDeclaredFields();
            for (Field f : fields) {
                int mod = f.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                f.setAccessible(true);
                Object value = f.get(instance);
                f.setAccessible(false);
                if (Objects.isNull(value)) {
                    throw new CommonException(CHECK_BASIC_PARAM_ERROR, f.getName() + IS_EMPTY);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new CommonException(CommonResultCode.SYS_FAIL, e.getMessage());
        }
    }

    /**
     * 判断所有字段是否为空，如果为空抛出异常
     *
     * @param instance
     * @param fieldNames
     *            指定字段校验是否为空
     */
    public static void checkFieldsIsNull(Object instance, List<String> fieldNames) {
        checkFieldsIsNull(instance, fieldNames, null);
    }

    /**
     * 判断所有字段是否为空，如果为空抛出异常
     *
     * @param instance
     * @param fieldNames
     *            指定字段校验是否为空
     * @param parentClass
     *            指定查找父类边界
     */
    public static void checkFieldsIsNull(Object instance, List<String> fieldNames, Class<?> parentClass) {
        if (instance == null) {
            throw new CommonException(CommonResultCode.PARAM_IS_NOT_NULL);
        }
        // 所有字段都不能为空
        if (fieldNames == null) {
            isNullThrowException(instance);
            return;
        }
        // 指定字段是否为空校验
        fieldNames.forEach(fieldName -> {
            try {
                Field field = findInheritedClassField(instance.getClass(), fieldName, parentClass);
                checkIsNull(instance, field);
            } catch (NoSuchFieldException e) {
                throw new CommonException(CHECK_BASIC_PARAM_ERROR, e.getMessage() + NOT_EXIST);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new CommonException(CHECK_BASIC_PARAM_ERROR, fieldName + e.getMessage());
            }
        });
    }

    /**
     * 查找指定域方法对象(包括父类)
     *
     * @param clazz
     *            子类对象
     * @param fieldName
     *            属性字段名称
     * @param parentClass
     *            指定父类，不指定默认Object.class
     * @return field 属性字段方法对象
     * @throws NoSuchFieldException
     *             如果没找到将抛出异常
     */
    private static Field findInheritedClassField(Class<?> clazz, String fieldName, Class<?> parentClass)
            throws NoSuchFieldException {
        if (parentClass == null) {
            parentClass = Object.class;
        }
        Class<?> currentClazz = clazz;
        do {
            try {
                Field field = currentClazz.getDeclaredField(fieldName);
                if (Objects.nonNull(field)) {
                    return field;
                }
            } catch (NoSuchFieldException | SecurityException e) {
                // Nothing. Will try to get field from parentClass
            }
            currentClazz = currentClazz.getSuperclass();
        } while (currentClazz != null && !currentClazz.equals(parentClass));
        throw new NoSuchFieldException(fieldName);
    }

    /**
     * 具体检查方法，如果为空将抛出异常
     *
     * @param instance
     * @param field
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private static void checkIsNull(Object instance, Field fieldObject) throws IllegalArgumentException,
            IllegalAccessException {
        fieldObject.setAccessible(true);
        Object fieldValue = fieldObject.get(instance);
        fieldObject.setAccessible(false);
        if (Objects.isNull(fieldValue)) {
            throw new CommonException(CHECK_BASIC_PARAM_ERROR, fieldObject.getName() + IS_EMPTY);
        }
    }

    /**
     * 对象空值校验器
     *
     * @param targetObject 传入的目标对象
     * @param params 需要校验的参数
     * @return Result success/fail
     */
    public static Result<?> validateParamsBlankAndNull(Object targetObject, String... params)
            throws NoSuchFieldException {
        Class<? extends Object> clazz = targetObject.getClass();
        Integer errorNumber = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (String param : params) {
            param = param.trim();
            Method method = null;
            try {
                // 将param首字母大写
                method = clazz.getMethod("get" + param.substring(0, 1).toUpperCase() + param.substring(1));
            } catch (NoSuchMethodException e) {
                return ResultUtil.createFailResult("参数" + param + "不存在");
            }
            String result = null;
            try {
                // 取得getter执行结果
                result = (String) method.invoke(targetObject);
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
                return ResultUtil.createFailResult("方法不可执行");
            } catch (InvocationTargetException e) {
                logger.error(e.getMessage());
                return ResultUtil.createFailResult("传入对象异常");
            }
            if (result == null || "".equals(result)) {
                FieldCNName fieldName = clazz.getDeclaredField(param).getAnnotation(FieldCNName.class);
                if (fieldName != null) {
                    stringBuilder.append(fieldName.value() + "不能为空，");
                } else {
                    stringBuilder.append(param + "不能为空，");
                }
                errorNumber++;
            }
        }
        return errorNumber == 0 ? ResultUtil.createSuccessResult("校验通过") : ResultUtil.createFailResult(stringBuilder
                .toString());
    }
}
