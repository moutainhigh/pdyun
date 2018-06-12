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
            <input type="hidden" name="id" value="${model.id}">
            <div class="form-group" style="width: 200px;">
                <label>数据值</label>
                <input type="text" class="form-control span1" name="value" placeholder="数据值长度:250字内" value="${model.value}" maxlength="250"
                       required>
            </div>
            <div class="form-group" style="width: 200px;">
                <label>标签名</label>
                <input type="text" class="form-control span1" name="label" placeholder="标签名长度:250字内" value="${model.label}" maxlength="250"
                       required>
            </div>
            <div class="form-group" style="width: 200px;">
                <label>类型</label>
                <input type="text" class="form-control span1" name="type" placeholder="类型长度:100字内" value="${model.type}" maxlength="100"
                       required>
            </div>
            <div class="form-group" style="width: 200px;">
                <label>描述</label>
                <input type="text" class="form-control span1" name="description" placeholder="描述长度:250字内" value="${model.description}" maxlength="250"
                       required>
            </div>
            <div class="form-group" style="width: 200px;">
                <label>排序</label>
                <input type="number" class="form-control span1" name="sort" placeholder="排序号:100000以内数字" value="${model.sort}" maxlength="5"
                       required>
            </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/dic/update')">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
