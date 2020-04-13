package com.baizhi.zw.controller;

import com.baizhi.zw.entity.User;
import com.baizhi.zw.service.UserService;
import com.baizhi.zw.util.AliyunSendSmsOldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("queryByPage")
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Map<String, Object> map = userService.queryByPage(page, rows);
        return map;
    }

    @ResponseBody
    @RequestMapping("edit")
    public String edit(User user, String oper) {
        System.out.println("user = " + user);

        String uuid = null;
        if (oper.equals("add")) {
            uuid = userService.add(user);
        }
        if (oper.equals("edit")) {
            uuid = userService.update(user);
        }
        if (oper.equals("del")) {
            userService.delete(user);
        }

        return uuid;
    }

    @RequestMapping("uploadFile")
    @ResponseBody
    public void uploadFile(MultipartFile headImg, String id, HttpSession httpSession) {
        //userService.uploadFile(headImg,id,httpSession);
        userService.uploadFileAliyun(headImg, id, httpSession);
    }

    @RequestMapping("sendPhoneCode")
    @ResponseBody
    public String sendPhoneCode(String phone) {
        String code = AliyunSendSmsOldUtils.getRandom(6);

        System.out.println("验证码:" + code);
        String message = AliyunSendSmsOldUtils.sendCode(phone, code);

        System.out.println(message);

        return message;
    }

    //导出用户信息
    @RequestMapping("export")
    @ResponseBody
    public void exportUser(){
        userService.exportUser();
    }

    @ResponseBody
    @RequestMapping("queryUserNum")
    public Map<String,Object> queryUserNum(){
        Map<String, Object> map = userService.queryUserNum();
        System.out.println("map = " + map);
        return map;
    }

    @ResponseBody
    @RequestMapping("queryUserDistributionNum")
    public List<Object> queryUserDistributionNum(){
        List<Object> list = userService.queryUserDistributionNum();
        System.out.println("list = " + list);
        return list;
    }
}
