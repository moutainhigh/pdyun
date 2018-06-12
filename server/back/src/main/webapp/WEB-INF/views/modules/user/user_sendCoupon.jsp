<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/4
  Time: 11:24
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
                    <label>代金券面值</label>
                    <input type="text" class="form-control span1" name="sendMoney" placeholder="" required>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-4">
                    <label>备注</label>
                    <input type="text" class="form-control span1" name="content" placeholder="" required>
                </div>
            </div>
            <%--<div class="form-group row">--%>
                <%--<div class="col-md-4">--%>
                    <%--<label>到期日期</label>--%>
                    <%--&lt;%&ndash;<input type="text" class="form-control span1" name="end_date" placeholder="" required>&ndash;%&gt;--%>
                    <%--<input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="end_date" name="end_date"/>--%>
                <%--</div>--%>
            <%--</div>--%>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/user/sendCoupon')">确定</button>
        </form>
    </div>
</div>
<script type="application/javascript">
    function _check(){
        var uMoney = $('input[name="sendMoney"]').val();
        if(!/[0-9]+\.?[0-9]{0,2}/.test(uMoney)){
            layer.msg('金额不合法，最多精确到小数点后两位', {
                offset: getLayerCenter()
            });
            return false;
        }
        return true;
    }
</script>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
