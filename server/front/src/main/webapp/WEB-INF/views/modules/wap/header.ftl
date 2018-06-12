<!-- 定义变量 -->
<#assign cp = "${request.contextPath}"/>
<#assign ctx = "${request.contextPath}/v1"/>
<#assign ctxStatic = "${request.contextPath}/wap"/>
<#assign ctxWap = "${request.contextPath}/wap"/>
<#assign title = "中赢國際"/>
<#if request.serverPort == 80>
    <#assign domain = "${request.scheme}://${request.serverName}"/>
</#if>
<#if request.serverPort != 80>
    <#assign domain = "${request.scheme}://${request.serverName}:${request.serverPort?c}"/>
</#if>
<link href="${ctxStatic}/asserts/layer_mobile/need/layer.css" rel="stylesheet" type="text/css"/>

<!-- js中定义变量 -->
<script type="text/javascript">var cp = '${cp}', ctx = '${ctx}', ctxWap = '${ctxWap}', ctxStatic='${ctxStatic}', domain = '${domain}';</script>