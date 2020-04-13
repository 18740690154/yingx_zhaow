package com.baizhi.zw.service;

import com.baizhi.zw.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String,Object> queryByPage(Integer page,Integer rows);

    String update(User user);

    String add(User user);

    void uploadFile(MultipartFile headImg, String id, HttpSession httpSession);

    void delete(User user);

    void uploadFileAliyun(MultipartFile headImg, String id, HttpSession httpSession);

    void exportUser();

    Map<String,Object> queryUserNum();

    List<Object> queryUserDistributionNum();


}
