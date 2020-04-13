<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>应学视频App后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>

</head>
<body>
<!--顶部导航-->
<nav class="navbar navbar-default navbar-inverse ">
    <div class="navbar-header">
        <button class="navbar-toggle collapsed" data-toggle="collapse" data-target="#collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a href="#" class="navbar-brand">应学视频App后台管理系统
        </a>
    </div>
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav navbar-right">
            <li><a href="#">欢迎:<strong>${adminLogin.username}</strong></a></li>
            <li>
                <a href="${path}/admin/logout">
                    退出登录<span class="glyphicon glyphicon-log-out"></span>
                </a>
            </li>
        </ul>
    </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<!--栅格系统-->
<div class="container-fluid">
    <div class="row">
        <%--左侧--%>
        <div class="col-sm-2">
            <%--手风琴--%>
            <div class="panel-group" id="accordion" role="tablist" align="center">
                <div class="panel panel-success">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse " role="tabpanel">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <button class="btn btn-success">
                                        <a href="javascript:$('#mainId').load('${path}/user/user.jsp')">用户展示</a>
                                    </button>
                                </li>
                                <br>
                                <li>
                                    <button class="btn btn-success">
                                        <a href="javascript:$('#mainId').load('${path}/user/echartsGoEasy.jsp')">用户统计</a>
                                    </button>
                                </li>
                                <br>
                                <li>
                                    <button class="btn btn-success">
                                        <a href="javascript:$('#mainId').load('${path}/user/echartsChinaGoEasy.jsp')">用户分布</a>
                                    </button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <hr>

                <div class="panel panel-info">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                分类管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse " role="tabpanel">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <button class="btn btn-info">
                                        <a href="javascript:$('#mainId').load('${path}/category/category.jsp')">类别展示</a>
                                    </button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <hr>

                <div class="panel panel-danger">
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                视频管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse " role="tabpanel">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <button class="btn btn-danger">
                                        <a href="javascript:$('#mainId').load('${path}/video/video.jsp')">视频展示</a>
                                    </button>
                                </li>
                                <br>
                                <li>
                                    <button class="btn btn-danger">
                                        <a href="javascript:$('#mainId').load('${path}/video/searchVideo.jsp')">视频搜索</a>
                                    </button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <hr>

                <div class="panel panel-warning ">
                    <div class="panel-heading" role="tab" id="headingFour">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                                日志管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse " role="tabpanel">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <button class="btn btn-warning">
                                        <a href="javascript:$('#mainId').load('${path}/log/log.jsp')">日志展示</a>
                                    </button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <hr>

                <div class="panel panel-primary ">
                    <div class="panel-heading" role="tab" id="headingFive">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive">
                                反馈管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse " role="tabpanel">
                        <div class="panel-body">
                            <ul class="nav nav-pills nav-stacked">
                                <li>
                                    <button class="btn btn-primary">
                                        <a href="javascript:$('#mainId').load('${path}/comment/comment.jsp')">反馈展示</a>
                                    </button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>


            </div>
        </div>

        <div class="col-sm-10" id="mainId">
            <!--巨幕开始-->
            <div class="jumbotron">
                <h1>欢迎来到应学视频App后台管理系统</h1>
            </div>
            <!--右边轮播图部分-->
            <div id="car" class="carousel slider" data-ride="carousel" align="center">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#car" data-slide-to="0" class="active"></li>
                    <li data-target="#car" data-slide-to="1"></li>
                    <li data-target="#car" data-slide-to="2"></li>
                    <li data-target="#car" data-slide-to="3"></li>
                </ol>
                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <img src="${path}/bootstrap/img/pic1.jpg" alt="">
                        <div class="carousel-caption"></div>
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/pic2.jpg" alt="">
                        <div class="carousel-caption"></div>

                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/pic3.jpg" alt="">
                        <div class="carousel-caption"></div>

                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/pic4.jpg" alt="">
                        <div class="carousel-caption"></div>
                    </div>
                </div>
                <!-- Controls -->
                <a href="#car" class="left carousel-control" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a href="#car" class="carousel-control right" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>
    </div>
    <!--页脚-->
    <div class="panel panel-footer" align="center">
        <div>@百知教育 zhaow@zparkhr.com</div>
    </div>
</div>
</body>
</html>
