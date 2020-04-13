package com.baizhi.zw.service;

import com.baizhi.zw.entity.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CategoryService {
    //查询一级类别
    HashMap<String, Object> queryByLevelsAndPage(Integer page, Integer rows);
    //添加类别
    void add(Category category);
    //修改类别
    void update(Category category);
    //删除类别
    Map<String,Object> delete(Category category);
    //根据一级类别id查询二级类别
    Map<String,Object> queryByParentIdAndPage(Integer page,Integer rows,String parentId);

    List<Category> queryAllCategory();
}
