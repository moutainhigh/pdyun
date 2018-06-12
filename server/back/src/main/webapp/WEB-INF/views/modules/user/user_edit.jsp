<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-10-17
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
            <input type="hidden" name="id" value="${model.id}">
            <div class="form-group row">
                <div class="col-md-2">
                    <label>中文姓名</label>
                    <input type="text" class="form-control span1" name="chnName" placeholder="中文姓名:20字以内" maxlength="100" required value="${model.chnName}">
                </div>
                <div class="col-md-2">
                    <label>手机号码</label>
                    <input type="text" class="form-control span1" name="mobile" placeholder="手机号码：11位" maxlength="100" required value="${model.mobile}">
                </div>
                <div class="col-md-2">
                    <label>是否启用</label>
                    <select class="form-control span1" name="status" required value="${model.status}">
                        <option selected="selected" value="0">是</option>
                        <option value="1">否</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <label>能否认购</label>
                    <select class="form-control span1" name="subFlag" required value="${model.subFlag}">
                        <option <c:if test="${model.subFlag=='1'}">selected="selected"</c:if> value="1">能</option>
                        <option <c:if test="${model.subFlag!='1'}">selected="selected"</c:if> value="2">否</option>
                    </select>
                </div>
            </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/user/update')">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>