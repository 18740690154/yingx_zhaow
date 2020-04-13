package com.baizhi.zw.controller;

import com.baizhi.zw.entity.Video;
import com.baizhi.zw.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("video")
public class VideoController {

    @Autowired
    VideoService videoService;

    @RequestMapping("queryByPage")
    @ResponseBody
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Map<String, Object> map = videoService.queryByPage(page, rows);
        return map;
    }


    @ResponseBody
    @RequestMapping("edit")
    public Object edit(Video video,String oper){
        System.out.println("video = " + video);
        if (oper.equals("add")){
            String uuid = videoService.add(video);
            return uuid;
        }
        if (oper.equals("edit")){
            videoService.update(video);
        }
        if (oper.equals("del")){
            Map<String, Object> map = videoService.delete(video);
            return map;
        }

        return null;
    }

    @ResponseBody
    @RequestMapping("uploadVideoFile")
    public void uploadVideoFile(MultipartFile videoPath, String id, HttpSession httpSession){
        //videoService.uploadVideo(videoPath,id,httpSession); 本地
        //videoService.uploadVideoAliyun(videoPath, id, httpSession); //阿里云
        videoService.uploadVideoAliyuns(videoPath, id, httpSession); //阿里云
    }

    @ResponseBody
    @RequestMapping("querySearch")
    public List<Video> querySearch(String content){
        List<Video> videos = videoService.querySearch(content);
        return videos;
    }

    @ResponseBody
    @RequestMapping("querySearchByPage")
    public List<Video> querySearchByPage(String content,Integer page,Integer rows){
        System.out.println(content);
        System.out.println(page);
        System.out.println(rows);
        List<Video> videos = videoService.querySearchByPage(content,page,rows);
        return videos;
    }
}
