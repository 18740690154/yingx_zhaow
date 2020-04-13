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
            $("#tableUser").jqGrid({
                url: "${path}/user/queryByPage",
                editurl: "${path}/user/edit",
                datatype: "json",
                rowNum: 3,
                rowList: [2, 3, 5, 10, 20, 30],
                pager: '#pageUser',    //分页工具栏
                viewrecords: true,  //显示总条数
                styleUI: "Bootstrap",
                height: "auto",
                autowidth: true,
                colNames: ['ID', '用户名', '手机号', '头像', '签名', '微信', '状态', '注册时间','性别','地址'],
                colModel: [
                    {name: 'id', width: 120, align: "center"},
                    {name: 'username', width: 50, align: "center", editable: true},
                    {name: 'phone', width: 60, align: "center", editable: true},
                    {
                        name: 'headImg', width: 120, align: "center", editable: true, edittype: "file",
                        //cellvalue：当前列的值  options：操作  rowObject:整行的数据
                        formatter: function (cellvalue, options, rowObject) {
                            //return "<img src='/upload/" + cellvalue + "' width='200px' height='100px'/>";
                            return "<img src='" + rowObject.headImg + "' width='200px' height='100px' />";
                        }
                    },
                    {name: 'sign', width: 80, align: "center", editable: true},
                    {name: 'wechat', width: 80, align: "center", editable: true},
                    {name: 'status', width: 50, align: "center",
                        formatter: function (cellvalue, options, rowObject) {
                            if (cellvalue == 1) {
                                return "<button class='btn btn-success' onclick='updateStatus(\"" + rowObject.id + "\",\"" + cellvalue + "\")'>冻结</button>";
                            } else {
                                return "<button class='btn btn-danger'  onclick='updateStatus(\"" + rowObject.id + "\",\"" + cellvalue + "\")'>解除冻结</button>";
                            }
                        }
                    },
                    {name: 'createDate', width: 80, align: "center",
                        formatter: 'date', formatoptions: {
                            srcformat: 'Y-m-d H:i:s',
                            newformat: 'Y-m-d H:i:s'
                        }
                    },
                    {name: 'sex', width: 50, align: "center", editable: true,edittype:"select",
                        editoptions: {value:'男:男;女:女'}
                    },
                    {name: 'address', width: 80, align: "center", editable: true}

                ],
            });
            //表格的工具栏
            $("#tableUser").jqGrid('navGrid', '#pageUser',
                {edit: true, add: true, del: true, edittext: "修改", addtext: "添加", deltext: "删除"},
                {
                    closeAfterEdit: true,  //关闭修改窗口

                    afterSubmit: function (response) {
                        /*数据入库时出现的问题
                            1.文件未上传
                            2.图书的路径不对
                         */
                        //fileElementId:需要上传文件域的id
                        //修改图片路径  id,图片路径
                        //文件上传
                        $.ajaxFileUpload({
                            url: "${path}/user/uploadFile",
                            fileElementId: "headImg",
                            data: {id: response.responseText},
                            type: "post",
                            success: function () {
                                //刷新表单
                                $("#tableUser").trigger("reloadGrid");
                            }
                        })
                        return "null";
                    }
                },  //修改
                {
                    closeAfterAdd: true,  //关闭添加窗口
                    //文件上传
                    afterSubmit: function (response) {
                        /*数据入库时出现的问题
                            1.文件未上传
                            2.图书的路径不对
                         */
                        //fileElementId:需要上传文件域的id
                        //修改图片路径  id,图片路径
                        $.ajaxFileUpload({
                            url: "${path}/user/uploadFile",
                            fileElementId: "headImg",
                            data: {id: response.responseText},
                            type: "post",
                            success: function () {
                                //刷新表单
                                $("#tableUser").trigger("reloadGrid");
                            }
                        })
                        return "null";
                    }
                },  //添加
                {}   //删除
            );

            //发送验证码

            $("#addonId").click(function () {
                var phone = $("#phoneInput").val();

                $.ajax({
                    url: "${path}/user/sendPhoneCode",
                    data: {"phone": phone},
                    datatype: "json",
                    type: "post",
                    success: function (data) {
                    }
                })
            })

            //导出用户信息
            $("#exportId").click(function () {
                $.ajax({
                    url: "${path}/user/export",
                    type: "post",
                    success: function (data) {
                    }
                })
            })

        });

        //修改状态
        function updateStatus(id, status) {
            //根据id修改状态

            if (status == 1) {
                $.ajax({
                    url: "${path}/user/edit",
                    data: {"id": id, "status": "0", "oper": "edit"},
                    type: "post",
                    success: function () {
                        //刷新表单
                        $("#tableUser").trigger("reloadGrid");
                    }
                })
            } else {
                $.ajax({
                    url: "${path}/user/edit",
                    data: {"id": id, "status": "1", "oper": "edit"},
                    type: "post",
                    success: function () {
                        //刷新表单
                        $("#tableUser").trigger("reloadGrid");
                    }
                })
            }
        }
    </script>
</head>
<body>
<div class="panel panel-success">
    <%--面板头部--%>
    <div class="panel panel-heading">
        <h3>用户信息</h3>
    </div>
</div>

<%--发送验证码--%>
<div class="input-group" style="width: 400px;height: 30px;float: right">
    <input id="phoneInput" type="text" class="form-control" placeholder="请输入验证码" aria-describedby="basic-addon2">
    <span class="input-group-addon " id="addonId">点击发送验证码</span>
</div>
<%--标签页--%>
<div class="nav nav-tabs">
    <li class="active">
        <a href="#">用户信息</a>
    </li>
</div>
<%--按钮--%>
<div class="panel">
    <button id="exportId" class="btn btn-success">导出用户信息</button>
    <button class="btn btn-success">导入用户</button>
    <button class="btn btn-success">测试按钮</button>
</div>
<%--初始化表格--%>
<table id="tableUser"/>
<%--分页工具栏--%>
<div id="pageUser"></div>
</body>
</html>