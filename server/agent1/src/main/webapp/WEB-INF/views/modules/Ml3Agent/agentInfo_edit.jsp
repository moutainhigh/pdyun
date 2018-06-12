<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-11-4
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
            <input type="hidden" name="id" value="${model.id}" required>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>账号</label>
                    <input type="text" class="form-control span1" value="${model.account}" name="account" placeholder="请输入新账号"  maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>用户名</label>
                    <input type="text" class="form-control span1" value="${model.realName}" name="realName" placeholder="请输入新用户名"  maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>手机号</label>
                    <input type="text" class="form-control span1" value="${model.mobile}" name="mobile" placeholder="请输入新手机号"  maxlength="100" required>
                </div>
            </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/Ml3Agent/updMl3AgentUserInfo')" style="float: right">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>