<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <#include "/wap/header.ftl">
    <link href="${ctxStatic}/all/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/tixian.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/denglumimano.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/chjonzhicuowu.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"  src="${ctxStatic}/all/js/rem.js"></script>
    <title>${title}</title>
</head>
<body>
<section class="wrap-page">
    <div class="user_top" id="reg_form">
        <div class="ti_money" id="selectButton">
            <a class="left buy_chic" id="moneyOut">
                <i class="icon i_z"></i>余额提现
            </a>
            <#if user??>
                <#if user.returnMoney?string != '0'>
                    <a class="right buy_dd" id="profitOut"style="display: block;" onclick="commissionToMoney()">
                        <i class="icon i_z"></i>收益转余额
                    </a>
                </#if>
            </#if>

            <#--<a class="left buy_sy" id="shouyiOut"style="display: block;">-->
                <#--<i class="icon i_z"></i>收益转余额-->
            <#--</a>-->
        </div>
        <div class="blance">可提现余额 : <span style="color:#ff0000;">${user.money}</span><#if user.returnMoney?string != '0'>收益余额 : <span style="color:#ff0000;">${user.returnMoney}</span></#if></div>
        <div class="ti_money_text">出金如遇银行系统本身原因，会短暂延迟。</div>
        <div class="w-item clearfix">
            <input type="text" placeholder="请输入金额" id="money" name="money" maxlength="11" data-validtype="require"><span style="color: red">¥</span>
        </div>
        <div class="w-item clearfix">
            <input type="password" placeholder="输入密码" id="password" name="password" data-validtype="require">
        </div>
        <button class="login" type="submit" onclick="moneyOut()">申请提现</button>
        <div class="chognzhi_content">
            <p> 注 : 每日单笔提现最低100元</p>
            <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每日单笔提现手续费3元</p>
            <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申请提现时间:9 : 30 - 16 : 30</p>
            <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;提现到帐时间为24小时，请注意查收！</p>
        </div>
    </div>
</section>
<!--余额不足弹出框-->
<div class="popBox" id="moneyErrorBox" style="display: none">
    <div class="popCont">
        <div class="pop_bd f36">
            <div class="tishi">
                <div class="tishi_text left"><i class="img"></i>提示</div>
                <div class="close" onclick="hideErrorBox()"><img src="${ctxStatic}/all/image/q_03.png"></div>
            </div>
            <p class="xiadan_num xiadan_no">出金金额不可大于出余额！</p>
            <button class="sure right" onclick="hideErrorBox()">确认</button>

        </div>
    </div>
</div>
<!--密码错误弹出框-->
<div class="popBox" id="passErrorBox" style="display: none;">
    <div class="popCont">
        <div class="pop_bd f36" style="height:4.3rem;">
            <div class="tishi">
                <div class="tishi_text left"><i class="img"></i>提示</div>
                <div class="close" onclick="hidePassErrorBox()"><img src="${ctxStatic}/all/image/q_03.png"></div>
            </div>
            <p class="xiadan_num xiadan_cuo">密码输入误，请重试。</p>
            <button class="chognshi " style="margin-left: 3rem; font-size:0.3rem;" onclick="hidePassErrorBox()">重试</button>
            <a onclick="window.location.href = '${ctxWap}/user/firstPartResetTradePassWordWhenMoneyOut'" class="forget right" style="font-size:0.3rem;">忘记密码</a>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript"  src="${ctxStatic}/js/md5.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/asserts/layer_mobile/layer.src.js"></script>
<script type="application/javascript" charset="UTF-8" src="${ctxStatic}/asserts/iscroll-4/iscroll.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/tools.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/valid.js"></script>
<!--切换TAB -->
<script type="application/javascript" charset="utf-8">
    $(function () {
        hideErrorBox();
        hidePassErrorBox();
        $("#moneyOut").css("background","#FF0000").css("color","#FFF").data('selected', true);
        $("#profitOut").click(function(){
            $("#profitOut").css("background","#FF0000").css("color","#fff").data('selected', true);
            $("#moneyOut").css("background","").css("color","#fff").data('selected', false);
            $("#shouyiOut").css("background","").css("color","#fff").data('selected', false);
        })
        $("#moneyOut").click(function(){
            $("#moneyOut").css("background","#FF0000").css("color","#FFF").data('selected', true);
            $("#profitOut").css("background","").css("color","#fff").data('selected', false);
            $("#shouyiOut").css("background","").css("color","#fff").data('selected', false);
        })
        $("#shouyiOut").click(function(){
            $("#shouyiOut").css("background","#FF0000").css("color","#FFF").data('selected', true);
            $("#profitOut").css("background","").css("color","#fff").data('selected', false);
            $("#moneyOut").css("background","").css("color","#fff").data('selected', false);
        })
    })

    function moneyOut() {
        var valid = $('#reg_form').valid();
        if(!valid){
            return;
        }
        var obj = $('#reg_form').obj();
        obj.password = md5(obj.password);
        obj.type = $('#moneyOut').data('selected') ? 0 : 1;
        $.ajax({
            url: ctx + '/money/out',
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify(obj),
            useDefaultError: false,
            success: function(data){
                $('#money,#password').val('');
                layer.msg("提现成功", function () {
                    window.location.href = ctxWap + '/user?='+Math.random();
                });
            },
            error:function (XMLHttpRequest, textStatus, errorThrown, entity) {
                if(entity.error == '余额不足'){
                    showErrorBox();
                }else if('请先绑定银行卡' == entity.error){
                    layer.msg(entity.error);
                }else if('最低提现100元' == entity.error){
                    layer.msg(entity.error);
                }else if('超出提现申请时间' == entity.error){
                    layer.msg(entity.error);
                }else{
                    if(entity.error == '密码不正确'){
                        showPassErrorBox();
                    }else if(entity.error == '每日提现金额达到上限'){
                        layer.msg(entity.error);
                    }else if(entity.error == '每日提现次数达到上限'){
                        layer.msg(entity.error);
                    }
                }
            }
        });
    }
    function hideErrorBox() {
        $("#moneyErrorBox").hide();
    }
    function showErrorBox() {
        $("#moneyErrorBox").show();
    }
    function hidePassErrorBox() {
        $("#passErrorBox").hide();
    }
    function showPassErrorBox(){
        $("#passErrorBox").show();
    }

    function commissionToMoney(){
        var commission= parseFloat( $("#returnMoney").text(),10);
        if(commission<=0){
            $("#commissionErrorBox").show();
            return ;
        }
        $.ajax({
            url: ctx + '/users/commissionToMoney',
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            success: function (data) {
                layer.msg('转换成功！', lod());
            }
        });
    }

    function lod(){
        console.log(ctxWap + '/out');
        setTimeout("window.location.href = ctxWap + '/money/out'", 2000);
    }
</script>
</body>
</html>