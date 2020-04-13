package com.baizhi.zw.dao;

import com.baizhi.zw.entity.Category;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryDAO extends Mapper<Category> {

    List<Category> queryAllCategory();
}