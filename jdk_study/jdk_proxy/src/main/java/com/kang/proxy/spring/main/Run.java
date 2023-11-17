package com.kang.proxy.spring.main;

import com.kang.proxy.spring.advisor.KangPointcutAdvisor;
import com.kang.proxy.spring.introduction.DefaultCustomInterface;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

public class Run {

	public static void main(String[] args) {

		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(new DefaultCustomInterface());
		/*proxyFactory.addInterface(CustomInterface);
		proxyFactory.setProxyTargetClass(true);
		proxyFactory.setOptimize(true);*/
		proxyFactory.addAdvisor(new KangPointcutAdvisor());
		proxyFactory.addAdvice(new MethodInterceptor() {
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				System.out.println("切面逻辑 before...");
				//Object result = invocation.proceed();
				Object result = invocation.getMethod().invoke(invocation.getThis(), invocation.getArguments());
				System.out.println("切面逻辑 after...");
				return result;
			}
		});
		DefaultCustomInterface userService2  = (DefaultCustomInterface) proxyFactory.getProxy();
		userService2.custom();
	}
}







