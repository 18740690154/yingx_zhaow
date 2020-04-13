package com.baizhi.zw.dao;

import com.baizhi.zw.entity.User;
import com.baizhi.zw.vo.CityVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDAO extends Mapper<User> {
    //根据性别查询每个地区用户的分布
    List<CityVo> queryUserAreaBySex(String sex);

    //根据性别查询每个月份用户注册的人数
    String queryUserMonthBySex(@Param("sex")String sex,@Param("month")String month);

    //查询用户注册的所有月份
    List<String> queryUserMonth();

}