package com.kang.proxy.spring.advice;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * @author 周瑜
 */
public class KangThrowsAdvice implements ThrowsAdvice {

	public void afterThrowing(Method method, Object[] args, Object target, NullPointerException ex) {
		System.out.println("方法抛出异常后执行");
	}

}
