package com.hwq.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 切面类
 * @Aspect 告诉spring当前类是一个切面类
 */
@Component
//表示 当前代理的目标对象如果是 com.hwq.aop.MathCalculator，并且目标对象是原型对象则该代理对象也每次生成一个新对象
@Aspect("perthis(target(com.hwq.aop.MathCalculator))")
@Scope("prototype")
public class LogAspects {

    //这是spring新版本里加的特性，表示 com.hwq.aop下所有的类都实现Calculator这个接口，默认方法的实现就按照MathCalculator这类
    @DeclareParents(value="com.hwq.aop.*+", defaultImpl=MathCalculator.class)
    public static Calculator mixin;

    //抽取公共的切入点表达式
    @Pointcut("execution(public * com.hwq.aop.*.*(..))")
    public void pointCut(){}


    @Pointcut("args(java.lang.String)")
    public void pointCutArgs(){

    }

    //表示同时满足这两个切点 则加上该通知
    @Before("pointCut() && pointCutArgs()")
    public void pointCutAdd(){
        System.out.println("pointCutAdd.....");
    }

    //@Before在目标方法前切入,切入点表达式指定在哪个方法切入 @Before("execution(public int com.hwq.aop.MathCalculator.div(int ,int))")
    //如果想在MathCalculator的所有方法前切入则可以用 * 代替方法名，如果不区分参数则可以用 .. 来代替 @Before("execution(public int  com.hwq.aop.MathCalculator.*(..))")
    //可以在通知方法的参数里加 JoinPoint 获取调用的方法和参数,JoinPoint要出现的话必须是在第一个参数否则会报错
    @Before("execution(public * com.hwq.aop.*.*(..))")
    public void logStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.println("proxy object="+this.hashCode());
        System.out.println(joinPoint.getSignature().getName()+"运行before。。。参数列表,{"+ Arrays.asList(args) +"}");
    }


    //@After("public int com.hwq.aop.MathCalculator.*(..)") //如果每个地方都写一下这个表达式觉得有点难写，则可以抽到一个公共的地方，如 pointCut()
    @After("pointCut()") //使用自己类中定义的切入点 则可以这样，如果是应用其他类中定义的切入点则类名要写全 @After("public int com.xxx.xxx.pointCut()")
    public void logEnd(){
        System.out.println("除法结束after。。。");
    }

    @AfterReturning(pointcut="pointCut()",returning = "result") //returning = "result" 表示用通知方法的参数中的result来获取目标方法的运行结果
    public void logReturn(Object result){
        System.out.println("除法正常返回。。。运行结果,{"+result+"}");
    }

    @AfterThrowing(value = "pointCut()", throwing="exception")
    public void logException(Exception exception){
        System.out.println("除法异常。。。异常信息="+ exception.toString());
    }

    /**
     *
     * @param joinPoint
     * @return  这个环绕通知的返回类型如果是 void ，那么会导致 @AfterReturning 获取不到结果，如果这个方法不
     * 抛异常，被自己try catch了的话，@AfterThrowing也将获取不到异常信息
     * @throws Throwable
     */
    @Around("pointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("====around  start=======");
        Object proceed = joinPoint.proceed();
        System.out.println("====around  end=======");
        return proceed;
    }
}
