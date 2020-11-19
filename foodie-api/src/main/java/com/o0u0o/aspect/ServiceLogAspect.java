package com.o0u0o.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 服务日志的切面
 * 1.需要在pom文件引入aop的依赖方可使用@Aspect注解
 * @author mac
 * @date 2020/11/19 1:43 下午
 */
@Aspect
@Component
public class ServiceLogAspect {

    public static final Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * APO的5种通知
     * 1.前置通知：在方法调用之前执行
     * 2.后置通知：在方法正常调用之后执行
     * 3.环绕通知：在方法调用之前和调用之后，都可以执行的通知
     * 4.异常通知：在方法调用过程中发生异常，则通知
     * 5.最终通知：在方法调用之后执行
     */

    /**
     * 记录方法执行的时间
     * 切面表达式：
     *  execution 代表所要执行的表达式主体
     *  第一处 * 代表方法返回类型 * 代表所有返回类型
     *  第二处 包名代表aop监控的类所在的包
     *  第三处  .. 代表该包以及其子包下的所有类方法
     *  第四处 * 代表类名， * 代表所有类
     *  第五处 *(..) * 代表类中的方法名是什么 (..) 表示方法中的任何参数
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.o0u0o.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("==============================================");
        log.info("=================== 开始执行 ===================");
        log.info("============ 执行方法: {}.{} ",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());
        //记录开始时间
        long begin = System.currentTimeMillis();

        //执行目标service
        Object result = joinPoint.proceed();

        //记录结束时间

        long end = System.currentTimeMillis();

        long takeTime = end - begin;

        if (takeTime > 3000){
            log.error("============== 耗时:{} 毫秒 ", takeTime);
            log.error("=================== 执行结束 ===================");
            log.error("==============================================");

        } else if (takeTime > 2000){
            log.warn("============== 耗时:{} 毫秒 =============", takeTime);
            log.warn("=================== 执行结束 ===================");
            log.warn("==============================================");

        }else {
            log.info("============== 耗时:{} 毫秒 =============", takeTime);
            log.info("=================== 执行结束 ===================");
            log.info("==============================================");

        }
        return result;
    }
}
