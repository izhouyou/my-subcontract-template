package com.izhouy.template.common.handler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author izhou
 * @path com.izhouy.template.common.handler
 * @methodName ExceptionHandleHandler
 * @description 实现方法异常增强处理器
 * @dateTime 2022/11/7 16:09
 * @editNote
 */
@Component
public class ExceptionHandleHandler extends BaseMethodAdviceHandler<Object> {

    /**
     * 抛出异常时的处理
     */
    @Override
    public void onThrow(ProceedingJoinPoint point, Throwable e) {
        super.onThrow(point, e);
        // 发送异常到邮箱或者钉钉的逻辑
    }

    /**
     * 抛出异常时的返回值
     */
    @Override
    public Object getOnThrow(ProceedingJoinPoint point, Throwable e) {
        // 获得返回值类型
        Class<?> returnType = getTargetMethod(point).getReturnType();

        // 如果返回值类型是 Map 或者其子类
        if (Map.class.isAssignableFrom(returnType)) {
            Map<String, Object> result = new HashMap<>(4);
            result.put("success", false);
            result.put("message", "调用出错");

            return result;
        }

        return null;
    }
}