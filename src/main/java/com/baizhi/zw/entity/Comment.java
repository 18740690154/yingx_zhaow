package com.baizhi.zw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_comment")
public class Comment {
    @Id
    private String id;

    private String title;

    private String content;

    @Column(name = "user_id")
    private String userId;
    @Column(name = "save_time")
    private Date saveTime;

}