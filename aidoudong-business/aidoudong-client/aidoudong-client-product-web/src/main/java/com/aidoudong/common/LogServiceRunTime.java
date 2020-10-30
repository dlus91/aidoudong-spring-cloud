package com.aidoudong.common;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogServiceRunTime {

	private final static Logger logger = LoggerFactory.getLogger(LogServiceRunTime.class);

	@Pointcut("execution(* com.aidoudong.service..*.*(..))")
	public void performance(){}


	@Around("performance()")
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
		//记录起始时间
		StopWatch stopWatch = StopWatch.createStarted();
		Object result = "";
		/** 执行目标方法 */
		try{
			result= joinPoint.proceed();
		}catch(Exception e){
			logger.error("日志记录发生错误, errorMessage: {}", e.getMessage());
		}finally{
			System.out.println("invoke use service time: "+stopWatch.getTime()+"ms");
			stopWatch.stop();
		}
		return result;
	}


}
