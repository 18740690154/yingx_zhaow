package com.baizhi.zw.aspect;

import com.baizhi.zw.annotation.AddLog;
import com.baizhi.zw.entity.Admin;
import com.baizhi.zw.entity.Log;
import com.baizhi.zw.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Configuration
public class LogAspect {
    @Autowired
    HttpSession httpSession;
    @Autowired
    LogService logService;

    //@Around("execution(* com.baizhi.zw.serviceimpl.*.*(..))")
    @Around("@annotation(com.baizhi.zw.annotation.AddLog)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint) {

        Log log = new Log();
        //谁 时间  操作(调用了那个方法)  结果(是否成功)

        //谁
        Admin adminLogin = (Admin) httpSession.getAttribute("adminLogin");
        log.setWho(adminLogin.getUsername());
        //时间
        log.setTime(new Date());
        //操作(调用了那个方法)
        //获取方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        //获取方法名
        String methodName = signature.getName();
        //获取方法上的注解
        AddLog annotation = method.getAnnotation(AddLog.class);
        //获取注解中属性的值
        String value = annotation.value();
        log.setWhat(value + "(" + methodName + ")");
        //方法放行
        String result = "success";
        Object proceed = null;
        try {
            proceed = proceedingJoinPoint.proceed();
            log.setResult(result);
            //添加日志
            logService.add(log);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            result = "error";
            log.setResult(result);
            //添加日志
            logService.add(log);
        }
        return proceed;
    }

}
