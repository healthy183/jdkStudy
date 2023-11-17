package com.kang.proxy.spring.advisor;

import com.kang.proxy.spring.advice.KangBeforeAdvice;
import com.kang.proxy.spring.introduction.DefaultCustomInterface;
import org.aopalliance.aop.Advice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

import java.lang.reflect.Method;

/**
 * User:
 * Description:
 * Date: 2023-03-15
 * Time: 23:03
 */
public class KangPointcutAdvisor implements PointcutAdvisor {

	@Override
	public Advice getAdvice() {
		return new KangBeforeAdvice();
	}

	@Override
	public boolean isPerInstance() {
		return false;
	}

	/*@Override
	public Pointcut getPointcut() {
		return new StaticMethodMatcherPointcut() {
			@Override
			public boolean matches(Method method, Class<?> targetClass) {
				//return targetClass.equals(DefaultCustomInterface.class);
				return method.getName().equals("custom");
			}
		};
	}*/
	@Override
	public Pointcut getPointcut() {
		return new Pointcut() {
			@Override
			public ClassFilter getClassFilter() {
				return new ClassFilter() {
					@Override
					public boolean matches(Class<?> clazz) {
						return clazz.equals(DefaultCustomInterface.class);
					}
				};
			}

			@Override
			public MethodMatcher getMethodMatcher() {
				return new MethodMatcher() {
					@Override
					public boolean matches(Method method, Class<?> targetClass) {
						return method.getName().equals("custom");
					}

					@Override
					public boolean isRuntime() {
						return true;
					}

					@Override
					public boolean matches(Method method, Class<?> targetClass, Object... args) {
						return true;
					}
				};
			}
		};
	}
}
