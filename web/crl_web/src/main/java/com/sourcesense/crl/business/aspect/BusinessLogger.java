package com.sourcesense.crl.business.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class BusinessLogger {


	private Logger log = LoggerFactory.getLogger(BusinessLogger.class);
	
	

	@After ("within(com.sourcesense.crl.business.service.rest.*)")
	public void restServices(JoinPoint joinPoint) {
	String trace = "Executed Rest Service : "+joinPoint.getSignature().getName()+"-"+joinPoint.getSignature().getDeclaringTypeName();
		
		
		Object[] inputList = joinPoint.getArgs();

		for (Object object : inputList) {

			if(object.getClass().isAnnotationPresent(JsonRootName.class)){
				
				trace += " beanIn ["+object.toString()+"] ";
				
			}else {
				
				trace += " otherIn [ "+object.getClass().getCanonicalName()+" : " +object.toString()+ "]";
			}
		}
		
		
		log.info(trace);

	}

		
	@Pointcut("within(@org.springframework.stereotype.Service *)")
	public void beanAnnotatedWithService() {
	}
	
	
	@Pointcut("execution(public * *(..))")
	public void publicMethod() {

	}

	@Pointcut("publicMethod() && beanAnnotatedWithService()")
	public void publicMethodInsideAClassMarkedWithService() {
	}

	@AfterReturning(pointcut = "publicMethodInsideAClassMarkedWithService()")
	public void prova(JoinPoint joinPoint) {

		String trace = "Executed Manager : "+joinPoint.getSignature().getName()+"-"+joinPoint.getSignature().getDeclaringTypeName();
		
		
		Object[] inputList = joinPoint.getArgs();

		for (Object object : inputList) {

			if(object.getClass().isAnnotationPresent(JsonRootName.class)){
				
				//System.out.println("Object :" + object.toString());
				trace += " beanIn ["+object.toString()+"] ";
				
			}else {
				
				trace += " otherIn [ "+object.getClass().getCanonicalName()+" : " +object.toString()+ "]";
			}
		}
		
		
		log.info(trace);

	}

}
