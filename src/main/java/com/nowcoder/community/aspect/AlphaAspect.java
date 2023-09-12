package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

//@Component
//@Aspect
public class AlphaAspect {
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointcut(){
    }//注释为切点
    @Before("pointCut()")
    public void before(){
        System.out.println("before");
    }
    @After("pointCut()")
    public void after(){
        System.out.println("after");
    }
    @AfterReturning("pointCut()")
    public void afterReturning(){
        System.out.println("afterReturning");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("afterThrowing");
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("aroundbefore");//在调用前可以输出
        Object obj = joinPoint.proceed();//调用目标组件
        System.out.println("aroundafter");//在调用后可以输出
        return obj;
    }

}
