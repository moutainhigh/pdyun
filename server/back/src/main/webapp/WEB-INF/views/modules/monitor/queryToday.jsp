<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-9-21
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>


<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
    <link href="${ctx}/static/index/index.css" rel="stylesheet" type="text/css">
    <style>
        .shuju{
            margin: 0 auto;
            width: 100%;
        }
        .nav{
            height:50px;
            background-color:#86d0ef;
            font-size:20px;
            line-height:50px;
            padding-left:12px;
            color:#fff;
            font-family: "微软雅黑";
        }
        #rows{
            border-collapse: collapse;
            width:100%;
        }

        #rows tr td{
            text-align: center;
            font-size:16px;
            line-height: 50px;
            width: 50%;
        }
        .hui{
            background-color: #eee;
        }
        .header{
            height:25px;
            width: 100%;
        }
        .header ul li{
            list-style: none;
            float: left;
            width: 25%;
            text-align: left;
            font-size:15px;
        }
    </style>
</head>
<body style="background: #ffffff;">

<div class="panel panel-default">
    <div class="header">
        <ul>
            <li><span>当前刷新时间：<span id="showTimeStr"></span></span></li>
            <li>距下次刷新：<span id="showTime" style="">0</span> 秒</li>
            <li>刷新频率： <select class="" style="width: 100px;" id="intervalSeconds">
                <option value="5">5秒</option>
                <option value="4">4秒</option>
                <option value="3">3秒</option>
                <option value="2">2秒</option>
            </select></li>
            <li>自动刷新：
                <span>
                    <input class='tgl tgl-light' id='cb1' type='checkbox' data-flag="1" onclick="_refresh_fun(this)" >
                    <label class='tgl-btn' for='cb1'></label>
            </span>
            </li>
        </ul>
    </div>
    </div>

    <div class="panel-body" style="padding:10px 10px 10px 20px;border: none"></div>
    <div class="shuju">
        <div class="nav">当日数据统计</div>
        <div class="connect">
            <table id="rows">
                <thead>
                <tr class="hui">
                    <td>注册人数</td>
                    <td><div class="registerNumbers"></div></td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>充值金额</td>
                    <td><div class="rechargeMoney"></div></td>
                </tr>
                <tr class="hui">
                    <td>提现人数</td>
                    <td><div class="withdrawalsPeoples"></div></td>
                </tr>
                <tr>
                    <td>提现金额</td>
                    <td><div class="withdrawalsMoney"></div></td>
                </tr>
                <tr class="hui">
                    <td>交易笔数</td>
                    <td><div class="tradePens"></div></td>
                </tr>
                <tr>
                    <td>盈亏</td>
                    <td><div class="formula"></div></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script type="application/javascript">

    $(function(){
        var registerNumbers = $(".registerNumbers").val();
        var rechargeMoney = $(".rechargeMoney").val();
        var withdrawalsPeoples = $(".withdrawalsPeoples").val();
        var tradePens = $(".tradePens").val();
        var profitAndLossMoney = $(".profitAndLossMoney").val();
        var withdrawalsMoney = $(".withdrawalsMoney").val();
        if(registerNumbers == ""||rechargeMoney==""||withdrawalsPeoples==""||tradePens==""||profitAndLossMoney==""||withdrawalsMoney==""){
            $(".registerNumbers").text("0");
            $(".rechargeMoney").text("0");
            $(".withdrawalsPeoples").text("0");
            $(".withdrawalsMoney").text("0");
            $(".tradePens").text("0");
            $(".profitAndLossMoney").text("0");
        };

        _ref();

        $('#cb1').click();
    })

    //新修改的，控制台左上角时间不变
    var dd = new Date();
    $('#showTimeStr').text(dd.toLocaleString());

    var _refresh = false;
    function _ref(_fun){
        (_fun || function(){})();
        if(_refresh){
           return ;
        }
        _refresh = true;
        var d = new Date();
        $('#showTimeStr').text(d.toLocaleDateString() + '' + d.toLocaleTimeString());
        $.post('${ctx}/monitor/queryToday', function(data) {
            $(".registerNumbers").text("0");
            $(".rechargeMoney").text("0");
            $(".withdrawalsPeoples").text("0");
            $(".withdrawalsMoney").text("0");
            $(".tradePens").text("0");
            $(".profitAndLossMoney").text("0");
            _refresh = false;
            for(var attr in data){
                $('.' + attr).text(data[attr]);
            }
        });
    }

    //按时间段查询
    /*function timeSectionRef(){
        var startTime = $('#createTimeMin').datebox('getValue');
        var endTime = $('#createTimeMax').datebox('getValue');
        if(startTime == '' && endTime == ''){
            return;
        }
        $('#cb1').attr('checked',false);
        $($('#cb1')).data('flag', 0);
        _refresh_fun($('#cb1'));
        $(".registerNumbers").text("0");
        $(".rechargeMoney").text("0");
        $(".withdrawalsPeoples").text("0");
        $(".withdrawalsMoney").text("0");
        $(".tradePens").text("0");
        $(".profitAndLossMoney").text("0");
        $.ajax({
            url: '${ctx}/monitor/queryDateSection',
            type: 'POST',
            data: {
                "createTimeMin": startTime,
                "createTimeMax":endTime
            },
            success: function (data) {
                for(var attr in data){
                    $('.' + attr).text(data[attr]);
                }
            }
        });
    }*/
    var __ref = null;

    var _interval_time = null;
    var _time = null;

    function _interval_ref() {
        var count = $('#intervalSeconds').val();
        $('#showTime').text(count);
        clearInterval(__ref);
        _time = function(){
            count--;
            $('#showTime').text(count);
            if(count == 0){
                clearInterval(__ref);
                (_ref)(function(){
                    _interval_ref();
                });
            }
        };
        __ref = setInterval(_time, 1000);
    }

    function _clear_interval_ref(){
        clearTimeout(__ref);
    }
    function _refresh_fun(the){
        if($(the).data('flag') == '1'){
            _clear_interval_ref();
            _interval_ref();
            $(the).data('flag', 0);
            // $(the).html('关闭自动刷新');
        }else{
            _clear_interval_ref();
            $(the).data('flag', 1);
            //  $(the).html('开启自动刷新');
            $('#showTime').text(0);
        }
    }
</script>
</body>
</html>