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
        #rows {
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
            text-align: left;
            font-size:15px;
        }
        .header ul li:first-child{
            width: 20%;
        }
        header ul li:nth-child(2){
            width: 12%;
        }
        header ul li:nth-child(3){
            width: 14% ;
        }
        header ul li:nth-child(4){
            width: 14% ;
        }
    </style>

</head>
<body style="background: #ffffff; overflow: scroll;">
<div class="panel panel-default">
<div class="header">
    <ul>
        <li><span>当前刷新时间：<span id="showTimeStr"></span></span></li>
        <li style="width: 12%">距下次刷新：<span id="showTime" style="">0</span> 秒</li>
        <li>刷新频率： <select class="" style="width: 100px;" id="intervalSeconds">
            <option value="5">5秒</option>
            <option value="4">4秒</option>
            <option value="3">3秒</option>
            <option value="2">2秒</option>
        </select></li>
        <li style="width: 12%">自动刷新：
            <span  class='tg-list-item'>
                    <input class='tgl tgl-light' id='cb1' type='checkbox' checked onclick="_refresh_fun(this)" >
                    <label class='tgl-btn' for='cb1'></label>
            </span>
        </li>
        <li>
            <span>开始时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="createTimeMin" />
        </li>
        <li>
            <span>结束时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="createTimeMax" />
        </li>
        <li>
            <button id="queryListBt" class="btn btn-info margin-r10" onclick="selectByTimeSection()">查询</button>
            <button id="clearBt" class="btn btn-info margin-r10" onclick="_clearQueryParams()">清空</button>
        </li>
    </ul>
</div>
</div>
    <div class="panel-body" style="padding:10px 10px 10px 20px;border: none">
    </div>
    <div class="shuju">
        <div class="nav">数据汇总</div>
        <div class="connect">
            <table id="rows">
                <thead>
                <tr class="hui">
                    <td>客户总余额</td>
                    <td><div class="userMoney"></div></td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>注册人数</td>
                    <td><div class="userNumbers"></div></td>
                </tr>
                <tr class="hui">
                    <td>客户总盈亏</td>
                    <td><div class="formula"></div></td>
                </tr>
                <tr>
                    <td>充值总额</td>
                    <td><div class="userRecharge"></div></td>
                </tr>
                <tr class="hui">
                    <td>充值总笔数</td>
                    <td><div class="userRechargePens"></div></td>
                </tr>
                <tr>
                    <td>提现人数</td>
                    <td><div class="userWithdrawalsNumbers"></div></td>
                </tr>
                <tr class="hui">
                    <td>提现笔数</td>
                    <td><div class="userWithdrawalsPens"></div></td>
                </tr>
                <tr>
                    <td>提现总额</td>
                    <td><div class="withdrawalsMoney"></div></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script type="application/javascript">
    $(function(){
        var userMoney = $(".userMoney").val();
        var userNumbers = $(".userNumbers").val();
        var userProfitAndLoss = $(".userProfitAndLoss").val();
        var userRecharge = $(".userRecharge").val();
        var userRechargePens = $(".userRechargePens").val();
        var userWithdrawalsNumbers = $(".userWithdrawalsNumbers").val();
        var userWithdrawalsPens = $(".userWithdrawalsPens").val();
        if(userMoney == "" ||userNumbers == "" ||userProfitAndLoss == "" ||userRecharge == "" ||userRechargePens == "" ||userWithdrawalsNumbers == "" ||userWithdrawalsPens == "" ){
            $(".userMoney").text("0");
            $(".userNumbers").text("0");
            $(".userProfitAndLoss").text("0");
            $(".userRecharge").text("0");
            $(".userRechargePens").text("0");
            $(".userWithdrawalsNumbers").text("0");
            $(".userWithdrawalsPens").text("0");

        }
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
        $.post('${ctx}/monitor/queryAll', function(data) {
            $(".userMoney").text("0");
            $(".userNumbers").text("0");
            $(".userProfitAndLoss").text("0");
            $(".userRecharge").text("0");
            $(".userRechargePens").text("0");
            $(".userWithdrawalsNumbers").text("0");
            $(".userWithdrawalsPens").text("0");
            _refresh = false;
            for(var attr in data){
                $('.' + attr).text(data[attr]);
//                if(report[attr]){
//                    report[attr].addPoint(data[attr]);
//                }
            }
        });
    }

    //按时间段查询
    function selectByTimeSection(){
        var startTime = $('#createTimeMin').datebox('getValue');
        var endTime = $('#createTimeMax').datebox('getValue');
        if(startTime == '' && endTime == ''){
            return;
        }
        $('#cb1').attr('checked',false);
        $($('#cb1')).data('flag', 0);
        $(".userMoney").text("0");
        $(".userNumbers").text("0");
        $(".userProfitAndLoss").text("0");
        $(".userRecharge").text("0");
        $(".userRechargePens").text("0");
        $(".userWithdrawalsNumbers").text("0");
        $(".userWithdrawalsPens").text("0");
        _refresh_fun($('#cb1'));
        layer.load(1, {
            shade: [0.5,'#fff']
        });
        $.ajax({
            url: '${ctx}/monitor/queryAllDateSection',
            type: 'POST',
            data: {
                "createTimeMin": startTime,
                "createTimeMax":endTime
            },
            success: function (data) {
                layer.closeAll();
                for(var attr in data){
                    $('.' + attr).text(data[attr]);
                }
            }
        });
    }

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

    _ref();
    _interval_ref();

    function _clearQueryParams() {
        $('#createTimeMin,#createTimeMax').datetimebox('setValue', '');
    }
</script>
</body>
</html>