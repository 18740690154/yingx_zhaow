package com.baizhi.zw.controller;

import com.baizhi.zw.entity.Admin;
import com.baizhi.zw.service.AdminService;
import com.baizhi.zw.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping("getCodeImg")
    public void getCodeImg(HttpSession httpSession, HttpServletResponse httpServletResponse){
        //根据验证码工具类获取随机字符
        String code = ImageCodeUtil.getSecurityCode();
        System.out.println(code);
        //存储随机字符
        httpSession.setAttribute("code", code);
        //生成验证码图片
        BufferedImage image = ImageCodeUtil.createImage(code);
        //将验证码图片响应到页面
        try {
            ImageIO.write(image,"png",httpServletResponse.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("login")
    public HashMap<String, String> login(Admin admin,String codeImg){
        System.out.println("admin = " + admin);
        System.out.println("codeImg = " + codeImg);
        HashMap<String, String> map = adminService.login(admin, codeImg);
        return map;
    }

    @RequestMapping("logout")
    public String logout(HttpSession httpSession){
        httpSession.removeAttribute("login");
        return "redirect:/login/login.jsp";
    }
}
