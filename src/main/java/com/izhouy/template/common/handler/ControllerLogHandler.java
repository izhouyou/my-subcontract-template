package com.izhouy.template.common.handler;

import com.izhouy.template.common.anno.ControllerLogAnno;
import com.izhouy.template.common.anno.InvokeRecordAnno;
import com.izhouy.template.common.anno.Log;
import com.izhouy.template.common.constant.LogAnnotConstants;
import com.izhouy.template.domain.LogErrorInfo;
import com.izhouy.template.domain.LogInfo;
import com.izhouy.template.service.LogErrorInfoService;
import com.izhouy.template.service.LogInfoService;
import com.izhouy.template.util.IpUtils;
import info.jiatu.jtlsp.common.response.Result;
import info.jiatu.jtlsp.common.util.JacksonUtils;
import info.jiatu.jtlsp.common.util.ResultGeneratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
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
 * @author izhou
 * @path com.izhouy.template.common.handler
 * @methodName ControllerLogHandler
 * @description 接口层日志处理器
 * @dateTime 2022/11/7 17:19
 * @editNote
 */
@Component
public class ControllerLogHandler extends BaseMethodAdviceHandler<Result> {

    /**
     * 操作版本号
     * 项目启动时从命令行传入，例如：java -jar xxx.war --version=201902
     */
    private String version = "1.0.0";

    @Resource
    private LogInfoService logInfoService;

    @Resource
    private LogErrorInfoService logErrorInfoService;

    /**
     * 记录方法出入参和调用时长
     */
    @Override
    public void onComplete(ProceedingJoinPoint joinPoint, long startTime, boolean permitted, boolean thrown, Object result) {
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
            ControllerLogAnno log = method.getAnnotation(ControllerLogAnno.class);
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
            logInfo.setResParam(JacksonUtils.toJsonString(result));
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
            logInfo.setTakeUpTime(System.currentTimeMillis() - startTime);
            logInfoService.save(logInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getMethodDesc(ProceedingJoinPoint point) {
        Method targetMethod = getTargetMethod(point);
        // 获得方法上的 InvokeRecordAnno
        InvokeRecordAnno anno = targetMethod.getAnnotation(InvokeRecordAnno.class);
        String description = anno.value();

        // 如果没有指定方法说明，那么使用默认的方法说明
        if (StringUtils.isBlank(description)) {
            description = super.getMethodDesc(point);
        }

        return description;
    }

    /**
     * 抛出异常时的处理
     */
    @Override
    public void onThrow(ProceedingJoinPoint joinPoint, Throwable e) {
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
     * 抛出异常时的返回值
     */
    @Override
    public Result getOnThrow(ProceedingJoinPoint point, Throwable e) {
        // 获得返回值类型
        Class<?> returnType = getTargetMethod(point).getReturnType();
        // 如果返回值类型是 Map 或者其子类
        if (Result.class.isAssignableFrom(returnType)) {
            return ResultGeneratorUtils.error("调用出错");
        }
        return null;
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
}
