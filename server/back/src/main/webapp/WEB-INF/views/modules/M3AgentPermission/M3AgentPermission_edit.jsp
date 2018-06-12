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
<div class="panel panel-default" style="margin:5px;">
    <div class="panel-body" style="padding:10px 10px 10px 20px;border: none; overflow-x:hidden;">
        <form>
            <input type="hidden" name="id" value="${model.id}"/>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>名称</label>
                    <input type="text" class="form-control span1" name="name" placeholder="名称长度:100字内" value="${model.name}" maxlength="100"
                           required>
                </div>

                <div class="col-md-2">
                    <label>权限标识</label>
                    <input type="text" class="form-control span1" name="permission" placeholder="权限标识长度:200字内" value="${model.permission}" maxlength="200"
                    >
                </div>

                <div class="col-md-2">
                    <label>链接</label>
                    <input type="text" class="form-control span1" name="href" placeholder="链接" value="${model.href}" maxlength="1000">
                </div>

            </div>
            <div class="form-group row">

                <div class="col-md-2">
                    <label>icon</label>
                    <input type="text" class="form-control span1" name="icon" placeholder="图标编码:100字以内" value="${model.icon}" maxlength="100">
                </div>

                <div class="col-md-2">
                    <label>排序</label>
                    <input type="number" class="form-control span1" name="sort" placeholder="排序号:100000以内数字" value="${model.sort}" maxlength="5"
                           required>
                </div>

                <div class="col-md-2">
                    <label class="control-label">菜单是否显示</label>
                    <select class="form-control span1" name="isShow" required>
                        <option id="1" value="1">是</option>
                        <option id="0" value="0">否</option>

                    </select>
                </div>

            </div>
            <div class="form-group row">
                    <div class="col-md-2">
                        <label>别名</label>
                        <input type="text" class="form-control span1" name="aliasName" placeholder="别名：100字以内" maxlength="100"
                               required value="${model.aliasName}">
                    </div>
             </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/M3AgentPermission/update')">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
