package com.samsung.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ExceptionHandlingProxy implements InvocationHandler {

    private Object target;

    public ExceptionHandlingProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(target, args);
        } catch (Exception e) {
            return null;
        }
    }
}
