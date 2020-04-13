package com.baizhi.zw.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Set;

@Configuration
@Aspect
public class RedisCacheHashAspect {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //添加缓存
    @Around("@annotation(com.baizhi.zw.annotation.AddCaChe)")
    //@Around("execution(* com.baizhi.zw.serviceimpl.*Impl.query*(..))")
    public  Object addCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("进入环绕通知");

        //序列化解决乱码
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);

        StringBuilder sb = new StringBuilder();

        //key:类的全限定名
        //value:hash(key:方法名+参数(实参),value:缓存数据)

        //获取类的全限定名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        //字符串拼接
        sb.append(methodName);
        //获取方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }
        //获取hash中的key
        String key = sb.toString();

        ValueOperations valueOperations = redisTemplate.opsForValue();

        Object result = null;
        HashOperations hashOperations = redisTemplate.opsForHash();
        //判断redis中key是否存在
        if (hashOperations.hasKey(className,key)){
            //存在 缓存数据存在，根据key取数据并返回结果
            result = hashOperations.get(className,key);
        }else {
            //不存在 缓存数据存在，查询数据库，放行方法得到结果
            result = proceedingJoinPoint.proceed();
            //拿到数据，添加缓存
            hashOperations.put(className,key,result);
        }

        return result;
    }


    //清除缓存
    @After("@annotation(com.baizhi.zw.annotation.DelCaChe)")
    //@After("execution(* com.baizhi.zw.serviceimpl.*.*(..)) && !execution(* com.baizhi.zw.serviceimpl.*.query*(..))")
    public void delCaChe(JoinPoint joinPoint){
        //清空同一全限定类名的数据

        //获取类的全限定类名
        String className = joinPoint.getTarget().getClass().getName();

        //删除该类下所有的数据
        redisTemplate.delete(className);
    }
}
