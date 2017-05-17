package gr.personal.user.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Created by Nick Kanakis on 17/5/2017.
 */
@Aspect
@Component
public class LogTimeExecutionAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogTimeExecutionAspect.class);

    @Around("@annotation(gr.personal.user.aop.LogTimeExecution)")
    public Object logTimeExecutionAspect(ProceedingJoinPoint joinPoint) throws Throwable{
        StopWatch stopWatch = new StopWatch(LogTimeExecutionAspect.class.getName());
        stopWatch.start(joinPoint.toShortString());

        Object proceed = joinPoint.proceed();

        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());
        return proceed;
    }

}
