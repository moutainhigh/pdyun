<#if footer??>
<!--footer-->
<nav class="bar bar-tab index" id="bar-tab">
    <a href="javascript:<#if footer == "market">void(0);</#if><#if footer != "index">toHome();</#if>" class="tab-item jy">
        <span class="icon icontu icon-index<#if footer == "index">-active</#if>"></span>
        <span class="tab-label index<#if footer == "index">-active</#if>">首页</span>
    </a>
    <a href="javascript:<#if footer == "market">void(0);</#if><#if footer != "market">toMarketPage();</#if>" class="tab-item jy">
        <span class="icon icontu icon-sc<#if footer == "market">-active</#if>"></span>
        <span class="tab-label sc<#if footer == "market">-active</#if>">交易</span>
    </a>
    <#--<a href="javascript:<#if footer == "trade">void(0);</#if><#if footer != "trade">toTradePage();</#if>" class="tab-item jy">-->
        <#--<span class="icon icontu icon-jy<#if footer == "trade">-active</#if>"></span>-->
        <#--<span class="tab-label jy<#if footer == "trade">-active</#if>">点位交易</span>-->
    <#--</a>-->
    <a href="javascript:<#if footer == "hold">void(0);</#if><#if footer != "hold">toHoldTradePage();</#if>" class="tab-item cc">
        <span class="icon icontu icon-cc<#if footer == "hold">-active</#if>"></span>
        <span class="tab-label cc<#if footer == "hold">-active</#if>">持仓</span>
    </a>
    <#--<a href="javascript:<#if footer == "corps">void(0);</#if><#if footer != "corps">toCorpsPage();</#if>" class="tab-item jt">-->
        <#--<span class="icon icontu icon-jt<#if footer == "corps">-active</#if>"></span>-->
        <#--<span class="tab-label jt<#if footer == "corps">-active</#if>">军团</span>-->
    <#--</a>-->
    <#--<a href="javascript:<#if footer == "center">void(0);</#if><#if footer != "center">toCenterPage();</#if>" class="tab-item jt">-->
        <#--<span class="icon icontu icon-center<#if footer == "center">-active</#if>"></span>-->
        <#--<span class="tab-label center<#if footer == "center">-active</#if>">公告</span>-->
    <#--</a>-->
    <a href="javascript:<#if footer == "user">void(0);</#if><#if footer != "user">toUserPage();</#if>" id="me label" class="tab-item">
        <span class="icon icontu icon-me<#if footer == "user">-active</#if>"></span>
        <span class="tab-label me<#if footer == "user">-active</#if>">我的</span>
    </a>
</nav>
</#if>
<script type="application/javascript" charset="utf-8">
    function toHome() {
        /*window.location.replace("${ctxWap}/index/broadcast/list?r="+Math.random());*/
        window.location.href = "${ctxWap}/index/broadcast/list?r="+Math.random();
    }
    
    function toHomePage(){
        window.location.replace("${ctxWap}/home?r="+Math.random());
    }
    function toTradePage(){
        window.location.replace("${ctxWap}/trade?r="+Math.random());
    }
    function toHoldTradePage(){
        window.location.replace("${ctxWap}/trade/hold?r="+Math.random());
    }
    function toUserPage(){
        window.location.replace("${ctxWap}/user?r="+Math.random());
    }
    function toCorpsPage() {
        window.location.replace("${ctxWap}/corps?r="+Math.random());
    }
    function toMarketPage(){
        window.location.replace("${ctxWap}/market?r="+Math.random());
    }
    /*function toCenterPage() {
        window.location.replace("${ctxWap}/index/center?r="+Math.random());
    }*/
</script>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript"  src="${ctxStatic}/js/md5.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/asserts/layer_mobile/layer.src.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/tools.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/valid.js"></script>
