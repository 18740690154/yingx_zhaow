package com.baizhi.zw.service;

import java.util.Map;

public interface CommentService {
   Map<String,Object> queryByPage(Integer page,Integer rows);
}
