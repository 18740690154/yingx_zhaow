package com.baizhi.zw.dao;

import com.baizhi.zw.entity.Admin;

public interface AdminDAO {
    Admin queryByUsername(String username);
}
