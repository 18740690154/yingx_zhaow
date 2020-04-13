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
    <%--引入chain.js--%>
    <script src="${path}/echarts/js/china.js"></script>
    <script>
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            $.get("${path}/user/queryUserDistributionNum",function (data) {
               var series=[];
               for (var i = 0;i<data.length;i++) {
                   var e = data[i];
                   series.push({
                       name: e.title,
                       type: 'map',
                       mapType: 'china',
                       roam: false,
                       label: {
                       normal: {
                           show: false
                       },
                       emphasis: {
                           show: true
                       }
                   },
                       data:e.citys
                   })
               }
               //指定图表的配置项和数据
                var option = {
                    title : {
                        text: '每月用户注册省份分布图',
                        subtext: '纯属虚构',
                        left: 'center'
                    },
                    tooltip : {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data:['男','女']
                    },
                    visualMap: {
                        min: 0,
                        max: 300,
                        left: 'left',
                        top: 'bottom',
                        text:['高','低'],           // 文本，默认为数值文本
                        calculable : true
                    },
                    toolbox: {
                        show: true,
                        orient : 'vertical',
                        left: 'right',
                        top: 'center',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    series : series
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            },"json")
        });
    </script>

    <script>
        //初始化goEasy对象
        var goEasy = new GoEasy({
            host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-76fcbf87a4484fb4bd7a2c54674aa8f9", //替换为您的应用appkey
        });
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            //接收消息
            goEasy.subscribe({
                channel: "listChannel", //替换为您自己的channel，管道名称
                onMessage: function (message) {
                    //获取GoEasy数据
                    var dataGoEasy = message.content;
                    //将Json字符串转化为 javascript对象
                    var data =JSON.parse(dataGoEasy);

                    // 指定图表的配置项和数据
                    var option = {
                        title : {
                            text: '每月用户注册省份分布图',
                            subtext: '纯属虚构',
                            left: 'center'
                        },
                        tooltip : {
                            trigger: 'item'
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'left',
                            data:['男','女']
                        },
                        visualMap: {
                            min: 0,
                            max: 300,
                            left: 'left',
                            top: 'bottom',
                            text:['高','低'],           // 文本，默认为数值文本
                            calculable : true
                        },
                        toolbox: {
                            show: true,
                            orient : 'vertical',
                            left: 'right',
                            top: 'center',
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        series : series
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            });
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