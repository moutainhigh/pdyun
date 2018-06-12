<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-12-16
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
        <form onsubmit="return _check();">
            <div class="form-group row">
                <div class="col-md-2">
                    <label>最大持仓单数</label>
                    <input type="text" class="form-control span1" name="holdCount"   maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>最大持仓金额</label>
                    <input type="text" class="form-control span1" name="holdMoney" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>系统实时返现金额</label>
                    <input type="text" class="form-control span1" name="cashMoney"  maxlength="100" required>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>每日提现限额</label>
                    <input type="text" class="form-control span1" name="cashMoneyRation"  maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>每日提现次数</label>
                    <input type="text" class="form-control span1" name="cashMoneyCount"  maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>推广二维码url</label>
                    <input type="text" class="form-control span1" name="qrCodeUrl"  maxlength="100" required>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>微信菜单url</label>
                    <input type="text" class="form-control span1" name="qrCodeMenuUrl"  maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>最低建仓金额</label>
                    <input type="text" class="form-control span1" name="ordersMinMoney"  maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>同时持仓单数</label>
                    <input type="text" class="form-control span1" name="holdOrdersCount"  maxlength="100" required>
                </div>
            </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/parameterset/save')">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    function _check(){
        var holdMoney = $('input[name="holdMoney"]').val();
        if(!(/[0-9]+\.?[0-9]{0,2}/.test(holdMoney))){
            layer.msg('金额不合法，最多精确到小数点后两位', {
                offset: getLayerCenter()
            });
            return false;
        }
        return true;
    }
</script>
</body>
</html>