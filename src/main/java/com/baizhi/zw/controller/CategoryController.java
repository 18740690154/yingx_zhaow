package com.baizhi.zw.controller;

import com.baizhi.zw.entity.Category;
import com.baizhi.zw.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    //查询一级类别
    @ResponseBody
    @RequestMapping("queryByLevelsAndPage")
    public Map<String, Object> queryByLevelsAndPage(Integer page, Integer rows) {
        Map<String, Object> map = categoryService.queryByLevelsAndPage(page, rows);
        return map;
    }

    //操作一级类别
    @ResponseBody
    @RequestMapping("editOne")
    public Object edit(Category category,String oper){
        System.out.println("category = " + category);
        Map<String, Object> map =null;
        if (oper.equals("add")){
            categoryService.add(category);
        }
        if (oper.equals("edit")){
            categoryService.update(category);
        }
        if (oper.equals("del")){
            map = categoryService.delete(category);
            return map;
        }
        return null;
    }

    //根据一级类别id查询二级类别
    @ResponseBody
    @RequestMapping("queryByParentIdAndPage")
    public Map<String,Object> queryByParentIdAndPage(Integer page,Integer rows,String parentId){
        System.out.println("parentId = " + parentId);
        Map<String, Object> map = categoryService.queryByParentIdAndPage(page, rows, parentId);
        return map;
    }

    //操作二级类别
    @ResponseBody
    @RequestMapping("editTwo")
    public void editChild(Category category,String oper){
        if (oper.equals("add")){
            categoryService.add(category);
        }
        if (oper.equals("edit")){
            categoryService.update(category);
        }
        if (oper.equals("del")){
            categoryService.delete(category);
        }
    }
}
