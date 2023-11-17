package com.kang.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * User:
 * Description:本demo使用jdk8
 * Date: 2023-06-15
 * Time: 22:53
 */
public class ProxyRun {

    public static void main(String[] args) {
        //添加vm参数： -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true
        //或者：
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        //生成的在项目根路径的com/sun/proxy下面(这里手写了$Proxy0.java)
        // jdk代理，被代理对接必须有接口，且代理创建出来的对象就是接口类型
        JdkService jdkService = new JdkServiceImpl();
        Object obj =  Proxy.newProxyInstance(
                JdkServiceImpl.class.getClassLoader(),
                new Class[]{JdkService.class}, // 必须是接口
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("before");
                        method.invoke(jdkService,args); // 这里要jdkService而不是proxy
                        System.out.println("after");
                        return null;
                    }
                });
        JdkService jdkServiceProxy = (JdkService) obj; // 代理创建出来的是接口类型,并且继承Proxy
        jdkServiceProxy.run();
    }

}
