<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <#include "/wap/header.ftl" >
    <#assign footer = "center"/>
    <link href="${ctxStatic}/all/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/message.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"  src="${ctxStatic}/js/rem.js"></script>
    <title>${title}</title>
</head>
<body>
<section class="wrap-page">
    <div class="top_img"><img class="img" src="${ctxStatic}/all/image/denglulog.png"></div>
    <form class="form">
        <#list data as it>
            <#--<div class="w-item clearfix" onclick="<#if it_index != 0  && it_index != 1 >window.location.href = ctxWap + '/index/center/content/'+'${it.id}'</#if>">-->
            <div class="w-item clearfix" onclick="window.location.href = ctxWap + '/index/broadcastDetail/'+'${it.id}'">
            <div class="user_img_lf left">
                    <img src="${ctxStatic}/all/image/sa5445_31.png" class="left user_info clearfix">
                    <div class="user_info_text left clearfix">
                        <span>${it.title}</span>
                    </div>
                </div>
            <#--<#if it_index != 0  && it_index != 1 >
                <div class="jiantou right"><img src="${ctxStatic}/all/image/right.png"></div>
            </#if>-->
            </div>
        </#list>
    </form>
</section>
<#include "/wap/footer.ftl">
</body>
</html>