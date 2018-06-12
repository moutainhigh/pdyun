<%--
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
                <div class="col-md-2">
                    <label>管理员账号</label>
                    <input type="text" class="form-control span1" name="mLoginname" placeholder="管理员账号:20字以内"  maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>中文姓名</label>
                    <input type="text" class="form-control span1" name="mCnname" placeholder="中文姓名:20字以内" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>是否启用</label>
                    <select class="form-control span1" name="mIsuse" required>
                        <option selected="selected" value="0">是</option>
                        <option value="1">否</option>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>是否内置</label>
                    <select class="form-control span1" name="mIsposwerful" required>
                        <option value="0">是</option>
                        <option selected="selected" value="1">否</option>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-10">
                    <label>选择角色：</label>
                    <label class="checkbox-inline">
                        <input type="checkbox" name="roleAttr" value="">
                    </label>
                </div>
            </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/Ml3AgentUser/save')">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>