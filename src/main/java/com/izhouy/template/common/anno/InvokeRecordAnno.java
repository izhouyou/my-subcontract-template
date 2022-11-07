package com.izhouy.template.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author izhou
 * @path com.izhouy.template.common.annotation
 * @methodName InvokeRecordAnno
 * @description 用于产生调用记录的注解，会记录下方法的出入参、调用时长
 * @dateTime 2022/11/7 15:46
 * @editNote
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InvokeRecordAnno {

    /**
     * 调用说明
     */
    String value() default "";
}
