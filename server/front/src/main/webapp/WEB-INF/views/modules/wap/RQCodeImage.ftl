<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
        <#include "/wap/header.ftl" >
        <link href="${ctxStatic}/all/css/base.css" rel="stylesheet" type="text/css"/>
        <link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>
        <link href="${ctxStatic}/all/css/denglu.css" rel="stylesheet" type="text/css"/>
        <link href="${ctxStatic}/all/css/code.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript"  src="${ctxStatic}/all/js/rem.js"></script>
        <title>${title}</title>
    </head>
    <body>
        <section class="text-center wrap-page">
            <div style="margin-top: 35%">
                <img style="width:48%;height:100%;" id="images" src="${ctxWap}/all/image/ios_download.png">
            </div>

            <p  style="margin-top: 1rem;display: block;font-size: .45rem;">请识别二维码进行下载app</p>
            <span><font  size="3rem;color:#fff;" style="margin-top: .5rem;display: block;font-size: .45rem;">已有账户?
                <a href="${ctxWap}/login" style="color:#cf372d;">立即登录</a></font></span>
        </section>
        <#include "/wap/footer.ftl"/>
    </body>
</html>