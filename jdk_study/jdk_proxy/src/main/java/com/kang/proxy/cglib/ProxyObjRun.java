package com.kang.proxy.cglib;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * User:  cglib的动态代理
 * Description:
 * jdk8以上请增加vm参数: --add-opens java.base/java.lang=ALL-UNNAMED
 *想看动态代理编译出来的class请加vm参数：-Dcglib.debugLocation=D:\idea4restart\jdk_study\jdk_proxy\target\classes
 * Date: 2023-06-15
 * Time: 23:17
 */
public class ProxyObjRun {

    public static void main(String[] args) {
        ProxyObj proxyObj = new ProxyObj();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ProxyObj.class);
        enhancer.setCallbacks(new Callback[]{new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("before");
                methodProxy.invoke(proxyObj,objects); // 这里要proxyObj而不是o，如果是o的话则死循环
                methodProxy.invokeSuper(o,objects); // 通过代理类调用被代理方法
                System.out.println("after");
                return null;
            }
        }});
        // 支持指定过滤器,但是new Callback[]需要增加参数： NoOp.INSTANCE
        /*enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                if (method.getName().equals("test") || method.getName().equals("b")) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });*/
        ProxyObj createProxyObj = (ProxyObj) enhancer.create();
        createProxyObj.test();
        //createProxyObj.fly();
    }
}
