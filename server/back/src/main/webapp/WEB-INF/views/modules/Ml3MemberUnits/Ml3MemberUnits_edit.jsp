<%@ page import="com.rmkj.microcap.modules.Ml3OperateCenter.entity.Ml3OperateCenterBean" %>
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
            <input type="hidden" name="id" value="${model.id}">
            <div class="form-group row">

                <div class="col-md-2">
                    <label>会员中心名称</label>
                    <input type="text" class="form-control span1" name="name" placeholder="会员中心名称:20字以内"  maxlength="100" required value="${model.name}">
                </div>
                <div class="col-md-2">
                    <label>保证金</label>
                    <input type="text" class="form-control span1" name="bondMoney" placeholder="保证金:20字以内" maxlength="100" required value="${model.bondMoney}" disabled = "disabled">
                </div>

            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>真实姓名</label>
                    <input type="text" class="form-control span1" name="realName" placeholder="真实姓名:20字以内" maxlength="100" required value="${model.realName}">
                </div>
                <div class="col-md-2">
                    <label>身份证</label>
                    <input type="text" class="form-control span1" name="idCard" placeholder="身份证:20字以内"  maxlength="100" required value="${model.idCard}">
                </div>


            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>银行开户姓名</label>
                    <input type="text" class="form-control span1" name="bankAccountName" placeholder="银行开户姓名:20字以内" maxlength="100" required value="${model.bankAccountName}">
                </div>
                <div class="col-md-2">
                    <label>银行账号</label>
                    <input type="text" class="form-control span1" name="bankAccount" placeholder="银行账号:20字以内" maxlength="100" required value="${model.bankAccount}">
                </div>

            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>开户行</label>
                    <input type="text" class="form-control span1" name="bankName" placeholder="开户行:20字以内"  maxlength="100" required value="${model.bankName}">
                </div>
                <div class="col-md-2">
                    <label>开户银行支行名</label>
                    <input type="text" class="form-control span1" name="bankChildName" placeholder="开户银行支行名:20字以内" maxlength="100" required value="${model.bankChildName}">
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>二级域名</label>
                    <input type="text" class="form-control span1" name="twoLevelDomain" placeholder="二级域名:20字以内" maxlength="100" required value="${model.twoLevelDomain}">
                </div>
                <div class="col-md-2">
                    <label>备注</label>
                    <input type="text" class="form-control span1" name="remark" placeholder="备注:20字以内" maxlength="100" required value="${model.remark}">
                </div>
            </div>
            <div class="form-group row">
                <%--<div class="col-md-2">--%>
                    <%--<label>保证金余额限制(元)</label>--%>
                    <%--<input type="text" class="form-control span1" name="moneyLimit" placeholder="请输入限制金额" maxlength="100" required value="${model.moneyLimit}">--%>
                <%--</div>--%>
                <div class="col-md-2">
                    <label>VIP合伙人手机号</label>
                    <input type="text" class="form-control span1" name="mobile" placeholder="请输入11位手机号码" maxlength="100" required value="${model.mobile}">
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>返VIP合伙人服务费比例(%)</label>
                    <input type="text" class="form-control span1" name="unitsReturnServicePercent" placeholder="请输入限制金额" maxlength="100" required value="${model.unitsReturnServicePercent}">
                </div>
                <div class="col-md-2">
                    <label>返VIP合伙人手续费费比例(%)</label>
                    <input type="text" class="form-control span1" name="unitsReturnFeePercent" placeholder="请输入11位手机号码" maxlength="100" required value="${model.unitsReturnFeePercent}">
                </div>
            </div>
            <button id="sub" type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/Ml3MemberUnits/update');">提交</button>
        </form>
    </div>
</div>


<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var str = "${model.centerName}";
        var sel = document.getElementById("se");
        for(var i = 0;i<sel.options.length;i++){
            if(sel.options[i].text == str){
                sel.options[i].selected = true;
                break;
            }
        }
//        $("#sub").click(function () {
//           var str =  $("#se").find("option:selected").text();//选中的文本
//            if(str == "请选择"){
//                layer.msg("请选择一个市场管理部！");
//                return false;
//            }
//        })
    })
</script>
</body>
</html>