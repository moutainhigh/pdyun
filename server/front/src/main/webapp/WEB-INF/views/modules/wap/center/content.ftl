<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
<#include "/wap/header.ftl" >
<#assign footer = "corps"/>
    <link href="${ctxStatic}/all/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/message.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"  src="${ctxStatic}/js/rem.js"></script>
    <title>${title}</title>
</head>
<body>
<section class="wrap-page">
    <#--<div class="top_img"><img class="img" src="${ctxStatic}/all/image/denglulog.png"></div>-->
<#--    <form class="form">
        <div class="w-item clearfix">
            <div class="user_img_lf left">
                <img src="${ctxStatic}/all/image/sa5445_31.png" class="left user_info clearfix">
                <div class="user_info_text left clearfix">
                    <span>${data.content}</span>
                </div>
            </div>
        </div>
    </form>-->
    <div style="font-size: 0.3rem">
        <span>${data.content}</span>
    </div>
</section>
<#include "/wap/footer.ftl">
</body>
</html>