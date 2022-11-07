package com.izhouy.template.common.aspect;

import com.izhouy.template.common.anno.Log;
import com.izhouy.template.common.constant.LogAnnotConstants;
import com.izhouy.template.common.handler.ControllerLogHandler;
import com.izhouy.template.common.handler.InvokeRecordHandler;
import com.izhouy.template.common.handler.MethodAdviceHandler;
import com.izhouy.template.domain.LogErrorInfo;
import com.izhouy.template.domain.LogInfo;
import com.izhouy.template.service.LogErrorInfoService;
import com.izhouy.template.service.LogInfoService;
import com.izhouy.template.util.IpUtils;
import info.jiatu.jtlsp.common.util.JacksonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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