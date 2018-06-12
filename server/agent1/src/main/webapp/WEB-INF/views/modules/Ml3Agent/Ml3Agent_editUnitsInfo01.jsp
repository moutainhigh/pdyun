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
            <input type="hidden" name="id" value="${model.id}">
            <div class="form-group row">
                <div class="col-md-2">
                    <label>VIP合伙人名称</label>
                    <input type="text" class="form-control span1" name="name" placeholder="VIP合伙人名称:18位"  maxlength="100" required value="${model.name}">
                </div>
                <div class="col-md-2">
                    <label>身份证号</label>
                    <input type="text" class="form-control span1" name="idCard" placeholder="身份证号:18位"  maxlength="100" required value="${model.idCard}">
                </div>
                <div class="col-md-2">
                    <label>银行开户姓名</label>
                    <input type="text" class="form-control span1" name="bankAccountName" placeholder="银行开户姓名:20字以内"  maxlength="100" required value="${model.bankAccountName}">
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>银行账号</label>
                    <input type="text" class="form-control span1" name="bankAccount" placeholder="银行账号:20字以内"  maxlength="100" required value="${model.bankAccount}">
                </div>
                <div class="col-md-2">
                    <label>开户行名称</label>
                    <input type="text" class="form-control span1" name="bankName" placeholder="开户行名称:20字以内" maxlength="100" required value="${model.bankName}">
                </div>
                <div class="col-md-2">
                    <label>开户行支行名称</label>
                    <input type="text" class="form-control span1" name="bankChildName" placeholder="开户行支行名称:20字以内"  maxlength="100" required value="${model.bankChildName}">
                </div>
            </div>
            <button type="submit" id="sub" class="btn btn-primary">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $("#sub").click(function () {
        var idCard = $("input[name=idCard]").val();
        if(idCard.length!=18){
            alert("身份证号必须是18位，请重新输入！");
            return;
        }
        infoAjaxSubmit(this,'${ctx}/Ml3Agent/updateUnitsInfo01')
    })
</script>
</body>
</html>