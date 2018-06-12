<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
<#include "/wap/header.ftl">
<#assign footer = "corps"/>
    <title>${title}</title>
    <link rel="shortcut icon" href="${ctxStatic}/favicon.ico" type="image/x-icon" />
    <link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/cd.css" />
    <link rel="stylesheet" href="${ctxStatic}/css/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/base.css">
    <script type="text/javascript"  src="${ctxStatic}/js/rem.js"></script>
  <style>
    .top_div a:before{
      content: "";
      display: inline-block;
      width: 16px;
      height: 28px;
      background:url("${ctxStatic}/img/images2/css_sprites.png");
      background-position-y:-111px;
       background-position-x:0;
       line-height:6rem;
       -webkit-transform: translateY(7px);margin-right: 0.5rem;
    }
    .menus ul li:nth-child(4) a{
        color: #FF8400;
    }
    .legion:before{
        background: url("${ctxStatic}/img/images2/legion/dph.png") no-repeat;
    }
  </style>
  <link rel="stylesheet" href="${ctxStatic}/css/global.css">
</head>
<body style="width100%;height:100%;margin: 0 auto; overflow: hidden;position:relative;">
<div id="fixation">
<!--顶部开始-->
<#--<div class="top_div" style="max-width: 768px;" id="myteam_top">-->
        <#--<h1>我的军团</h1>-->
<#--</div>-->
<!--顶部结束-->
<!-- mian开始 -->
<div class="min" style="width: 100%;height: 100%; overflow-y:auto;position:absolute;">
	    <div class="team_login jutuan">
	       <div class="login_header">
           <h1><img src="<#if user.userHeader??>${user.userHeader}<#else>${ctxStatic}/img/images2/touxiang.png</#if>" alt=""></h1>
	       		<p>${user.chnName}</p>
	       </div>
	       <div class="login_money">
               <ul class="login_money_u1">
                   <li>累计的收益<i></i></li>
                   <li>未结算的收益<i></i></li>
                   <li>战队的人数<i></i></li>
               </ul>
               <ul class="login_money_u2">
                   <li>${user.returnMoneyTotal}<i></i></li>
                   <li>${user.returnMoney}<i></i></li>
                   <li>${corpsCount.bingCount?c}<i></i></li>
               </ul>
           </div>
	       <div class="login_rulebtn" id="viewrule"><a href="#">查看军团规则</a></div>
	       <div class="login_tab">

               <div id="teamouter">
                   <ul id="teamtab">
                       <li class="teamcurrent">军团分级</li>
                       <li>军团收益</li>
                   </ul>
                   <div id="teamcontent" class="juntuan_shouj"><!--包住4个DIV-->

                       <div style="display:block;background: #2c2f38;" id="level"><!--下面整体的DIV1-->
                           <ul class="teamcontent_header" >
                               <#--<li>炮兵团<i></i></li>-->
                               <li>骑兵团<i></i></li>
                               <li>步兵团 <i></i></li>
                              <#-- <li>应得收益<i></i></li>-->
                           </ul>
                           <ul class="teamcontent_header_num">
                               <#--<li>${corpsCount.paoBingCount?c}<i></i></li>-->
                               <li>${corpsCount.qiBingCount?c}<i></i></li>
                               <li>${corpsCount.buBingCount?c}<i></i></li>
                               <#--<li>${corpsCount.buBingCount?c}<i></i></li>-->
                           </ul>
                           <p><a href="${ctxWap}/corps/members">查看详情>> </a></p>
                       </div>
                       <div ><!--下面整体的DIV2-->
                           <ul class="teamcontent_header">
                               <li>军团<i></i></li>
                               <li>收益比例<i></i></li>
                               <li>交易总额<i></i></li>
                               <li>应得收益<i></i></li>
                           </ul>
<#--                           <ul class="teamcontent_header">
                               <li>炮兵团<i></i></li>
                               <li>${percent3.percentPaoBing*100}%<i></i></li>
                               <li>${corpsMoney.paoBingMoneyTotal}<i></i></li>
                               <li>${corpsMoney.paoBingMoney}<i></i></li>
                           </ul>-->
                           <ul class="teamcontent_header">
                               <li>骑兵团<i></i></li>
                               <li>${percent3.percentQiBing*100}%<i></i></li>
                               <li>${corpsMoney.qiBingMoneyTotal}<i></i></li>
                               <li>${corpsMoney.qiBingMoney}<i></i></li>
                           </ul>
                           <ul class="teamcontent_header">
                               <li>步兵团 <i></i></li>
                               <li>${percent3.percentBuBing*100}%<i></i></li>
                               <li>${corpsMoney.buBingMoneyTotal}<i></i></li>
                               <li>${corpsMoney.buBingMoney}<i></i></li>
                           </ul>

                           <p><a href="${ctxWap}/corps/profit" class="look_xq">查看详情>> </a></p>
                       </div>

                   </div>
               </div>
           </div>
    	</div>

</div><!--597 x 880-->
<!-- main结束 -->
<#include "/wap/footer.ftl">
<!--footer-->

<!--模态框-->
<div class="pop_box" style="display: none">
    <div id="rules" style="display: none;">
        <div class="tishi">
            <p class="tishi_text">军团规则</p>
            <div class="close" id="clickTip"><img src="${ctxStatic}/all/image/q_03.png"></div>
        </div>
        <p class="xieyi xiadan_no">
            恭喜你！你已成功加入${title}“军团决胜计划“，想要真正做到躺着赚钱，请仔细阅读以下规则：品道云商平台有权拒绝为您开通交易服务<br>
            <span>军团组建规则：<br></span>
            你的军团总共分为两大军团，分别是骑兵团、步兵团 。<br>
            骑兵团：通过你直接分享的链接，扫码关注品道云商平台的用户，属于你的骑兵团成员；<br>
            步兵团 ：通过你的骑兵团成员分享的链接，扫码关注中晟的用户，属于你的步兵团 成员。<br>
            <span>获取收益规则：<br></span>
            1、假如你邀请好友A关注${title}并注册交易，恭喜你！你已成功组建自己的军团，开始享受军队交易带来源源不断的收益，你将从A的每一笔交易额中获得0.3%的“骑兵团收益”；【你的骑兵团收益=A的每一笔交易金额*0.3%】<br>
            2、假如你邀请的好友A对他（她）的好友B发出新的邀请，B成功关注${title}并注册交易，你将从B的交易金额中获得0.1%的“ 步兵团收益”；【你的步兵团 收益=B的每一笔交易金额*0.1%】<br>
            注意：<br>
            1、你获取的收益仅限于骑兵团、步兵团 交易金额按比例产生，假如B继续向好友C发送邀请，则C的交易金额与你的收益无关；<br>
            2、军团收益次日结算，结算当日即可提现。<br>
            因此，你只需要不断邀请好友加入你的军团一起盈战，并教会你的军队像你一样邀请好友参与这个计划，即可让你和你的好友坐享连绵不绝的财富。想要躺着赚钱，就这么简单！独乐乐，不如众乐乐，赶紧向你的好友发送财富邀请吧！<br>
        </p>
        <div class="rules_btn">确定</div>
    </div>
</div>

</div>
<!--模态框JS-->
<script>
    $("#viewrule").click(function(){
        $(".pop_box").show();
        $("#rules").show();
    })
    $(".rules_btn").click(function(){
        $(".pop_box").hide();
        $("#rules").hide();
    })
    $("#ruleclose").click(function(){
        $(".pop_box").hide();
        $("#rules").hide();
    })
    $("#clickTip").click(function(){
        $(".pop_box").hide();
        $("#rules").hide();
    })
    $(function(){
        window.onload = function()
        {
            var $li = $('#teamtab li');
            var $ul = $('#teamcontent>div');

            $li.mouseover(function(){
                var $this = $(this);
                var $t = $this.index();
                $li.removeClass();
                $this.addClass('teamcurrent');
                $ul.css('display','none');
                $ul.eq($t).css('display','block');
            })
        }
    });
</script>

</body>
</html>