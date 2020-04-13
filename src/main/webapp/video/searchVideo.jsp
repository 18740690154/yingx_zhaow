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
        $(function () {
            //清空表单
            $("#tableSearchVideo").empty();

            //为搜索按钮添加单击事件
            $("#searchId").click(function () {
                //获取搜索框中内容
                var content = $("#contentId").val();
                //向后台发请求查询数据
                $.ajax({
                    url: "${path}/video/querySearch",
                    type: "post",
                    dataType: "JSON",
                    data: {"content": content},
                    success: function (data) {
                        $("#tableSearchVideo").append("<tr>" +
                            "<td>ID</td>" +
                            "<td>标题</td>" +
                            "<td>描述</td>" +
                            "<td>封面</td>" +
                            "<td>上传时间</td>" +
                            "</tr>");
                        //遍历取出数据
                        $.each(data, function (index, video) {
                            //获取数据渲染页面
                            $("#tableSearchVideo").append("<tr>" +
                                "<td width='30%'>" + video.id + "</td>" +
                                "<td width='10%'>" + video.title + "</td>" +
                                "<td width='25%'>" + video.brief + "</td>" +
                                "<td width='250%'><img src='" + video.coverPath + "' style='width: 200px;height: 100px' /></td>" +
                                "<td width='10%'>" + video.publishDate + "</td>" +
                                "</tr>");
                        })
                    }
                })
            });
        });

    </script>
</head>
<body>
<div align="center">
    <%--搜索框--%>
    <div class="input-group" style="width: 500px">
        <input type="text" class="form-control" id="contentId" placeholder="请输入要搜素的内容">
        <span class="input-group-btn">
            <button class="btn btn-primary" id="searchId">点击搜索</button>
        </span>
    </div>
</div>
<hr>
<div class="panel panel-default">
    <%--面板头部--%>
    <div class="panel panel-heading">搜素结果</div>
    <%--初始化表格--%>
        <table id="tableSearchVideo"></table>
</div>

</body>
</html>