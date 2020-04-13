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
        var content =null;
        $(function () {
            //为搜索按钮添加单击事件
            $("#searchId").click(function () {

                //获取搜索框中内容
                content = $("#contentId").val();


            })
        })

        $("#tableSearchVideo").jqGrid({
            url: "${path}/video/querySearchByPage?content=" + content,
            datatype: "json",
            rowNum: 5,
            rowList: [2, 3, 5, 10, 20, 30],
            pager: '#pageSearchVideo',    //分页工具栏
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
        $("#tableSearchVideo").jqGrid('navGrid', '#pageSearchVideo',
            {edit: false, add: false, del: false, edittext: "修改", addtext: "添加", deltext: "删除"},
            {}, {}, {}
        )


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
    <table id="tableSearchVideo"/>
    <%--分页工具栏--%>
    <div id="pageSearchVideo"></div>
</div>

</body>
</html>