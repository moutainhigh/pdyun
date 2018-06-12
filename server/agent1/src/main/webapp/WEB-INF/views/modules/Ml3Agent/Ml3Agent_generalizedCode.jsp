<%@ page import="java.util.HashMap" %>
<%@ page import="com.google.zxing.EncodeHintType" %>
<%@ page import="com.google.zxing.qrcode.decoder.ErrorCorrectionLevel" %>
<%@ page import="com.google.zxing.common.BitMatrix" %>
<%@ page import="com.google.zxing.MultiFormatWriter" %>
<%@ page import="com.google.zxing.BarcodeFormat" %>
<%@ page import="java.nio.file.Path"%>
<%@ page import="com.google.zxing.client.j2se.MatrixToImageWriter"%>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.OutputStream" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/18
  Time: 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <script type="text/javascript" src="${ctxStatic}/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/js/jquery.qrcode.min.js"></script>
</head>
<body class="easyui-layout">
<div region="north" style="padding:5px;" border="false">
    <!-- 查询 -->

</div>
<div region="center" style="padding:5px;" border="false">
    <label class="input-inline">
        <span style="font-size:20px;">代理的推广二维码：</span>
    </label><br/><br/>
    <div style="width: 100%;height: 100%;padding-left:11%;" id="qrcode"></div>
    <script>
        jQuery('#qrcode').qrcode("${url}");
    </script>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>

</body>
</html>
