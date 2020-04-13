package com.baizhi.zw.controller;

import com.baizhi.zw.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("log")
public class LogController {
    @Autowired
    LogService logService;

    @RequestMapping("queryByPage")
    @ResponseBody
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Map<String, Object> map = logService.queryByPage(page, rows);
        return map;
    }
}
