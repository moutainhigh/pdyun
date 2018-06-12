<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <#include "/wap/header.ftl" >
    <#assign footer = "user"/>
    <link href="${ctxStatic}/all/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/gerenzhognxin.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"  src="${ctxStatic}/all/js/rem.js"></script>
    <title>${title}</title>
</head>
<body id="fixation">
<section class="wrap-page">
    <div class="user_top">
        <div class="user_top_a left">
            <div class="vm infor text-center">
                <div class="photo ">
                    <img src="<#if user.userHeader??>${user.userHeader}<#else>${ctxStatic}/all/image/logo_header.png</#if>" alt="">
                </div>
            </div>
            <div class="ac_name ac_a left">
                <p>姓名：<span>${user.chnName}</span></p>
                <span>交易账号：${user.mobile}</span>
            </div>
        </div>
        <div class="user_top_line left"></div>
        <div class="erwei_tu left" id="tanchuang" onclick="weixinCode()">
            <img src="${ctxStatic}/all/image/eeee_03.png" class="img">
            <p>二维码邀请</p>
        </div>
        <div class="user_top_line left"></div>
        <div class="mima_tu right" onclick="window.location.href = '${ctxWap}/user/modifyTradePwd'">
            <img src="${ctxStatic}/all/image/w_03.png" class="img">
            <p>改密码</p>
        </div>
    </div>
    <form class="form">
        <div class="w-item clearfix">
            <div class="user_img_lf left">
                <img src="${ctxStatic}/all/image/qwwqwq_15.png" class="left user_img clearfix">
                <div class="user_money left clearfix">
                    <p class="red" style="color:#f75a60">&yen;<span>${user.money}</span></p>
                    <span>账户余额</span>
                </div>
            </div>
            <div class="tichong right">
                <button type="button" class="tixian" onclick="window.location.href = '${ctxWap}/money/out'">提现</button>
                <button type="button" class="chognzhi" onclick="window.location.href = '${ctxWap}/pay/recharge'">充值</button>
            </div>
        </div>
        <div class="w-item clearfix" onclick="window.location.href = '${ctxWap}/account'">
            <div class="user_img_lf left">
                <img src="${ctxStatic}/all/image/sasas_18.png" class="left user_info clearfix">
                <div class="user_info_text left clearfix">
                    <span>账户信息</span>
                </div>
            </div>
            <div class="jiantou right"><img src="${ctxStatic}/all/image/right.png"></div>
        </div>
        <div class="qianyue clearfix">
            <div class="bank_qianyue" onclick="window.location.href = '${ctxStatic}/user/bankCard'">
                <div class="user_img_lf user_img_tu left">
                    <i class="icon left"></i>
                    <div class="user_info_text left clearfix">
                        <span>绑定银行卡</span>
                    </div>
                </div>
                <div class="jiantou right"><img src="${ctxStatic}/all/image/right.png"></div>
            </div>
            <img src="${ctxStatic}/all/image/bank_line.png" class="bank_line">
            <div class="bank_qianyue" onclick="window.location.href = '${ctxWap}/trade/tradeHistory'">
                <div class="user_img_dd user_img_tu left">
                    <i class="icon diangd left"></i>
                    <div class="user_info_text left clearfix">
                        <span>历史订单</span>
                    </div>
                </div>
                <div class="jiantou right"><img src="${ctxStatic}/all/image/right.png"></div>
            </div>
            <img src="${ctxStatic}/all/image/bank_line.png" class="bank_line">
            <div class="bank_qianyue" onclick="window.location.href='${ctxWap}/money/payRecord'">
                <div class="user_img_quan user_img_tu left">
                    <i class="icon left"></i>
                    <div class="user_info_text left clearfix">
                        <span>充提记录</span>
                    </div>
                </div>
                <div class="jiantou right"><img src="${ctxStatic}/all/image/right.png"></div>
            </div>
        </div>
        <div class="qianyue clearfix">
            <#--<div class="bank_qianyue" onclick="window.location.href = '${ctxWap}/corps'">-->
                <#--<div class="user_img_junt user_img_tu left">-->
                    <#--<i class="icon left"></i>-->
                    <#--<div class="user_info_text left clearfix">-->
                        <#--<span>我的军团</span>-->
                    <#--</div>-->
                <#--</div>-->
                <#--<div class="jiantou right"><img src="${ctxStatic}/all/image/right.png"></div>-->
            <#--</div>-->
            <img src="${ctxStatic}/all/image/bank_line.png" class="bank_line">
            <div class="bank_qianyue" onclick="window.location.href = '${ctxWap}/user/userMessageList'">
                <div class="user_img_xiaoxi user_img_tu left">
                    <i class="icon left"></i>
                    <div class="user_info_text left clearfix">
                        <span>我的消息</span>
                    </div>
                </div>
                <div class="jiantou right"><img src="${ctxStatic}/all/image/right.png"></div>
            </div>
            <img src="${ctxStatic}/all/image/bank_line.png" class="bank_line">
            <div class="bank_qianyue" onclick="weixinCode()">
                <div class="user_img_friend user_img_tu left">
                    <i class="icon left"></i>
                    <div class="user_info_text left clearfix">
                        <span>邀请好友</span>
                    </div>
                </div>
                <div class="jiantou right"><img src="${ctxStatic}/all/image/right.png"></div>
            </div>
        </div>
        <div class="w-item clearfix exit" onclick="window.location.href = '${ctxWap}/logout'">
            <div class="user_img_lf left">
                <img src="${ctxStatic}/all/image/asasasas_37.png" class="left user_info clearfix">
                <div class="user_info_text left clearfix">
                    <span>安全退出</span>
                </div>
            </div>
        </div>
    </form>
</section>
<!--弹出框-->
<div class="pop_box" style="display: none">
    <!--提示：请填写完整信息-->
    <div class="pop_box_wanzhengxinxi" style="height: 17rem;">
        <div style="text-align: left"><span style="margin-left: 1rem"><img src="${ctxStatic}/img/images2/logo2.png" style="margin-right: 0.5rem">提示</span><img src="${ctxStatic}/img/images2/close.png"></div>
        <div style="border-bottom: none;margin-top: 1.5rem;width: 80%;margin: 1rem auto;line-height: 2.5rem;">
            您还不是经纪人，请联系代理机构获取经纪人资格。
            <i>确认</i>
        </div>
    </div>
</div>
<#include "/wap/footer.ftl"/>
<script type="application/javascript">
    // 二维码
    function weixinCode() {
        $("<div id='bg' onclick='hide(this)' style='position:relative;width:100%;height:100%;text-align:center;position:fixed;top:0;left:0;background-color:rgba(0,0,0,0.7);z-index:999;overflow: auto;'><div style='padding-top:0.8rem;margin:0 auto;position: relative'><img src='${ctxWap}/user/shareImg' style='position:relative; top: 50%; margin-top:4.5rem; width:9rem;'></div>"
                +'<div style="width: 100%; height: 3rem; text-align: center; padding-top: .3rem; font-size: .35rem; line-height: .7rem; color: #cccccc;"></div>'
                +"</div>").appendTo($("body"));
    }
    function hide(ts){
        $(ts).hide();
    }
</script>
</body>
</html>