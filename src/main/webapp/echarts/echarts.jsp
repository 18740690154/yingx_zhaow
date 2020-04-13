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
    <%--引入jQuery--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <%--引入echarts--%>
    <script src="${path}/echarts/js/echarts.js"></script>
    <script>
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '用户月注册人数统计',  //标题
                    subtext: "1-6月",   //副标题
                    link: "${path}/main/main.jsp", //主标题超链接,新建窗口打开
                    target:"self"  //当前窗口打开
                },
                tooltip: {},  //鼠标提示
                legend: {
                    data: ['男','女']   //选项卡
                },
                xAxis: {   //横轴
                    data: ["1月", "2月", "3月", "4月", "5月", "6月"]
                },
                yAxis: {}, //纵轴:自适应
                series: [
                    {
                    name: '男',
                    type: 'bar',   //展示的图形的类型
                    data: [15, 10, 35, 15, 20, 30]
                    },{
                    name: '女',
                    type: 'bar',   //展示的图形的类型
                    data: [5, 20, 30, 10, 5, 15]
                    }
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        })
    </script>
</head>
<body>
<div align="center">
    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="main" style="width: 600px;height:400px;"></div>

</div>
</body>
</html>