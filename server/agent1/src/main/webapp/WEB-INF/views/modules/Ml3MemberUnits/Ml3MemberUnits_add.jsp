<%--<%@ page import="com.rmkj.microcap.modules.Ml3OperateCenter.entity.Ml3OperateCenterBean" %>--%>
<%@ page import="java.util.List" %><%--
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
                    <label>VIP合伙人名称</label>
                    <input type="text" class="form-control span1" name="name" placeholder="VIP合伙人名称:20字以内"  maxlength="100" required>
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
                    <label>二级域名</label>
                    <input type="text" class="form-control span1" name="twoLevelDomain" placeholder="二级域名:20字以内" maxlength="100" required>
                </div>
            </div>
            <%--<div class="form-group row">--%>

            <%--</div>--%>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>备注</label>
                    <input type="text" class="form-control span1" name="remark" placeholder="备注:20字以内" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>返VIP合伙人手续费比例</label>
                    <input type="text" class="form-control span1" name="unitsReturnFeePercent" placeholder="请输入格式为数字的手续费比例" required/>
                </div>
                <div class="col-md-2">
                    <label>VIP合伙人手机号码</label>
                    <input type="text" class="form-control span1" name="mobile" placeholder="请输入11位手机号码" required/>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>返VIP合伙人服务费比例</label>
                    <input type="text" class="form-control span1" name="unitsReturnServicePercent" placeholder="请输入格式为数字的服务费比例" required/>
                </div>
            </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/Ml3MemberUnits/save');">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
    })
</script>
</body>
</html>