package com.izhouy.template.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author izhou
 * @path com.izhouy.template.common.annotation
 * @methodName ExceptionHandleAnno
 * @description 用于异常处理的注解
 * @dateTime 2022/11/7 16:09
 * @editNote
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionHandleAnno { }