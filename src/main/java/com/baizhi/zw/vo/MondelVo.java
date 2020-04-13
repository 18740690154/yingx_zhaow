package com.baizhi.zw.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MondelVo {
    private String title;
    private List<CityVo> citys;
}
