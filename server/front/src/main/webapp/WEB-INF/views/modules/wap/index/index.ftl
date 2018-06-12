<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta http-equiv ="proma" content = "no-cache"/>
    <meta http-equiv="cache-control" content="no cache" />
    <meta http-equiv="expires" content="0" />
    <#include "/wap/header.ftl" >
    <#assign footer = "index"/>
    <link href="${ctxStatic}/all/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/zixunl.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"  src="${ctxStatic}/all/js/rem.js"></script>
    <title>${title}</title>
    <style type="text/css">
        body{
            -webkit-overflow-scrolling:touch;
        }
        .win_scroll{
            margin-left: 1rem;
            height: 1.1rem;
            overflow: hidden;
            margin-top: .1rem;
            font-size: .38rem;
        }
    </style>
</head>
<body>
<section class="wrap-page" style=" bottom: 1.7rem;">
    <div class="intop_banner"><img style="margin-top: .1rem;" src="${ctxStatic}/all/image/home_logo.jpg"></div>
    <div class="announcement">
        <div class="affiche"><i></i>公告</div>
        <div class="affiche_txt">
            <img class="img left" src="${ctxStatic}/all/image/win_profit.jpg" style="margin: 0rem; width: 2rem; height: 1.61rem;">
            <div class="affiche_t news_li left">
                <#--<p>释后发博致歉:对不起让大家担心了</p>-->
                <#--<p>释后发博致歉:对不起让大家担心了</p>-->
                <#--<p>释后发博致歉:对不起让大家担心了</p>-->
                <ul class="win_scroll" >
                    <li style="line-height: .4rem;">加载中...</li>
                </ul>
            </div>
            <div class="swap"></div>
        </div>
    </div>
    <div class="information">
        <div class="title"><i></i>资讯</div>
        <ul class="informationTxt">
            <#--<li class="affiche_txt mation">-->
                <#--<img class="img left" src="${ctxStatic}/all/image/timg01.jpg">-->
                <#--<div class="left message">-->
                    <#--<h2>标题</h2>-->
                    <#--<p>标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题</p>-->
                <#--</div>-->
            <#--</li>-->
            <#--<li class="affiche_txt mation">-->
                <#--<img class="img left" src="${ctxStatic}/all/image/timg (1).jpg">-->
                <#--<div class="left message">-->
                    <#--<h2>标题</h2>-->
                    <#--<p>标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题</p>-->
                <#--</div>-->
            <#--</li>-->
            <#--<li class="affiche_txt mation">-->
                <#--<img class="img left" src="${ctxStatic}/all/image/timg (2).jpg">-->
                <#--<div class="left message">-->
                    <#--<h2>标题</h2>-->
                    <#--<p>标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题</p>-->
                <#--</div>-->
            <#--</li>-->
            <#--<li class="affiche_txt mation ">-->
                <#--<img class="img left" src="${ctxStatic}/all/image/timg.jpg">-->
                <#--<div class="left message">-->
                    <#--<h2>标题</h2>-->
                    <#--<p>标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题</p>-->
                <#--</div>-->
            <#--</li>-->
            <#--<li class="affiche_txt mation">-->
                <#--<img class="img left" src="${ctxStatic}/all/image/timg (3).jpg">-->
                <#--<div class="left message">-->
                    <#--<h2>标题</h2>-->
                    <#--<p>标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题</p>-->
                <#--</div>-->
            <#--</li>-->
            <#--<li class="affiche_txt mation">-->
                <#--<img class="img left" src="${ctxStatic}/all/image/timg01.jpg">-->
                <#--<div class="left message">-->
                    <#--<h2>标题</h2>-->
                    <#--<p>标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题</p>-->
                <#--</div>-->
            <#--</li>-->
        </ul>

    </div>
    <div class="grading_data" id="click_more">
        <span>点击加载更多</span>
    <#--<#if users.start + users.data.size() == users.total>没有更多数据<#else><span onclick="loadMore();">点击加载更多</span></#if>-->
    </div>
</section>
<#include "/wap/footer.ftl"/>
</body>
<script type="text/javascript" src="${ctxStatic}/all/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/modules/index/topscroll.js"></script>
<#--<script type="text/javascript" src="${ctxStatic}/js/modules/index/scroll.js"></script>-->
<script type="text/javascript">
    $(function () {

    })

</script>
<script type="text/javascript">

</script>
</html>