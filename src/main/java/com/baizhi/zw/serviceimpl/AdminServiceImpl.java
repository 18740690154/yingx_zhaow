package com.baizhi.zw.serviceimpl;

import com.baizhi.zw.dao.AdminDAO;
import com.baizhi.zw.entity.Admin;
import com.baizhi.zw.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    HttpSession httpSession;
    @Autowired
    AdminDAO adminDAO;



    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String, String> login(Admin admin, String codeImg) {
        HashMap<String, String> map = new HashMap<>();

        //获取存储的验证码
        String code = (String) httpSession.getAttribute("code");
        //判断用户输入的验证码和存储的验证码是否一致
        if (code.equals(codeImg)){
            //验证码一致,判断用户是否存在
            Admin adminLogin = adminDAO.queryByUsername(admin.getUsername());
            if (adminLogin!=null){
                //用户存在,判断密码是否正确
                if (adminLogin.getPassword().equals(admin.getPassword())){
                    //密码正确,将对象存入Session作用域中
                    httpSession.setAttribute("adminLogin",adminLogin);
                    map.put("status","200");
                    map.put("message","密码正确,登录成功");
                }else {
                    //密码错误,返回错误信息
                    map.put("status","400");
                    map.put("message","密码错误");
                }
            }else {
                //用户不存在,返回错误信息
                map.put("status","400");
                map.put("message","用户不存在");
            }
        }else {
            //验证码不一致,返回错误信息
            map.put("status","400");
            map.put("message","验证码错误");
        }
        return map;
    }
}
