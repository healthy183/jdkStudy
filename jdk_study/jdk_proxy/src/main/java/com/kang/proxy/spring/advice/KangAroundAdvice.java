package com.kang.proxy.spring.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author 周瑜
 */
public class KangAroundAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("方法执行Around前");
		Object proceed = invocation.proceed();
		System.out.println("方法执行Around后");
		return proceed;
	}
}
