package com.baizhi.zw.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;




@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGitHub {

    @Test
    public void testGitHub(){
        int a = 10;
        int b = 20;
        System.out.println("a+b="+a+b);
    }



}
