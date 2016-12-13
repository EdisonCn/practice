package com.edison.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 文件名:com.edison.proxy.JDKProxy
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-07-14
 * 修改记录:
 */
public class JDKProxy {

    public static void main(String[] args) {
        InterfaceA interfaceA = (InterfaceA) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{InterfaceA.class}, new InvocationHandlerImpl(new ClassA()));

        System.out.println(interfaceA.getHello("xiaowang"));
    }

    public static interface InterfaceA {
        public String getHello(String name);
    }

    public static class ClassA implements InterfaceA {
        @Override
        public String getHello(String name) {
            return "hello,".concat(name);
        }
    }

    public static class InvocationHandlerImpl implements InvocationHandler {


        private InterfaceA target;

        public InvocationHandlerImpl(InterfaceA target) {
            this.target = target;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("invoke into proxy ...");
            return method.invoke(target, args);
        }
    }


}
