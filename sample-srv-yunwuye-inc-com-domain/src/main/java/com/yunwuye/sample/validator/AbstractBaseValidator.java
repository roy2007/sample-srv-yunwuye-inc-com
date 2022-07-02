
package com.yunwuye.sample.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yunwuye.sample.enums.CommonResultCode;
import com.yunwuye.sample.exception.ValidateException;

/**
 *
 * @author Roy
 *
 * @date 2020年6月23日-上午11:29:11
 */
public abstract class AbstractBaseValidator<ValidatorConfig> {
    private static final String DOT_SEPARATOR = "."; // TODO 可以改成#
    private static final Logger logger = LoggerFactory.getLogger(AbstractBaseValidator.class);

    /*  *//**
     * //TODO 可改进点： 1.上下文对象可以线程中共享，方便后续重复方法通过上下文获取内容,并缓存结果 2.可参考虑AOP方法获取参数值，不用定义具体的参数名称
     * 3.定义异常消息提示及说明，如果没有定义异常消息就统一抛出默认异常 消息
     * 
     * @param config
     *            上下文对象
     * @param empId
     *            登录人员工号
     * @param params
     *            传入参数
     * @return true-表过通过， false-表示不通过
     */
    /*
     * public abstract Boolean validate(ValidatorConfig config, String empId, Map<String, Object> paramMap);
     */

    /**
     * 获得自定义校验上下文 config,调用时具体校验器依赖的上下文
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public Class<ValidatorConfig> getGenericClassConfig() {
        return (Class<ValidatorConfig>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    /**
     * 获取参数对象中指定参数值
     * 
     * @param paramName
     * @param paramMap
     * @return
     * @throws Throwable
     */
    protected Object getParameterValueByName(String paramName, Map<String, Object> paramMap) throws Throwable {
        String[] paramNames = paramName.split(Pattern.quote(DOT_SEPARATOR));
        if (paramNames.length == 1) {
            return paramMap.getOrDefault(paramName, null);
        }

        if (paramNames.length == 2 && paramMap.containsKey(paramNames[0])) {
            return getPropertyValueByObject(paramNames[0], paramNames[1]);
        }

        throw new ValidateException(CommonResultCode.PARAM_IS_NOT_NULL, paramName);
    }

    /**
     * 
     * @param object
     * @param fieldName
     * @return
     * @throws Throwable
     */
    private Object getPropertyValueByObject(Object object, String fieldName) throws Throwable {
        if (object == null || StringUtils.isBlank(fieldName)) {
            return null;
        }
        Class<?> currentClazz = object.getClass();
        String methodName = StringUtils.capitalize(fieldName);
        do {
            try {
                Method method = currentClazz.getMethod("get" + methodName);
                return method.invoke(object);
            } catch (IllegalAccessException e) {
                logger.info("非法访问方法 ,", e.getMessage());
            } catch (NoSuchMethodException e) {
                logger.info("无效的方法,", e.getMessage());
            } catch (SecurityException e) {
                // Nothing. Will try to get field from parentClass
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
            currentClazz = currentClazz.getSuperclass();
        } while (currentClazz != null && !currentClazz.equals(Object.class));
        return null;
    }

    /**
     * //TODO 可改进点： 1.上下文对象可以线程中共享，方便后续重复方法通过上下文获取内容,并缓存结果 2.可参考虑AOP方法获取参数值，不用定义具体的参数名称
     * 3.定义异常消息提示及说明，如果没有定义异常消息就统一抛出默认异常 消息
     * 
     * @param config
     *            上下文对象
     * @param empId
     *            登录人员工号
     * @param params
     *            传入参数
     * @return true-表过通过， false-表示不通过
     */
    public abstract Boolean validate(Annotation validatorConfig, String empId, Map<String, Object> nameWithValueMap);
}
