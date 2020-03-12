package com.eteshis.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * This class logs all methods with slf4j. We write logs only here.
 *
 */
@Aspect
@Component
public class CustomAspectService {

	private static Logger logger = LoggerFactory.getLogger(CustomAspectService.class);

	@AfterThrowing(
			pointcut = "execution(* *(..)) &&(within(com.eteshis.service.*) || within(com.eteshis.repository.*) || within(com.eteshis.config.*))",
			throwing = "e")
	public void myAfterThrowing(JoinPoint joinPoint, Throwable e) {
		Signature signature = joinPoint.getSignature();
		String methodName = signature.getName();
		String stuff = signature.toString();
		String arguments = Arrays.toString(joinPoint.getArgs());
		logger.error("We have caught exception in method: " + methodName + " with arguments " + arguments + "\nand the full toString: " + stuff
				+ "\nthe exception is: " + e.getMessage(), e);

	}

	/**
	 * calculate method execution time.@Pointcut( execution()) is aspect method and its takes packages, classes and methods as prameter
	 */
	@Pointcut("execution(* *(..)) &&(within(com.eteshis.controller.*) ||  within(com.eteshis.service.*) || within(com.reposityory.*))")
	public void businessMethods() {
	}

	@Around("businessMethods()")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		Signature signature = pjp.getSignature();
		String methodName = signature.getName();
		String stuff = signature.toString();
		String arguments = Arrays.toString(pjp.getArgs());
		Object output = pjp.proceed();
		long elapsedTime = System.currentTimeMillis() - start;

		if (elapsedTime > 10000)

			logger.warn("Going to call the method.: " + methodName + " with arguments " + arguments + "\nand the full toString: " + stuff
					+ "\nMethod execution time: " + elapsedTime);

		return output;
	}

}
