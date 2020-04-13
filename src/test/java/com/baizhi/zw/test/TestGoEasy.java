package com.baizhi.zw.test;

import com.alibaba.fastjson.JSON;
import io.goeasy.GoEasy;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class TestGoEasy {

    @Test
    public void testSendMsg() {
        //配置发送消息所需要的参数 参数:regionHost:服务器地址 appkey:自己的appkey
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-76fcbf87a4484fb4bd7a2c54674aa8f9");
        //配置发送消息
        goEasy.publish("yingxChannel", "Hello, GoEasy!");
    }

    @Test
    public void testGoEasy() {
        for (int i = 0; i <= 10; i++) {
            //获取随机数
            Random random = new Random();

            HashMap<String, Object> map = new HashMap<>();

            map.put("month", Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月"));
            map.put("boys", Arrays.asList(random.nextInt(300), random.nextInt(300), random.nextInt(300), random.nextInt(300), random.nextInt(300), random.nextInt(300)));
            map.put("girls", Arrays.asList(random.nextInt(300), random.nextInt(300), random.nextInt(300), random.nextInt(300), random.nextInt(300), random.nextInt(300)));

            //将map对象转为json格式字符串
            String content = JSON.toJSONString(map);

            //配置发送消息所需要的参数 参数:regionHost:服务器地址 appkey:自己的appkey
            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-76fcbf87a4484fb4bd7a2c54674aa8f9");
            //配置发送消息
            goEasy.publish("yingxChannel", content);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
