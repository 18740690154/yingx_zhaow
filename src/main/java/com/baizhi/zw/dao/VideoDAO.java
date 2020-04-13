package com.baizhi.zw.dao;

import com.baizhi.zw.entity.Video;

import java.util.List;

import com.baizhi.zw.po.DetailVideoPo;
import com.baizhi.zw.po.LikeVideoPo;
import com.baizhi.zw.po.VideoPo;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface VideoDAO extends Mapper<Video> {
    List<VideoPo> queryByReleaseTime();

    List<LikeVideoPo> queryByLikeVideoName(String content);

    DetailVideoPo queryByVideoDetail(String videoId);

    List<LikeVideoPo> queryByVideo(@Param("videoId") String videoId,@Param("cateId") String cateId, @Param("userId") String userId);

    //根据类别id查询视频
    List<LikeVideoPo> queryCateVideoList(String cateId);
}