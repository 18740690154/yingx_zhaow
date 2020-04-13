package com.baizhi.zw.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoPo {

    private String id;
    private String vTitle;
    private String vBrief;
    private String vVideoPath;
    private String vCoverPath;
    private String vPublishDate;

    private String cateName;

    private String headImg;
}
