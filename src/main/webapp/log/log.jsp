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
            //初始化表格
            $("#tableLog").jqGrid({
                url: "${path}/log/queryByPage",
                datatype: "json",
                rowNum: 10,
                rowList: [ 5, 10, 20, 30],
                pager: '#pageLog',    //分页工具栏
                viewrecords: true,  //显示总条数
                styleUI: "Bootstrap",
                height: "auto",
                autowidth: true,
                colNames: ['ID', '操作者', '操作时间', '操作', '操作结果'],
                colModel: [
                    {name: 'id', width: 120, align: "center"},
                    {name: 'who', width: 50, align: "center", editable: true},
                    {name: 'time', width: 80, align: "center", formatter:'date', formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}},
                    {name: 'what', width: 60, align: "center", editable: true},
                    {name: 'result', width: 80, align: "center", editable: true},
                ],
            });
            //表格的工具栏
            $("#tableLog").jqGrid('navGrid', '#pageLog',
                {edit: false, add: false, del: false, edittext: "修改", addtext: "添加", deltext: "删除"},
                {},  //修改
                {},  //添加
                {}   //删除
            );
        });
    </script>
</head>
<body>
<div class="panel panel-warning">
    <%--面板头部--%>
    <div class="panel panel-heading">
        <h3>日志信息</h3>
    </div>
</div>
<%--标签页--%>
<div class="nav nav-tabs">
    <li class="active">
        <a href="#">日志信息</a>
    </li>
</div>

<%--初始化表格--%>
<table id="tableLog"/>
<%--分页工具栏--%>
<div id="pageLog"></div>
</body>
</html>