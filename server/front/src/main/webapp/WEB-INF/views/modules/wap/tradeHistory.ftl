<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <#include "/wap/header.ftl" >
    <link href="${ctxStatic}/all/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/lishidingdan.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"  src="${ctxStatic}/all/js/rem.js"></script>
    <title>${title}</title>
</head>
<body>
<section class="wrap-page">
    <div class="user_top">
        <div class="his_date icon left">日期</div>
        <div class="his_time icon left" ><input type="text" id="inputDate" style="width:3rem;background: #282828; color:#fff;line-height:0.6rem;font-size: 0.25rem; text-align: center;"></input></div>
        <div class="his_chaxun icon right" onclick="selectHistory()">查询</div>
    </div>
    <div class="pinzhong">
        <ul class="dis">
            <li>品种<i></i></li>
            <li>方向<i></i></li>
            <li>平仓时间<i></i></li>
            <li>盈利金额<i></i></li>
            <li>状态</li>
        </ul>
    </div>
    <#if list??><#list list as it>
    <div class="tong clearfix">
        <ul class="dis clearfix" id="first_${it.id}">
            <li>${it.contractName}<i></i></li>
            <li><img src="<#if it.buyUpDown?number == 0>${ctxStatic}/all/image/zhang.png<#else>${ctxStatic}/all/image/xiajiang.png</#if>"><i></i></li>
            <li>${it.sellTime?time}<i></i></li>
            <#if ((it.sellPoint - it.buyPoint) gt 0 && (it.buyUpDown?number == 0)) ||  ( (it.sellPoint - it.buyPoint) lt 0 && (it.buyUpDown?number == 1) )>
                <li class="red">¥ ${it.difMoney}<i></i></li>
                <li class="red">盈</li>
            <#elseif ( (it.sellPoint - it.buyPoint) lt 0 && (it.buyUpDown?number == 0) ) ||  ( (it.sellPoint - it.buyPoint) gt 0 && (it.buyUpDown?number == 1) )>
                <li class="green">¥ ${it.difMoney}<i></i></li>
                <li class="green">亏</li>
            <#else>
                <li class="grey">¥ ${it.difMoney}<i></i></li>
                <li class="grey">平</li>
            </#if>
        </ul>
        <div class="dis_list" id="second_${it.id}" style="display: none">
            <div class="dis_list_bianhao">订单编号 :            <span>${it.serialNo}</span></div>
            <img src="${ctxStatic}/all/image/bianhao.png" class="bianhao_img">
            <div class="kaicang">
                <ul class="kaicang_list">
                    <li>开仓价格&nbsp;&nbsp;<span>${it.buyPoint}</span><i></i></li>
                    <#list map?keys as key>
                        <#if it.code == key && map[key] == 0>
                            <li>点位&nbsp;&nbsp;<span>${it.offPoint?number * 1}</span></li>
                        <#elseif it.code == key && map[key] == 1>
                            <li>点位&nbsp;&nbsp;<span>${it.offPoint?number * 10}</span></li>
                        <#elseif it.code == key && map[key] == 2>
                            <li>点位&nbsp;&nbsp;<span>${it.offPoint?number * 100}</span></li>
                        <#elseif it.code == key && map[key] == 3>
                            <li>点位&nbsp;&nbsp;<span>${it.offPoint?number * 1000}</span></li>
                        </#if>
                    </#list>
                </ul>
            </div>
            <img src="${ctxStatic}/all/image/bianhao.png" class="kaicang_img">
            <div class="kaicang">
                <ul class="kaicang_list">
                    <li>平仓价格&nbsp;&nbsp;<span>${it.sellPoint}</span><i></i></li>
                    <li class="kctime">开仓时间 &nbsp;&nbsp;<span>${it.buyTime?datetime}</span></li>
                </ul>
            </div>
            <#--<img src="${ctxStatic}/all/image/bianhao.png" class="kaicang_img">-->
        </div>

    </div>

    </#list></#if>
</section>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script src="${ctxStatic}/js/jquery.cookie.js" type="text/javascript"></script>
<script type="text/javascript"  src="${ctxStatic}/js/md5.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/valid.js"></script>
<script type="text/javascript" src="${ctxStatic}/asserts/layer_mobile/layer.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/tools.js"></script>
<script type="text/javascript">
    $(function(){
        <#if date??>$("#inputDate").val('${date}')</#if>
        change();
    })
    function show(){
        var mydate = new Date();
        var str = "" + mydate.getFullYear() + "-";
        str += (mydate.getMonth()+1) + "-";
        str += mydate.getDate();
        return str;
    }

    function selectHistory() {
        var stringDate = $("#inputDate").val();
        if(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/.test(stringDate)){
            window.location.href = ctxWap + '/trade/tradeHistory?selDate='+stringDate;
        }else{
            layer.msg("请输入正确的时间");
        }
    }
    function change() {
        <#if list??><#list  list as it>
            $('#first_'+'${it.id}').click(function(){
                if($('#second_'+'${it.id}').is(':hidden')){//如果当前隐藏
                    $('#second_'+'${it.id}').show();//那么就显示div
                }else{//否则
                    $('#second_'+'${it.id}').hide();//就隐藏div
                }
            })
        </#list></#if>
    }
</script>
</body>
</html>