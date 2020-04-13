package com.baizhi.zw.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeVideoVo {
    private String id;
    private String videoTitle;
    private String cover;
    private String path;
    private String uploadTime;
    private String description;

    private Integer likeCount;

    private String cateName;
    private String categoryId;

    private String userId;
    private String userName;
}
