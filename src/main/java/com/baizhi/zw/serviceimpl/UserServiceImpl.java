package com.baizhi.zw.serviceimpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSON;
import com.baizhi.zw.annotation.AddCaChe;
import com.baizhi.zw.annotation.AddLog;
import com.baizhi.zw.annotation.DelCaChe;
import com.baizhi.zw.dao.UserDAO;
import com.baizhi.zw.entity.User;
import com.baizhi.zw.entity.UserExample;
import com.baizhi.zw.service.UserService;
import com.baizhi.zw.util.AliyunOssUtil;
import com.baizhi.zw.util.UUIDUtil;
import com.baizhi.zw.vo.CityVo;
import com.baizhi.zw.vo.MondelVo;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserService userService;

    @AddCaChe
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();

        //总条数:records
        UserExample userExample = new UserExample();
        int records = userDAO.selectCountByExample(userExample);
        map.put("records", records);
        //总页数:total  总条数/每页展示的条数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        //当前页:page
        map.put("page", page);
        //数据:rows
        //参数:从第几条数据展示,每页展示几条数据
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> users = userDAO.selectByRowBounds(new User(), rowBounds);
        map.put("rows", users);

        return map;
    }

    @DelCaChe
    @AddLog(value = "修改用户")
    @Override
    public String update(User user) {
        userDAO.updateByPrimaryKeySelective(user);
        return user.getId();
    }

    @DelCaChe
    @AddLog(value = "添加用户")
    @Override
    public String add(User user) {
        String uuid = UUIDUtil.getUUID();
        user.setId(uuid);
        user.setStatus("1");
        user.setCreateDate(new Date());

        Map<String, Object> map = userService.queryUserNum();
        String stringMap = JSON.toJSONString(map);
        List<Object> list = userService.queryUserDistributionNum();
        String stringList = JSON.toJSONString(list);
        //配置发送消息所需要的参数 参数:regionHost:服务器地址 appkey:自己的appkey
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-76fcbf87a4484fb4bd7a2c54674aa8f9");
        //配置发送消息
        goEasy.publish("mapChannel", stringMap);
        goEasy.publish("listChannel", stringList);

        userDAO.insert(user);
        return uuid;
    }

    @Override
    public void uploadFile(MultipartFile headImg, String id, HttpSession httpSession) {
        //根据相对路径获取绝对路径
        String realPath = httpSession.getServletContext().getRealPath("/upload/img");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdir();
        }
        //获取文件名
        String filename = headImg.getOriginalFilename();

        String newName = new Date().getTime() + "-" + filename;

        try {
            //文件上传
            headImg.transferTo(new File(realPath, newName));

            //头像路径的修改

            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(id);

            User user = new User();
            user.setHeadImg(newName);
            userDAO.updateByExampleSelective(user, userExample);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @DelCaChe
    @AddLog(value = "删除用户")
    @Override
    public void delete(User user) {
        userDAO.deleteByPrimaryKey(user);
    }

    //上传到阿里云
    @Override
    public void uploadFileAliyun(MultipartFile headImg, String id, HttpSession httpSession) {

        //获取文件名
        String fileName = headImg.getOriginalFilename();
        String newName = new Date().getTime() + "-" + fileName;
        //上传文件至阿里云

        AliyunOssUtil.uploadFileBytes(headImg, "img/" + newName);
        //头像路径的修改
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(id);

        User user = new User();
        //网络路径:https://yingx-zw.oss-cn-beijing.aliyuncs.com/img/2.jpg
        user.setHeadImg("https://yingx-zw.oss-cn-beijing.aliyuncs.com/img/" + newName);
        userDAO.updateByExampleSelective(user, userExample);
    }

    @Override //导出
    public void exportUser() {
        UserExample userExample = new UserExample();
        List<User> users = userDAO.selectByExample(userExample);
        System.out.println("users = " + users);

        //导出  参数:标题，工作表名
        ExportParams exportParams = new ExportParams("应学视频App用户信息表", "用户信息1");
        //配置工作表的参数：导出参数的对象，要导出的对象，导出的数据集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, User.class, users);

        try {
            //导出
            workbook.write(new FileOutputStream(new File("D:\\Excel\\users.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AddCaChe
    @Override
    public Map<String, Object> queryUserNum() {
        HashMap<String, Object> map = new HashMap<>();

        //查询所有的月份
        List<String> strings = userDAO.queryUserMonth();
        List<String> strs = new ArrayList<>();
        //遍历strings
        for (String string : strings) {
            //字符串拼接
            String str = string + "月";
            strs.add(str);
        }
        map.put("month", strs);

        ArrayList<String> manUserNum = new ArrayList<>();
        ArrayList<String> womanUserNum = new ArrayList<>();
        //遍历strs
        for (String str : strs) {
            String manUserVo = userDAO.queryUserMonthBySex("男",str);
            String womanUserVo = userDAO.queryUserMonthBySex("女",str);
            manUserNum.add(manUserVo);
            womanUserNum.add(womanUserVo);
        }
        map.put("boys", manUserNum);
        map.put("girls", womanUserNum);
        return map;
    }

    @AddCaChe
    @Override
    public List<Object> queryUserDistributionNum() {
        ArrayList<Object> list = new ArrayList<>();

        ArrayList<CityVo> boyCityVos = new ArrayList<>();

        //查询男性地区分布情况
        List<CityVo> boysList = userDAO.queryUserAreaBySex("男");

        for (CityVo cityVo : boysList) {
            boyCityVos.add(cityVo);
        }
        ArrayList<CityVo> girlCityVos = new ArrayList<>();
        //查询女性地区分布情况
        List<CityVo> girlsList = userDAO.queryUserAreaBySex("女");

        for (CityVo cityVo : girlsList) {
            girlCityVos.add(cityVo);
        }

        MondelVo boys = new MondelVo("男", boyCityVos);
        MondelVo girls = new MondelVo("女", girlCityVos);

        list.add(boys);
        list.add(girls);

        return list;
    }

}
