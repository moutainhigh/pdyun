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
<body style="background: #ffffff">
<div class="panel panel-default">
    <div class="panel-body" style="padding:10px 10px 10px 20px;border: none">
        <form>
            <div class="form-group" style="width: 200px;">
                    <label>角色名称</label>
                    <input type="text" class="form-control span1" name="name" placeholder="角色名称长度:100字内" maxlength="100"
                           required>
            </div>
            <div class="form-group" style="width: 200px;">
                    <label>英文名称</label>
                    <input type="text" class="form-control span1" name="enname" placeholder="英文名称长度:255字内" maxlength="255" required>
            </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/sysrole/save')">提交</button>
        </form>
    </div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
