package com.baizhi.zw.service;

import com.baizhi.zw.entity.Video;

import com.baizhi.zw.vo.DetailVideoVo;
import com.baizhi.zw.vo.LikeVideoVo;
import com.baizhi.zw.vo.VideoVo;
import org.elasticsearch.common.recycler.Recycler;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

public interface VideoService {

    Map<String,Object> queryByPage(Integer page, Integer rows);

    String add(Video video);

    void update(Video video);

    Map<String,Object> delete(Video video);

    //上传视频文件----本地
    void uploadVideo(MultipartFile videoPath, String id, HttpSession httpSession);
    //上传视频文件----阿里云
    void uploadVideoAliyun(MultipartFile videoPath, String id, HttpSession httpSession);
    void uploadVideoAliyuns(MultipartFile videoPath, String id, HttpSession httpSession);

    //首页:按时间查询视频并展示视频
    List<VideoVo> queryByReleaseTime();

    //首页:搜索视频
    List<LikeVideoVo> queryByLikeVideoName(String content);

    //根据视频id查询视频
    DetailVideoVo queryByVideoDetail(String videoId);

    //根据用户id或类别id查询视频 排除根据视频id查询出的视频
    List<LikeVideoVo> queryByVideo(String videoId,String cateId,String userId);

    //根据类别id查询视频
    List<LikeVideoVo> queryCateVideoList(String cateId);

    List<Video> querySearchByPage(String content,Integer page,Integer rows);

    List<Video> querySearch(String content);
}
