package com.hwq.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * 切面类
 * @Aspect 告诉spring当前类是一个切面类
 */
@Aspect
public class LogAspects {

    //抽取公共的切入点表达式
    @Pointcut("execution(public int com.hwq.aop.MathCalculator.*(..))")
    public void pointCut(){}

    //@Before在目标方法前切入,切入点表达式指定在哪个方法切入 @Before("execution(public int com.hwq.aop.MathCalculator.div(int ,int))")
    //如果想在MathCalculator的所有方法前切入则可以用 * 代替方法名，如果不区分参数则可以用 .. 来代替 @Before("execution(public int  com.hwq.aop.MathCalculator.*(..))")
    //可以在通知方法的参数里加 JoinPoint 获取调用的方法和参数,JoinPoint要出现的话必须是在第一个参数否则会报错
    @Before("execution(public int com.hwq.aop.MathCalculator.*(..))")
    public void logStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.println(joinPoint.getSignature().getName()+"运行。。。参数列表,{"+ Arrays.asList(args) +"}");
    }


    //@After("public int com.hwq.aop.MathCalculator.*(..)") //如果每个地方都写一下这个表达式觉得有点难写，则可以抽到一个公共的地方，如 pointCut()
    @After("pointCut()") //使用自己类中定义的切入点 则可以这样，如果是应用其他类中定义的切入点则类名要写全 @After("public int com.xxx.xxx.pointCut()")
    public void logEnd(){
        System.out.println("除法结束。。。");
    }

    @AfterReturning(value="pointCut()",returning = "result") //returning = "result" 表示用通知方法的参数中的result来获取目标方法的运行结果
    public void logReturn(Object result){
        System.out.println("除法正常返回。。。运行结果,{"+result+"}");
    }

    @AfterThrowing(value = "pointCut()", throwing="exception")
    public void logException(Exception exception){
        System.out.println("除法异常。。。异常信息="+ exception.toString());
    }
}
