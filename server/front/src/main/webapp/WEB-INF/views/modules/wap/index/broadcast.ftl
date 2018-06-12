<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta http-equiv ="proma" content = "no-cache"/>
    <meta http-equiv="cache-control" content="no cache" />
    <meta http-equiv="expires" content="0" />
    <#include "/wap/header.ftl" >
    <link href="${ctxStatic}/all/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/guanbbo.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${ctxStatic}/all/js/jquery.min.js"></script>
    <script type="text/javascript"  src="${ctxStatic}/all/js/rem.js"></script>
    <title>${title}</title>
    <style type="text/css">
        img{
            width: 100%!important;
        }
    </style>
</head>
<body>
<section class="wrap-page">
    <div class="user_top">广播公告</div>
    <div class="guangbo_contain">
        <p>${model.content!''}</p>
    </div>
    <script type="text/javascript">
        window.onload=function()//用window的onload事件，窗体加载完毕的时候
        {


        }
        
        $(function () {
            var div=document.getElementsByClassName('guangbo_contain');
            div[0].style.height=document.body.offsetHeight-(4.2*16)+"px";
            div[0].style.width=94+"%";
        })

    </script>
    <script type="text/javascript">

    </script>
</section>

</body>
</html>