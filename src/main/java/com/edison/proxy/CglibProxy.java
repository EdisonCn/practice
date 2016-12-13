package com.edison.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 文件名:com.edison.proxy.CglibProxy
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-07-19
 * 修改记录:
 */
public class CglibProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }
}
