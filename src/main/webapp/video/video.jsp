<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>

    <script>
        $("#tableVideo").jqGrid({
            url: "${path}/video/queryByPage",
            editurl: "${path}/video/edit",
            datatype: "json",
            rowNum: 3,
            rowList: [2, 3, 5, 10, 20, 30],
            pager: '#pageVideo',    //分页工具栏
            viewrecords: true,  //显示总条数
            styleUI: "Bootstrap",
            height: "auto",
            autowidth: true,
            colNames: ['ID', '标题', '描述', '视频', '上传时间', '分组ID', '类别ID', '用户ID'],
            colModel: [
                {name: 'id', width: 120, align: "center"},
                {name: 'title', width: 50, align: "center", editable: true},
                {name: 'brief', width: 60, align: "center", editable: true},
                {
                    name: 'videoPath', width: 120, align: "center", editable: true, edittype: "file",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<video id='media' src='" + cellvalue + "' controls width='200px' heigt='300px' poster='" + rowObject.cover + "'/>"
                    }
                },
                {
                    name: 'publishDate',
                    width: 80,
                    align: "center",
                    formatter: 'date',
                    formatoptions: {srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}
                },
                {name: 'groupId', width: 80, align: "center"},
                {name: 'categoryId', width: 50, align: "center"},
                {name: 'userId', width: 80, align: "center"}
            ],
        });
        //表格的工具栏
        $("#tableVideo").jqGrid('navGrid', '#pageVideo',
            {edit: true, add: true, del: true, edittext: "修改", addtext: "添加", deltext: "删除"},
            {
                closeAfterEdit: true, //关闭修改对话框
                beforeShowForm :function(obj){
                    obj.find("#videoPath").attr("disabled",true);//禁用文件input
                }
            },  //修改操作后的额外操作
            {
                closeAfterAdd: true,  //关闭添加对话框
                afterSubmit:function (data) {
                    $.ajaxFileUpload({
                        fileElementId:"videoPath",  //需要上传的文件域的ID，即<input type="file">的ID。
                        url: "${path}/video/uploadVideoFile",
                        type:"post",
                        data:{id:data.responseText},
                        success:function () {
                            //刷新表单
                            $("#tableVideo").trigger("reloadGrid");
                        }

                    });
                    return "null";
                }
            },  //添加操作后的额外操作
            {
                closeAfterDel: true, //关闭删除对话框
                //删除方法提交后的操作
                afterSubmit:function (response) {
                    //向警告框中写入内容
                    $("#alertMsg").html(response.responseJSON.message);
                    //展示警告框
                    $("#delAlert").show();

                    //警告框自动关闭
                    setTimeout(function () {
                        //隐藏警告框
                        $("#delAlert").hide();
                    },2000);

                    return "null";
                }
            }   //删除操作后的额外操作
        )
    </script>
</head>
<body>
<div class="panel panel-danger">
    <%--面板头部--%>
    <div class="panel panel-heading">
        <h3>视频信息</h3>
    </div>
</div>
<%--标签页--%>
<div class="nav nav-tabs">
    <li class="active">
        <a href="#">视频信息</a>
    </li>
</div>
<%--按钮--%>
<button class="btn btn-danger">添加视频</button>
<button class="btn btn-danger">修改视频</button>
<button class="btn btn-danger">删除视频</button>
<%--警告框--%>
<div id="delAlert" class="alert alert-info alert-dismissible" role="alert" style="width: 500px;display: none;"
     align="center">
    <span id="alertMsg"/>
</div>
<%--初始化表格--%>
<table id="tableVideo"/>
<%--分页工具栏--%>
<div id="pageVideo"></div>
</body>
</html>