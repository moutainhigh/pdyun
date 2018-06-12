<%@ page import="java.util.List" %>
<%@ page import="com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean" %><%--
Created by CodeGenerator.
User: Administrator
Date: 2016-11-17
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>


<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body style="background: #ffffff;border-top:3px solid #3c8dbc;">
<div class="panel panel-default">
    <div class="panel-body" style="padding:10px 10px 10px 20px;border: none">
        <form>
            <div class="form-group row">
                <div class="col-md-3">
                    <label>所属市场管理部</label>
                    <select id="se"  class="form-control" name="centerId">
                        <option>请选择</option>
                        <c:forEach items="${ml3OperateCenter}" var="operatrCenter">
                            <option id="${operatrCenter.id}" value="${operatrCenter.id}">${operatrCenter.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label>账号</label>
                    <input type="text" class="form-control span1" name="account" placeholder="账号:20字以内" maxlength="100" required>
                </div>
                <div class="col-md-3">
                    <label>手机号</label>
                    <input type="text" class="form-control span1" name="mobile" placeholder="手机号:20字以内" maxlength="100" required>
                </div>
                <div class="col-md-3">
                    <label>密码</label>
                    <input type="text" class="form-control span1" name="safePassword" placeholder="密码:20字以内" maxlength="100" required>
                </div>
            </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/Ml3OperateCenter/saveOperateCenterUser')">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>