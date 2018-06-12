<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/17
  Time: 17:51
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
                    <label>有效交易流水：<span>${model.tradeMoney}</span> 元</label>
                </div>
                <div class="col-md-2">
                    <label>盈利：<span id="winMoney">${model.winMoney}</span> 元</label>
                </div>
                <div class="col-md-2">
                    <label>亏损：<span id="loseMoney">${model.loseMoney}</span> 元</label>
                </div
                <div class="col-md-2">
                    <label>提现次数：<span id="tiXianCount">${model.tiXianCount}</span> 次</label>
                </div>
            </div>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function(){
        var str  = $("#winMoney").text();
        if(str == null||str==""){
            $("#winMoney").text(0);
        }
        var str1  = $("#loseMoney").text();
        if(str1 == null||str1==""){
            $("#loseMoney").text(0);
        }
    })
</script>
</body>
</html>
