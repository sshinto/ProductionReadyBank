package com.hb.bank.configuration.advice;

import com.hb.bank.dto.AccountDto;
import com.hb.bank.exception.BankException;
import com.hb.bank.service.account.AccountServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Configuration
public class LoggerAspect {
  private static final Logger logger = LoggerFactory.getLogger(AspectConfig.class);

  //https://docs.spring.io/spring-framework/docs/2.5.x/reference/aop.html
  //https://docs.spring.io/spring-framework/docs/2.5.x/reference/aop.html#aop-understanding-aop-proxies
  @Before("execution(public com.hb.bank.dto.CustomerDto  com.hb.bank.service.customer.CustomerServiceImpl.getDetails(..))") // this expression is called pointcut
  public void adviceForGetDetails(){
    logger.info("\n ==== BEFORE ADVICE ==== \n POINT CUT EXPRESSION IN THE @BEFORE \n");
  }
 /**
   * A join point is in the bank layer if the method is defined
   * in a type in the com.hb.bank package or any sub-package
   * under that.
   */
  @Pointcut("within(com.hb.bank..*)")
  public void inBankLayer() {}

//  @Before("inBankLayer()") // this expression is called pointcut
//  public void adviceinBankLayer(JoinPoint joinPoint){
//    logger.info("\n ==== BEFORE ADVICE ALL CLASSES ==== \n - Method Name: {} -  \n", joinPoint.getSignature().getDeclaringType().getSimpleName(),  joinPoint.getSignature().getName());
//  }
  /**
   * A join point is in the service layer if the method is defined
   * in a type in the com.xyz.someapp.service package or any sub-package
   * under that.
   */
  @Pointcut("within(com.hb.bank.service..*)")
  public void inServiceLayer() {}
//  @Before("inServiceLayer()") // this expression is called pointcut
//  public void adviceinServiceLayer(JoinPoint joinPoint){
//    logger.info("\n ==== BEFORE ADVICE SERVICE LAYER ONLY ==== \n  : Class Name: {} - Method Name: {} -  \n", joinPoint.getSignature().getDeclaringType().getSimpleName(),  joinPoint.getSignature().getName());
//  }

  /**
   * A business service is the execution of any method defined on a service
   * interface. This definition assumes that interfaces are placed in the
   * "service" package, and that implementation types are in sub-packages.
   *
   * If you group service interfaces by functional area (for example,
   * in packages com.xyz.someapp.abc.service and com.xyz.def.service) then
   * the pointcut expression "execution(* com.xyz.someapp..service.*.*(..))"
   * could be used instead.
   *
   * Alternatively, you can write the expression using the 'bean'
   * PCD, like so "bean(*Service)". (This assumes that you have
   * named your Spring service beans in a consistent fashion.)
   */
  @Pointcut("execution(* com.hb.bank.*.*(..))")
  public void businessService() {}

//  @Before("businessService()") // this expression is called pointcut
//  public void advicebusinessService(JoinPoint joinPoint){
//    logger.info("\n ==== BEFORE ADVICE BUSINESS LAYER ONLY ==== \n  : Class Name: {} - Method Name: {} -  \n", joinPoint.getSignature().getDeclaringType().getSimpleName(),  joinPoint.getSignature().getName());
//  }

//  // This advice is executed after the allMethod() pointcut
//  @After("inBankLayer()") // this expression is called pointcut
//  public void loggingAfter(JoinPoint joinPoint){
//    logger.info("\n ==== AFTER ADVICE ALL LAYER ==== \n : Class Name: {} - Method Name: {} \n", joinPoint.getSignature().getDeclaringType().getSimpleName(),
//        joinPoint.getSignature().getName());
//  }

  @AfterReturning(pointcut="execution(public com.hb.bank.dto.AccountDto  com.hb.bank.service.account.AccountServiceImpl.getDetails(..))", returning = "returnObj") // this expression is called pointcut
  public void loggingAfterReturning(JoinPoint joinPoint, Object returnObj){
    AccountDto returnObj1 = (AccountDto) returnObj;
    Object[] args = joinPoint.getArgs();
    Object arg=args[0];
    logger.info("\n ==== AFTER RETURNING - ALL LAYER ==== \n : Class Name: {} - Method Name: {} - input - {}, output- {} \n",
        joinPoint.getSignature().getDeclaringType().getSimpleName(),
        joinPoint.getSignature().getName(),
        arg.toString(),
        returnObj1.toString());
  }

  @AfterThrowing(pointcut="execution(public com.hb.bank.dto.CustomerDto  com.hb.bank.service.customer.CustomerServiceImpl.getDetails(..))", throwing = "ex") // this expression is called pointcut
  public void loggingAfterThrowing(JoinPoint joinPoint, BankException ex){
    logger.info("\n ====AFTER THROWING ====\n - EXCEPTION - {}", ex.getBankErrors());
  }

  @Around("inBankLayer()")
  public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
    Object returnval = null;
    //write code before proceed() and after
    try {
      logger.info("\n ==== BEFORE - AROUND ADVICE CALL ======\n");
      returnval = proceedingJoinPoint.proceed(); // this is must, it executes that target method.
      logger.info("\n ====AFTER - AROUND ADVICE CALL ======\n RETURN VALUE - {}\n", returnval.toString());
    } catch (Throwable e) {
      logger.info("\n ====EXCEPTION -AROUND ADVICE CALL ======\n EXCEPTION - {}\n", e); // if target method throws exception then that is logged here
    }
    return returnval;
  }
}
