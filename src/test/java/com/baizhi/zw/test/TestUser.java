package com.baizhi.zw.test;

import com.baizhi.zw.dao.UserDAO;
import com.baizhi.zw.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUser {

    @Autowired

    private UserDAO userMapper;
    // 通用mapper

    @Test
    public void queryAll(){
        List<User> users = userMapper.selectAll();
        users.forEach(user -> System.out.println(user));
    }

    /*  mybatis-generator
    @Test
    public void  queryAll(){
        //条件对象
        UserExample userExample = new UserExample();
        //userExample.createCriteria().andIdEqualTo("1111");

        List<Emp> users = userMapper.selectByExample(userExample);

        users.forEach(user -> System.out.println(user));
    }

    @Test
    public void add(){

        Emp user = new Emp("4444","44444","44444","44444","44444","44444","44444",new Date());

        userMapper.insert(user);
    }

    @Test
    public void update(){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo("4444");

        Emp user = new Emp();
        user.setId("4");
        user.setUsername("zw");
        user.setStatus("0");

       // userMapper.updateByExample(user,userExample);  //没设置的字段会被修改为null
        userMapper.updateByExampleSelective(user,userExample);
    }

    @Test
    public void del(){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo("4444");
        userMapper.deleteByExample(userExample);
    }


    @Test
    public void count(){
        UserExample userExample = new UserExample();
        long l = userMapper.countByExample(userExample);
        System.out.println("l = " + l);
    }*/
}
