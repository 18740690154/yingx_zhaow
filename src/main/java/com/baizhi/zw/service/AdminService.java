package com.baizhi.zw.service;

import com.baizhi.zw.entity.Admin;

import java.util.HashMap;

public interface AdminService {
    HashMap<String, String> login(Admin admin, String codeImg);

}
