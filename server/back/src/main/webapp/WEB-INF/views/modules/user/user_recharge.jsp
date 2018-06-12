<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/18
  Time: 11:28
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
            <input type="hidden" name="id" value="${model.id}">
            <div class="form-group row">
                <div class="col-md-4">
                    <label>客户手机号:</label>
                    <input type="text" class="form-control span1" name="mobile" placeholder="" required>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-4">
                    <label>充值金额:<%--(当期用户，姓名：，资金：￥)--%></label>
                    <input type="text" class="form-control span1" name="money" placeholder="" required>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-4">
                    <label>第三方流水号</label>
                    <input type="text" class="form-control span1" name="thirdSerialNo" placeholder="" required>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-4">
                    <label>备注信息</label>
                    <input type="text" class="form-control span1" name="remarks" placeholder="" required>
                </div>
            </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/user/recharge')">确定</button>
        </form>
    </div>
</div>
<script type="application/javascript">
    function _check(){
        var uMoney = $('input[name="money"]').val();
        if('' == uMoney){
            return false;
        }
        var str = uMoney.split(".");
        if(str.length >= 2){
            if(str[1].length>2){
                layer.msg('金额不合法，最多精确到小数点后两位', {
                    offset: getLayerCenter()
                });
                return false;
            }
        }
        return true;
    }
</script>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
