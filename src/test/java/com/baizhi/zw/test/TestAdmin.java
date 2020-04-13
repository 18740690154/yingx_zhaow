package com.baizhi.zw.test;


import com.baizhi.zw.dao.AdminDAO;
import com.baizhi.zw.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAdmin {
    @Autowired
    AdminDAO adminDAO;

    @Test
    public void contextLoads() {
        Admin admin = adminDAO.queryByUsername("admin");
        System.out.println(admin);
    }

}
