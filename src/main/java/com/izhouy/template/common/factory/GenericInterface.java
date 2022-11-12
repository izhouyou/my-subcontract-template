package com.izhouy.template.common.factory;

/**
 * @author izhou
 * @path com.izhouy.template.factory
 * @methodName GenericInterface
 * @description 策略模式泛型接口
 * @dateTime 2022/11/10 14:31
 * @editNote
 */
public interface GenericInterface<E> {
    E getType();
}
