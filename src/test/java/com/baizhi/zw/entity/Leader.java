package com.baizhi.zw.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leader {
    @ExcelIgnore  //不展示
    private String id;
    @Excel(name = "姓名",needMerge = true)
    private String name;
    @Excel(name = "部门",needMerge = true)
    private String dep;
    @ExcelCollection(name = "部门员工信息")
    private List<Emp> emps;
}
