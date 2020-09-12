package com.yunwuye.sample.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.yunwuye.sample.common.base.enums.CommonResultCode;
import com.yunwuye.sample.common.base.exception.CommonException;
import com.yunwuye.sample.common.base.result.Result;

/**
 *
 * @author Roy
 *
 * @date 2020年5月3日-下午11:02:15
 */
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
@Service
public class AspectServiceExceptionAOP {
    private static final Logger logger = LoggerFactory.getLogger(AspectServiceExceptionAOP.class);

    // 多包路径时可以通过||后面追加包路径
    @Around("execution(* com.yunwuye.web.service.impl.*.*(..))")
    public Object serviceLogAndException(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        StringBuilder sbd = new StringBuilder();
        Long startTime = System.currentTimeMillis();
        try {
            result = pjp.proceed();
        } catch (Throwable t) {
            String errorMessage = t.toString();
            logger.error("service aop catch throwable error: {}", errorMessage);
            try {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } catch (NoTransactionException te) {
                logger.error("NoTransactionException error: {}", te.getMessage());
                sbd.append("NoTransactionException error |");
            }
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            result = signature.getReturnType().newInstance();
            if (t instanceof CommonException) {
                logger.error("CommonException error: {}", errorMessage);
                if (result instanceof Result<?>) {
                    CommonException commonException = (CommonException) t;
                    ((Result<?>) result).setCode(commonException.code.getCode());
                    ((Result<?>) result).setMessage(commonException.toString());
                    ((Result<?>) result).setSuccess(Boolean.FALSE);
                }
                sbd.append(this.getClass().getName()).append("CommonException error |").append(t.getMessage());
            } else if (t instanceof Exception) {
                logger.error("Exception error: {}", errorMessage);
                if (result instanceof Result<?>) {
                    ((Result<?>) result).setCode(CommonResultCode.BIZ_SEREVICE_FAIL.getCode());
                    ((Result<?>) result).setMessage(String.format(CommonResultCode.BIZ_SEREVICE_FAIL.getDesc(),
                            errorMessage));
                    ((Result<?>) result).setSuccess(Boolean.FALSE);
                }
                sbd.append(this.getClass().getName()).append("Exception error |").append(t.getMessage());
            } else {
                logger.error("Unknow Exception error: {}", errorMessage);
                if (result instanceof Result<?>) {
                    ((Result<?>) result).setCode(CommonResultCode.UNFORESEEN_EXCEPTION.getCode());
                    ((Result<?>) result).setMessage(String.format(CommonResultCode.UNFORESEEN_EXCEPTION.getDesc(),
                            errorMessage));
                    ((Result<?>) result).setSuccess(Boolean.FALSE);
                }
                sbd.append(this.getClass().getName()).append("Unknow error |").append(t.getMessage());
            }
        } finally {
            Long elapses = System.currentTimeMillis() - startTime;
            String interfaceName = pjp.getTarget().getClass().getInterfaces()[0].getSimpleName();
            Object[] args = { interfaceName, pjp.getSignature(), pjp.getArgs(), JSON.toJSONString(result), elapses };
            logger.debug("interface:{} | method:{} | param:{} | result:{} | elapses:{} ms", args);
            sbd.delete(0, sbd.length());
        }
        return result;
    }
}
