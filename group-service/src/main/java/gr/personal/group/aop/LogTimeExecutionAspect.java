package gr.personal.group.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Created by Nick Kanakis on 17/5/2017.
 */

/*
* This module will capture the cross-cutting concern of method execution time logging.
* This is a Spring Bean so we annotated as @Component
* */
@Aspect
@Component
public class LogTimeExecutionAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogTimeExecutionAspect.class);
    /**
     * We have annotated our method with @Around. This is our advice,
     * and around advice means we are adding extra code both before and after method execution.
     *
     * Our @Around annotation has a point cut argument. Our pointcut just says,
     * ‘Apply this advice any method which is annotated with @LogExecutionTime.’
     */
    @Around("@annotation(gr.personal.group.aop.annotations.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        StopWatch stopWatch = new StopWatch(LogTimeExecutionAspect.class.getName());
        stopWatch.start(((MethodSignature) joinPoint.getSignature()).getMethod().getName());
        //Just call the annotated method (Join point)
        Object proceed = joinPoint.proceed();
        stopWatch.stop();
        logger.trace(stopWatch.prettyPrint());
        return proceed;
    }
}
