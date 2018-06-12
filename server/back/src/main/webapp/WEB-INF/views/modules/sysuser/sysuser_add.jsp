<%--
  Created by IntelliJ IDEA.
  User: zhangbowen
  Date: 2015/8/7
  Time: 8:34
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
                    <label>用户名</label>
                    <input type="text" class="form-control span1" name="loginName" placeholder="用户名:100字以内"  maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>密码</label>
                    <input type="password" class="form-control span1" name="password" placeholder="密码:100字以内" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>工号</label>
                    <input type="text" class="form-control span1" name="no" placeholder="工号:100字以内" required maxlength="100">
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>姓名</label>
                    <input type="text" class="form-control span1" name="name" placeholder="姓名：100字以内" maxlength="199"
                           required>
                </div>
                <div class="col-md-2">
                    <label>邮箱</label>
                    <input type="email" class="form-control span1" name="email" placeholder="邮箱：200字以内" maxlength="200" required>
                </div>
                <div class="col-md-2">
                    <label>电话</label>
                    <input type="number" class="form-control span1" name="phone" placeholder="电话:200字以内" maxlength="200" required>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>手机</label>
                    <input type="number" class="form-control span1" name="mobile" placeholder="手机：200字以内" maxlength="200"
                           required>
                </div>
                <div class="col-md-2">
                    <label>备注</label>
                    <input type="text" style="width: 520px" class="form-control span1" name="remarks" placeholder="备注：200字以内" maxlength="200">
                </div>
            </div>
            <div class="form-group">
                <label>选择用户角色</label>
                <c:forEach items="${roleList}" var="item">
                    <label class="checkbox-inline">
                        <input type="checkbox" name="roleAttr" value="${item.id}"> ${item.name}
                    </label>
                </c:forEach>
            </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/sysuser/save')">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
