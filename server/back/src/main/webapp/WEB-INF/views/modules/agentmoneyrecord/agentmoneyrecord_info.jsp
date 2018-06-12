
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
                    <label>姓名：<span id="rechargeMoney">${model.chnName}</span></label>
                </div><br/>
                <div class="col-md-2">
                    <label>银行名称：<span id="uBankno">${model.bankName}</span></label>
                </div><br/>
                <div class="col-md-2">
                    <label>银行卡号：<span id="uBankname">${model.bankAccount}</span></label>
                </div><br/>
                <div class="col-md-2">
                    <label>失败原因：<span id="uIdnumber">${model.failureReason}</span></label>
                </div>

            </div>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
