package com.yunwuye.sample.common.aop;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.yunwuye.sample.common.base.exception.ValidateException;
import com.yunwuye.sample.common.base.processor.Processor;
import com.yunwuye.sample.common.validator.AbstractBaseValidator;
import com.yunwuye.sample.common.validator.annotation.ParamProcess;
import com.yunwuye.sample.common.validator.annotation.Validator;
import com.yunwuye.sample.common.validator.annotation.Validator.ValidateClass;
import com.yunwuye.sample.common.validator.annotation.Validators;

/**
 *
 * @author Roy
 *
 * @date 2020年6月23日-下午6:18:01
 */
@Configuration
@EnableConfigurationProperties(ValidatorProperties.class)
@Aspect
@ComponentScan({ "com.yunwuye.sample.common.validator" })
public class AspectValidatorAOP {
  private static final Logger logger = LoggerFactory.getLogger(AspectValidatorAOP.class);

  @Autowired
  private ApplicationContext context;

  @Before("@annotation(com.yunwuye.sample.common.validator.annotation.Validators) || @annotation(com.yunwuye.sample.common.validator.annotation.Validator)")
  public void before(JoinPoint pjp) throws ValidateException {

    Signature sig = pjp.getSignature();
    MethodSignature msig = null;
    if (!(sig instanceof MethodSignature)) {
      throw new IllegalArgumentException("该注解只能用于方法");
    }
    msig = (MethodSignature) sig;

    List<Validator> validatorClazzs = new ArrayList<>();

    Validator validator = this.getAnnotation(msig, Validator.class);
    if (validator != null) {
      validatorClazzs.add(validator);
    }
    Validators validators = this.getAnnotation(msig, Validators.class);
    if (validators != null && validators.value().length > 0) {
      for (Validator v : validators.value()) {
        validatorClazzs.add(v);
      }
    }
    if (CollectionUtils.isEmpty(validatorClazzs)) {
      return;
    }

    Map<String, Object> nameWithValueMap = this.generateParameterForValidator(pjp);

    /**
     * 参数处理器，提前处理方法
     */
    ParamProcess paramProcess = this.getAnnotation(msig, ParamProcess.class);
    if (paramProcess != null) {
      for (Class<? extends Processor> processName : paramProcess.value()) {
        Processor instanceClass = context.getBean(processName);
        if (instanceClass == null) {
          // TODO 未定义的类异常
          continue;
        }
        instanceClass.process(nameWithValueMap);
      }
    }

    String empId = null; // TODO 可以通过前端参数传递，也可以通过Session中获取
    for (Validator currValidator : validatorClazzs) {
      Boolean validateResult = this.executor(empId, nameWithValueMap, currValidator, msig);
      if (!validateResult) {
        logger.warn(currValidator.name() + " || " + currValidator.errorMessage());
        throw new ValidateException(currValidator.errorMessage());
      }
    }
  }

  /**
   * 执行校验器的校验处理,后续这里需要封装成Result对象返回
   * 
   * @param empId
   * @param nameWithValueMap
   * @param validator
   * @return
   */
  private boolean executor(String empId, Map<String, Object> nameWithValueMap, Validator validator, MethodSignature msig) {
    Boolean isSuccess = Boolean.TRUE;
    ValidateClass[] validateClazzs = validator.value();
    for (ValidateClass validateClass : validateClazzs) {
      Class<? extends AbstractBaseValidator<? extends Annotation>> validatorClassName = validateClass.validator();
      AbstractBaseValidator<? extends Annotation> baseValidator = context.getBean(validatorClassName);

      Class<? extends Annotation> validatorConfigClass = baseValidator.getGenericClassConfig();
      Annotation validatorConfig = getAnnotation(msig, validatorConfigClass);
      isSuccess = baseValidator.validate(validatorConfig, empId, nameWithValueMap);
      /**
       * 失败就跳出
       */
      if (!isSuccess) {
        break;
      }
    }
    return isSuccess;
  }

  private <T extends Annotation> T getAnnotation(MethodSignature methodSignature, Class<T> annotationClass) {
    return methodSignature.getMethod().getAnnotation(annotationClass);
  }

  private Map<String, Object> generateParameterForValidator(JoinPoint pjp) {
    Map<String, Object> paramMap = null;
    Object[] paramValues = pjp.getArgs();
    if (paramValues != null && paramValues.length > 0) {
      paramMap = new HashMap<>(paramValues.length);
    }
    MethodSignature msig = (MethodSignature) pjp.getSignature();
    String[] paramNames = msig.getParameterNames();

    for (int i = 0, len = paramNames.length; i < len; i++) {
      Object value = paramValues[i];
      String name = paramNames[i];
      if (value instanceof ServletRequest || value instanceof ServletResponse || value instanceof MultipartFile) {
        continue;
      }
      paramMap.put(name, paramValues);
    }
    return paramMap;
  }
}
