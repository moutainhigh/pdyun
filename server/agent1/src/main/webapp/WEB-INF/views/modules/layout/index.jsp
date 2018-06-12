<%--
  Created by IntelliJ IDEA.
  User: zhangbowen
  Date: 2015/7/11
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/include/taglib.jsp" %>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<!DOCTYPE html>
<html>
<title>合伙人营销管理系统</title>
<head></head>
<body>

<div id="wrapper">
    <%--导航栏，包括顶部导航和左侧菜单--%>
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <%@include file="header.jsp" %>
        <jsp:include page="/menulist"></jsp:include>
    </nav>
    <%--导航栏结束--%>
    <%--内容--%>
    <div id="page-wrapper">
    </div>
    <%--内容结束--%>
</div>
<div id="mm" class="easyui-menu" style="width: 150px;">
    <div name="tabupdate">刷新</div>
    <div class="menu-sep"></div>
    <div name="close">关闭</div>
    <div name="closeall">全部关闭</div>
    <div name="closeother">除此之外全部关闭</div>
    <div class="menu-sep"></div>
    <div name="closeright">当前页右侧全部关闭</div>
    <div name="closeleft">当前页左侧全部关闭</div>
</div>
<%@include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var bodyHeight;
    function openPage(source,url) {
        var iframe = $('<iframe id="content_container" scrolling="no" src="' + url + '" frameborder="0" style="width: 100%; height:'+bodyHeight+'px;"></iframe>');
        $("#page-wrapper").html(iframe);
        $(".nav li a").removeClass("current");
        $(source).addClass("current");
    }

    $(function () {
        bodyHeight = $(window).height()-$(".navbar-static-top").height()-1;
        var ul = $("#side-menu li[class='active']").find("ul").children(0);
        ul.find("a").click();
    })
</script>
</body>

</html>
