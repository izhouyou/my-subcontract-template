package com.izhouy.template.common.aspect;

import com.izhouy.template.common.handler.ControllerLogHandler;
import com.izhouy.template.common.handler.MethodAdviceHandler;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * title ControllerLogAspect
 * Description 切面处理类，操作日志异常日志记录处理
 * CreateDate 2022/10/13 22:06
 *
 * @author izhouy
 */
@Aspect
@Component
public class ControllerLogAspect extends BaseMethodAspect {

    /**
     * 指定切点（处理打上 ControllerLogAnno 的方法）
     */
    @Override
    @Pointcut("@annotation(com.izhouy.template.common.anno.ControllerLogAnno)")
    protected void pointcut() { }

    /**
     * 指定该切面绑定的方法切面处理器为 ControllerLogHandler
     */
    @Override
    protected Class<? extends MethodAdviceHandler<?>> getAdviceHandlerType() {
        return ControllerLogHandler.class;
    }

}