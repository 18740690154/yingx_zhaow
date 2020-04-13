package com.baizhi.zw.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_user")
public class User implements Serializable {
    @Excel(name = "ID",width = 40)
    @Id
    private String id;
    @Excel(name = "用户名")
    private String username;
    @Excel(name = "手机号码",width = 20)
    private String phone;
    @Excel(name = "头像",width = 40)
    @Column(name = "head_img")
    private String headImg;
    @Excel(name = "签名",width = 20)
    private String sign;
    @Excel(name = "微信",width = 20)
    private String wechat;
    @Excel(name = "状态",width = 5)
    private String status;
    @Excel(name = "注册时间",format = "yyyy-MM-dd",width = 15)
    @Column(name = "create_date")
    private Date createDate;
    @Excel(name = "性别",width = 5)
    private String sex;
    @Excel(name = "地址")
    private String address;

}