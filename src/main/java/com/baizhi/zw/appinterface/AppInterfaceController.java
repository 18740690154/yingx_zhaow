package com.baizhi.zw.appinterface;

import com.baizhi.zw.common.CommonResult;
import com.baizhi.zw.entity.Category;
import com.baizhi.zw.service.CategoryService;
import com.baizhi.zw.service.VideoService;
import com.baizhi.zw.util.AliyunSendSmsOldUtils;
import com.baizhi.zw.vo.DetailVideoVo;
import com.baizhi.zw.vo.LikeVideoVo;
import com.baizhi.zw.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("app")
public class AppInterfaceController {

    @Autowired
    VideoService videoService;
    @Autowired
    CategoryService categoryService;
    //发送验证码的接口
    @RequestMapping(("getPhoneCode"))
    public CommonResult getPhoneCode(String phone) {
        //生成随机数
        String random = AliyunSendSmsOldUtils.getRandom(6);
        System.out.println("验证码:" + random);
        //发送验证码
        String message = AliyunSendSmsOldUtils.sendCode(phone, random);
        System.out.println("返回信息:" + message);

        if (message.equals("发送成功")) {
            return new CommonResult().success(phone);
        } else {
            return new CommonResult().error(message);
        }
    }

    //首页展示视频接口
    @RequestMapping("queryByReleaseTime")
    public CommonResult queryByReleaseTime() {
        try {
            //查询视频数据
            List<VideoVo> videoVos = videoService.queryByReleaseTime();
            return new CommonResult().success(videoVos);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().error();
        }
    }


    //首页搜索视频接口
    @RequestMapping("queryByLikeVideoName")
    public CommonResult queryByLikeVideoName(String content){
        try {
            List<LikeVideoVo> likeVideoVos = videoService.queryByLikeVideoName(content);
            System.out.println("likeVideoVos = " + likeVideoVos);
            return new CommonResult().success(likeVideoVos);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult().error();
        }
    }


    //浏览视频详细信息
    @RequestMapping("queryByVideoDetail")
    public CommonResult queryByVideoDetail(String videoId,String cateId,String userId){
        try {
            DetailVideoVo detailVideoVo = videoService.queryByVideoDetail(videoId);

            return new CommonResult().success(detailVideoVo);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult().error();
        }
    }

    //查询所有类别
    @RequestMapping("queryAllCategory")
    public CommonResult queryAllCategory(){
        try {
            List<Category> categories = categoryService.queryAllCategory();
            return new CommonResult().success(categories);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult().error();
        }
    }

    //根据类别查询视频
    @RequestMapping("queryCateVideoList")
    public CommonResult queryCateVideoList(String cateId){
        try {
            List<LikeVideoVo> likeVideoVos = videoService.queryCateVideoList(cateId);
            System.out.println("likeVideoVos = " + likeVideoVos);
            return new CommonResult().success(likeVideoVos);
        }catch (Exception e){
            return new CommonResult().error();
        }
    }


    //查看该登录用户关注其他用户发布的视频
    @RequestMapping("queryByUserMoving")
    public CommonResult queryByUserMoving(String userId){
        try {

            return new CommonResult().success();
        }catch (Exception e){
            return new CommonResult().error();
        }
    }
}
