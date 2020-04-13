package com.baizhi.zw.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {

    private String status;
    private String message;
    private Object data;

    public CommonResult success(String status, String message, Object data) {
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus(status);
        commonResult.setMessage(message);
        commonResult.setData(data);
        return commonResult;
    }

    public CommonResult success(String message, Object data) {
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("100");
        commonResult.setMessage(message);
        commonResult.setData(data);
        return commonResult;
    }


    public CommonResult success( Object data) {
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("100");
        commonResult.setMessage("查询成功");
        commonResult.setData(data);
        return commonResult;
    }


    public CommonResult success() {
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("100");
        commonResult.setMessage("查询成功");
        commonResult.setData(null);
        return commonResult;
    }

    public CommonResult error(String status,String message,Object data){

        CommonResult commonResult = new CommonResult();
        commonResult.setStatus(status);
        commonResult.setMessage("查询失败:"+message);
        commonResult.setData(data);
        return commonResult;
    }


    public CommonResult error(String message,Object data){

        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("104");
        commonResult.setMessage("查询失败:"+message);
        commonResult.setData(data);
        return commonResult;
    }

    public CommonResult error(String message){

        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("104");
        commonResult.setMessage("查询失败:"+message);
        commonResult.setData(data);
        return commonResult;
    }

    public CommonResult error(Object data){

        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("104");
        commonResult.setMessage("查询失败");
        commonResult.setData(data);
        return commonResult;
    }


    public CommonResult error(){

        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("104");
        commonResult.setMessage("查询失败");
        commonResult.setData(null);
        return commonResult;
    }
}
