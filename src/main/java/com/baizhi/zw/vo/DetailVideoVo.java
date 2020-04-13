package com.baizhi.zw.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVideoVo {
    private String id;
    private String videoTitle;
    private String cover;
    private String path;
    private String uploadTime;
    private String description;

    private Integer likeCount;
    private Integer playCount;
    private Boolean isAttention;

    private String categoryId;
    private String cateName;

    private String userId;
    private String userPicImg;
    private String userName;


    private List<LikeVideoVo> videoList;

}
