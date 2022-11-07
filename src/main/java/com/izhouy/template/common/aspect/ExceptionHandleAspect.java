package com.izhouy.template.common.aspect;

import com.izhouy.template.common.handler.ExceptionHandleHandler;
import com.izhouy.template.common.handler.MethodAdviceHandler;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author izhou
 * @path com.izhouy.template.common.aspect
 * @methodName ExceptionHandleAspect
 * @description 方法异常切面
 * @dateTime 2022/11/7 16:10
 * @editNote
 */
@Aspect
@Order(10)
@Component
public class ExceptionHandleAspect extends BaseMethodAspect {

    /**
     * 指定切点（处理打上 ExceptionHandleAnno 的方法）
     */
    @Override
    @Pointcut("@annotation(com.izhouy.template.common.anno.ExceptionHandleAnno)")
    protected void pointcut() { }

    /**
     * 指定该切面绑定的方法切面处理器为 ExceptionHandleHandler
     */
    @Override
    protected Class<? extends MethodAdviceHandler<?>> getAdviceHandlerType() {
        return ExceptionHandleHandler.class;
    }
}
