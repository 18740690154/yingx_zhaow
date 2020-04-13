package com.baizhi.zw.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoVo {

    private String id;
    private String videoTitle;
    private String description;
    private String path;
    private String cover;
    private String uploadTime;

    private String cateName;

    private String userPhoto;

    private Integer likeCount;

}
