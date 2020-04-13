package com.baizhi.zw.po;

import com.baizhi.zw.vo.LikeVideoVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVideoPo {
    private String id;
    private String vTitle;
    private String vBrief;
    private String vVideoPath;
    private String vCoverPath;
    private String vPublishDate;

    private String cId;
    private String cateName;

    private String uId;
    private String headImg;
    private String uUserName;

    private List<LikeVideoVo> LikeVideoVo;

}
