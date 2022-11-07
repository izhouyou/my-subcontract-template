package com.izhouy.template.common.aspect;

import com.izhouy.template.common.anno.Log;
import com.izhouy.template.common.constant.LogAnnotConstants;
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
import java.util.*;

/**
 * title LogAspect
 * Description 切面处理类，操作日志异常日志记录处理
 * CreateDate 2022/10/13 22:06
 *
 * @author izhouy
 */
@Aspect
@Component
public class LogAspect {

    /**
     * 操作版本号
     * 项目启动时从命令行传入，例如：java -jar xxx.war --version=201902
     */
    private String version = "1.0.0";

    /**
     * 统计请求的处理时间
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Resource
    private LogInfoService logInfoService;

    @Resource
    private LogErrorInfoService logErrorInfoService;

    /**
     * @methodName：logPoinCut
     * @description：设置操作日志切入点 记录操作日志 在注解的位置切入代码
     * @author：izhouy
     * @dateTime：2021/11/18 14:22
     * @Params： []
     * @Return： void
     * @editNote：
     */
    @Pointcut("@annotation(com.izhouy.template.common.anno.Log)")
    public void logPoinCut() {
    }

    /**
     * @methodName：exceptionLogPoinCut
     * @description：设置操作异常切入点记录异常日志 扫描所有controller包下操作
     * @author：izhouy
     * @dateTime：2021/11/18 14:22
     * @Params： []
     * @Return： void
     * @editNote：
     */
    @Pointcut("execution(* com.izhouy.template.controller..*.*(..))")
    public void exceptionLogPoinCut() {
    }

    @Before("logPoinCut()")
    public void doBefore() {
        // 接收到请求，记录请求开始时间
        startTime.set(System.currentTimeMillis());
    }

    /**
     * @methodName：doAfterReturning
     * @description：正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     * @author：izhouy
     * @dateTime：2021/11/18 14:21
     * @Params： [joinPoint, keys]
     * @Return： void
     * @editNote：
     */
    @AfterReturning(value = "logPoinCut()", returning = "keys")
    public void doAfterReturning(JoinPoint joinPoint, Object keys) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        LogInfo logInfo = LogInfo.builder().build();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取操作
            Log log = method.getAnnotation(Log.class);
            if (Objects.nonNull(log)) {
                logInfo.setModule(log.modul());
                logInfo.setType(log.type());
                logInfo.setMessage(log.desc());
            }
            // 请求的方法名
            logInfo.setMethod(className + "." + method.getName());
            // 请求参数
            logInfo.setReqParam(JacksonUtils.toJsonString(converMap(request.getParameterMap())));
            // 返回结果
            logInfo.setResParam(JacksonUtils.toJsonString(keys));
            // 请求用户ID
            logInfo.setUserId(LogAnnotConstants.USER_ID);
            // 请求用户名称
            logInfo.setUserName(LogAnnotConstants.USER_NAME);
            // 请求IP
            logInfo.setIp(IpUtils.getIpAddress(request));
            // 请求URI
            logInfo.setUri(request.getRequestURI());
            // 创建时间
            logInfo.setCreateTime(new Date());
            // 操作版本
            logInfo.setVersion(version);
            // 耗时
            logInfo.setTakeUpTime(System.currentTimeMillis() - startTime.get());
            logInfoService.save(logInfo);
            startTime.remove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @methodName：doAfterThrowing
     * @description：异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     * @author：izhouy
     * @dateTime：2021/11/18 14:23
     * @Params： [joinPoint, e]
     * @Return： void
     * @editNote：
     */
    @AfterThrowing(pointcut = "exceptionLogPoinCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            //获取用户请求方法的参数并序列化为JSON格式字符串
            Map<String, Object> rtnMap = new HashMap<>();
            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                for (int i = 0; i < joinPoint.getArgs().length; i++) {
                    rtnMap.put("param" + i, joinPoint.getArgs()[i]);
                }
            }
            logErrorInfoService.save(LogErrorInfo.builder()
                    // 请求参数
                    .reqParam(JacksonUtils.toJsonString(rtnMap))
                    // 请求方法名
                    .method(className + "." + method.getName())
                    // 异常名称
                    .name(e.getClass().getName())
                    // 异常信息
                    .message(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()))
                    // 操作员ID
                    .userId(LogAnnotConstants.USER_ID)
                    // 操作员名称
                    .userName(LogAnnotConstants.USER_NAME)
                    // 操作URI
                    .uri(request.getRequestURI())
                    // 操作员IP
                    .ip(IpUtils.getIpAddress(request))
                    // 版本号
                    .version(version)
                    // 发生异常时间
                    .createTime(new Date())
                    .build()
            );
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /**
     * @methodName：converMap
     * @description：转换request 请求参数
     * @author：izhouy
     * @dateTime：2021/11/18 14:12
     * @Params： [paramMap]
     * @Return： java.util.Map<java.lang.String, java.lang.String>
     * @editNote：
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<>(paramMap.size());
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * @methodName：stackTraceToString
     * @description：转换异常信息为字符串
     * @author：izhouy
     * @dateTime：2021/11/18 14:12
     * @Params： [exceptionName, exceptionMessage, elements]
     * @Return： java.lang.String
     * @editNote：
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "<br/>");
        }
        String message = exceptionName + ":" + exceptionMessage + "<br/>" + strbuff.toString();
        return message;
    }
}