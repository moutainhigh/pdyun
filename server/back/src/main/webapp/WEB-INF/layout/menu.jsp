<%--
  Created by IntelliJ IDEA.
  User: zhangbowen
  Date: 2015/7/11
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/include/taglib.jsp" %>
<%--左侧menu--%>
<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse">
        <ul class="nav" id="side-menu">
            <c:forEach items="${menuList}" var="item" varStatus="index">
                <c:if test="${item.isShow == '1' && item.parentId=='0'}">
                    <li
                    <c:if test="${index.index==0}">
                        class="active"
                    </c:if>
                            >
                        <a href="#"><i class="fa ${item.icon} fa-fw"></i>${item.name}<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <c:forEach items="${menuList}" var="children" varStatus="childIndex">
                                <c:if test="${children.parentId==item.id}">
                                    <li>
                                        <a href="javascript:void(0)" onclick="openPage(this,'${ctx}${children.href}')">${children.name}</a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</div>
<%--左侧menu结束--%>
