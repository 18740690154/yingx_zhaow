package com.baizhi.zw.test;

import com.baizhi.zw.dao.CategoryDAO;
import com.baizhi.zw.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCate {
    @Autowired
    CategoryDAO categoryDAO;

    @Test
    public void queryAllCate(){
        List<Category> categories = categoryDAO.queryAllCategory();
        for (Category category : categories) {
            System.out.println("category = " + category);
        }
    }
}
