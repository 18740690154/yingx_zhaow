package com.baizhi.zw.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp {
    @Excel(name = "Id",width = 15,height = 15)
    private String id;
    @Excel(name = "姓名",width = 15,height = 15)
    private String name;
    @Excel(name = "年龄",width = 15,height = 15)
    private Integer age;
    @Excel(name = "工资",width = 15,height = 15)
    private Double price;
    @Excel(name = "生日",format = "yyyy年MM月dd日",width = 15,height = 15)
    private Date birthday;

}
