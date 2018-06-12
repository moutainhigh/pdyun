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
                    <label>市场管理部名称</label>
                    <input type="text" class="form-control span1" name="name" placeholder="市场管理部名称:20字以内"  maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>保证金</label>
                    <input type="text" class="form-control span1" name="bondMoney" placeholder="保证金:20字以内" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>真实姓名</label>
                    <input type="text" class="form-control span1" name="realName" placeholder="真实姓名:20字以内" maxlength="100" required>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>身份证</label>
                    <input type="text" class="form-control span1" name="idCard" placeholder="身份证:20字以内"  maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>银行开户姓名</label>
                    <input type="text" class="form-control span1" name="bankAccountName" placeholder="银行开户姓名:20字以内" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>银行账号</label>
                    <input type="text" class="form-control span1" name="bankAccount" placeholder="银行账号:20字以内" maxlength="100" required>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>开户行</label>
                    <input type="text" class="form-control span1" name="bankName" placeholder="开户行:20字以内"  maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>开户银行支行名</label>
                    <input type="text" class="form-control span1" name="bankChildName" placeholder="开户银行支行名:20字以内" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>返市场管理部手续费比例(%)</label>
                    <input type="text" class="form-control span1" name="returnFeePercent" placeholder="请输入数字比例" maxlength="100" required>
                </div>
                <%--<div class="col-md-2">--%>
                    <%--<label>状态</label>--%>
                    <%--<select class="form-control span1" name="status" required>--%>
                        <%--<option selected="selected" value="0">正常</option>--%>
                        <%--<option value="1">停用</option>--%>
                    <%--</select>--%>
                <%--</div>--%>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>返市场管理部服务费比例(%)</label>
                    <input type="text" class="form-control span1" name="returnServicePercent" placeholder="请输入数字比例" maxlength="100" required>
                </div>
            </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/Ml3OperateCenter/save')">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>