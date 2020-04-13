package com.baizhi.zw.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_video")
@Document(indexName = "yingx",type = "video")
public class Video implements Serializable {
    @Id
    private String id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_work")
    private String title;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_work")
    private String brief;

    @Field(type = FieldType.Keyword)
    @Column(name = "video_path")
    private String videoPath;

    @Field(type = FieldType.Keyword)
    @Column(name = "cover_path")
    private String coverPath;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Field(type = FieldType.Date)
    @Column(name = "publish_date")
    private Date publishDate;

    @Field(type = FieldType.Keyword)
    @Column(name = "category_id")
    private String categoryId;

    @Field(type = FieldType.Keyword)
    @Column(name = "group_id")
    private String groupId;

    @Field(type = FieldType.Keyword)
    @Column(name = "user_id")
    private String userId;
}