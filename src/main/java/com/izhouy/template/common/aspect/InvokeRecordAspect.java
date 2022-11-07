package com.izhouy.template.common.aspect;

import com.izhouy.template.common.handler.InvokeRecordHandler;
import com.izhouy.template.common.handler.MethodAdviceHandler;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author izhou
 * @path com.izhouy.template.common.aop
 * @methodName InvokeRecordAspect
 * @description 方法切面的实现
 * @dateTime 2022/11/7 15:48
 * @editNote
 */
@Aspect
@Order(1)
@Component
public class InvokeRecordAspect extends BaseMethodAspect {

    /**
     * 指定切点（处理打上 InvokeRecordAnno 的方法）
     */
    @Override
    @Pointcut("@annotation(com.izhouy.template.common.anno.InvokeRecordAnno)")
    protected void pointcut() { }

    /**
     * 指定该切面绑定的方法切面处理器为 InvokeRecordHandler
     */
    @Override
    protected Class<? extends MethodAdviceHandler<?>> getAdviceHandlerType() {
        return InvokeRecordHandler.class;
    }
}
