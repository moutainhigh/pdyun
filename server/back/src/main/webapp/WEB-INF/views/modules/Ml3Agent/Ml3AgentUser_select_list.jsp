<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/11
  Time: 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <link rel="stylesheet" href="${ctxStatic}/modules/comboselect/css/combo.select.css">
    <style type="text/css">
        .jq22 { width: 400px; margin: 100px auto;}
        button{margin-left:40%;}
    </style>
</head>
<body>
    <div class="jq22">
        <input type="hidden" id="ids" value="${ids}">
        <select id="agent">
            <option value="">选择合伙人</option>
            <c:forEach items="${ml3AgentUser}" var="agentUser">
                <option value="${agentUser.id}">${agentUser.account}</option>
            </c:forEach>
        </select>
    </div>
    <button class="btn btn-info" id="btn">确定平移客户</button>
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    <script src="${ctxStatic}/modules/comboselect/js/jquery.combo.select.js"></script>
    <script type="text/javascript">
        $(function () {
            $('select').comboSelect();
        });

        $("#btn").on("click", function () {
            var _ids = $("#ids").val();
            var select = $("#agent").val();
            $.ajax({
                url: ctx + '/Ml3Agent/updateUserAgentInviteCode',
                type: 'POST',
                data: {
                    ids: _ids,
                    agentId: select
                },
                success: function (data) {
                    layer.msg(data.msg);
                }
            })
        });
    </script>
</body>
</html>
