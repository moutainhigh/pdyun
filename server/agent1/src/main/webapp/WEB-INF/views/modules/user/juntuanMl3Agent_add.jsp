<%@ page import="java.util.List" %>
<%@ page import="com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean" %><%--
Created by CodeGenerator.
User: Administrator
Date: 2016-11-17
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body style="background: #ffffff;border-top:3px solid #3c8dbc;">
<div class="panel panel-default">
    <div class="panel-body" style="padding:10px 10px 10px 20px;border: none">
        <form>
            <%--<input type="hidden" name="userId" value="${userBean.id}">--%>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>账号</label>
                    <input type="text" class="form-control span1" name="account" placeholder="账号:20字以内"  maxlength="100" required>
                </div>
                <%--<br/>--%>
                <div class="col-md-2">
                    <label>密码</label>
                    <input type="text" class="form-control span1" name="safePassword" placeholder="密码:20字以内" maxlength="100" required>
                </div>
                <%--<br/>--%>
                <div class="col-md-2">
                    <label>姓名</label>
                    <input type="text" id="realName" class="form-control span1" name="realName" placeholder="姓名:20字以内"  maxlength="100" required value="${userBean.chnName}">
                </div>
                <%--<br/>--%>
                <div class="col-md-2">
                    <label>手机号</label>
                    <input type="text" id="mobile" class="form-control span1" name="mobile" placeholder="手机号:20字以内" maxlength="100" required value="${userBean.mobile}">
                </div>
                <br/>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>返合伙人手续费比例(%)</label>
                    <input type="text" id="agentReturnFeePercent" class="form-control span1" name="agentReturnFeePercent" placeholder="请输入格式为数字的手续费比例" maxlength="100" required value="">
                </div>
                <div class="col-md-2">
                    <label>返合伙人服务费比例(%)</label>
                    <input type="text" id="agentReturnServicePercent" class="form-control span1" name="agentReturnServicePercent" placeholder="请输入格式为数字的服务费比例" maxlength="100" required value="">
                </div>
            </div>
            <button type="submit" id="sub"  class="btn btn-primary" style="float: right">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        $("#sub").click(function () {
            var mobile = $("input[name=mobile]").val();//输入的手机号
            var account = $("input[name=account]").val();//输入的账号
            var array = new Array();
            <c:forEach items="${list}" var="item">
            array.push("${item}")
            </c:forEach>
            for(var i = 0;i<array.length;i++){
                if(mobile == array[i]){
                    alert("手机号已存在,请重新输入");
                    return;
                }
            }
            var array1 = new Array();
            <c:forEach items="${accountList}" var="item1">
            array1.push("${item1}")
            </c:forEach>
            for(var i = 0;i<array1.length;i++){
                if(account == array1[i]){
                    alert("账号名已存在,请重新输入");
                    return;
                }
            }
            infoAjaxSubmit(this,'${ctx}/user/saveMl3Agent');
        })
    })
</script>
</body>
</html>