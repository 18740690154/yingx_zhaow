package com.baizhi.zw.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    @Excel(name = "Id")
    private String id;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "头像",type = 2,width = 25,height = 30)
    private String path;
}
