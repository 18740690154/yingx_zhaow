package com.baizhi.zw.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeVideoPo {
    private String id;
    private String vTitle;
    private String vBrief;
    private String vVideoPath;
    private String vCoverPath;
    private String vPublishDate;

    private String cId;
    private String cateName;

    private String uId;
    private String uUserName;
}
