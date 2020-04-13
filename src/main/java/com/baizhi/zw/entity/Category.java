package com.baizhi.zw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_category")
public class Category implements Serializable {
    @Id
    private String id;
    @Column(name = "cate_name")
    private String cateName;
    private String levels;
    @Column(name = "parent_id")
    private String parentId;

    @Transient
    private List<Category> categoryList;
}