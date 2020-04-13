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
            //初始化父表格
            $("#tableCate").jqGrid({
                url: "${path}/category/queryByLevelsAndPage",
                editurl: "${path}/category/editOne",
                datatype: "json",
                rowNum: 10,
                rowList: [ 5, 10, 20, 30],
                pager: '#pageCate',    //分页工具栏
                viewrecords: true,  //显示总条数
                styleUI: "Bootstrap",
                height: "auto",
                autowidth: true,
                colNames: ['ID', '类别名', '级别'],
                colModel: [
                    {name: 'id', width: 55},
                    {name: 'cateName', width: 90,editable: true},
                    {name: 'levels', width: 100},
                ],
                subGrid: true,  //是否开启子表格
                //1.subgridId:点击一行数据时会在父表格中创建一个div，用来容纳子表格，subgridId就是div的id
                //2.rowId:点击行的id
                subGridRowExpanded: function (subgridId, rowId) {
                    createSubGrid(subgridId, rowId);
                }

            });
            $("#tableCate").jqGrid('navGrid', '#pageCate',
                {add: true, edit: true, del: true,edittext: "修改", addtext: "添加", deltext: "删除"},
                {
                    closeAfterEdit: true,  //关闭修改窗口
                },

                {
                    closeAfterAdd: true,  //关闭添加窗口
                },

                {
                    closeAfterDel: true,  //关闭删除窗口
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
                }
            );
        });

        //创建子表格
        function createSubGrid(subgridId, rowId) {
            var subgridTableId = subgridId + "Table";  //子表格的id
            var pagerId = subgridId + "Page";     //子表格工具栏的id

            //在子表格容器中创建子表格和子表格工具栏
            $("#" + subgridId).html(
                "<table id='" + subgridTableId + "'/> " +
                "<div id='" + pagerId + "' />");

            //初始化子表格
            $("#" + subgridTableId).jqGrid({
                url: "${path}/category/queryByParentIdAndPage?parentId="+rowId,
                editurl: "${path}/category/editTwo?parentId="+rowId,
                datatype: "json",
                rowNum: 5,
                rowList: [5, 10, 20, 30],
                pager: '#'+pagerId,    //分页工具栏
                viewrecords: true,  //显示总条数
                styleUI: "Bootstrap",
                height: "auto",
                autowidth: true,
                colNames: ['ID', '类别名', '级别', '一级类别id'],
                colModel: [
                    {name: "id", width: 80},
                    {name: "cateName", width: 130,editable: true},
                    {name: "levels", width: 70, align: "left"},
                    {name: "parentId", width: 70, align: "left"},
                ],
            });
            $("#" + subgridTableId).jqGrid('navGrid', "#" + pagerId,
                {edit: true, add: true, del: true,edittext: "修改", addtext: "添加", deltext: "删除"},
                {
                    closeAfterEdit: true,  //关闭修改窗口
                }, //修改
                {
                    closeAfterAdd: true,  //关闭添加窗口
                }, //添加
                {

                }//删除
            );
        }
    </script>
</head>
<body>
<div class="panel panel-info">
    <%--面板头部--%>
    <div class="panel panel-heading">
        <h3>类别信息</h3>
    </div>
</div>
<%--标签页--%>
<div class="nav nav-tabs">
    <li class="active">
        <a href="#">类别信息</a>
    </li>
</div>
<%--按钮--%>
<button class="btn btn-info">添加类别</button>
<button class="btn btn-info">修改类别</button>
<button class="btn btn-info">删除类别</button>
<%--警告框--%>
<div id="delAlert" class="alert alert-info alert-dismissible" role="alert" style="width: 500px;display: none;" align="center" >
    <span id="alertMsg" />
</div>
<%--初始化表格--%>
<table id="tableCate"/>
<%--分页工具栏--%>
<div id="pageCate"></div>
</body>
</html>