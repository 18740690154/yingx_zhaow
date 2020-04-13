package com.baizhi.zw.service;

import com.baizhi.zw.entity.Log;

import java.util.Map;

public interface LogService {

    Map<String,Object> queryByPage(Integer page,Integer rows);

    void add(Log log);
}
