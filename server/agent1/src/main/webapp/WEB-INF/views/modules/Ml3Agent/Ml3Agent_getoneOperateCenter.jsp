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
<body class="easyui-layout">
<div region="north" style="padding:5px;" border="false">
    <%--<!-- 查询 -->--%>
    <%--<div>--%>
    <%--<label class="input-inline">--%>
    <%--<span>代理名：</span>--%>
    <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="realName" />--%>
    <%--<span>手机号：</span>--%>
    <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />--%>
    <%--<button id="queryListBt" class="btn btn-info margin-r10">查询</button>--%>
    <%--<button id="clearBarBt" class="btn btn-info margin-r10">清空</button>--%>
    <%--</label>--%>
    <%--</div>--%>
    <!-- 筛选条件 结束 -->
    <%--<button id="addInfoBt" class="btn btn-info margin-r10">添加代理</button>--%>
    <%--<shiro:hasPermission name="Ml3Agent:edit">--%>
    <%--<button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>--%>
    <%--</shiro:hasPermission>--%>
    <%--<button id="deleteBt" class="btn btn-info margin-r10">删除</button>--%>
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/Ml3Agent/getoneOperateCenter?id=${id}',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="account" align="center" data-options="width:100">账号</th>
            <th field="mobile" align="center" data-options="width:100">手机号</th>
            <th field="centerRealName" align="center" data-options="width:100">名字</th>
            <th field="agentReturnFeeMoney" align="center">返手续费余额</th>
            <th field="agentReturnFeeTotalMoney" align="center">返手续费累计金额</th>
            <th field="agentReturnFeePercent" align="center">返手续费比例</th>
            <th field="agentReturnServiceMoney" align="center">返服务费余额</th>
            <th field="agentReturnServiceTotalMoney" align="center">返服务费累计金额</th>
            <th field="agentReturnServicePercent" align="center">返服务费比例</th>
            <th field="centerIdCard" align="center" data-options="width:100">身份证</th>
            <th field="centerBankAccountName" align="center" data-options="width:100">银行开户姓名</th>
            <th field="centerBankAccount" align="center" data-options="width:100">银行账号</th>
            <th field="centerBankName" align="center" data-options="width:100">开户行名称</th>
            <th field="centerBankChildName" align="center" data-options="width:100">开户行支行名称</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/Ml3Agent/add" width="300" height="400"></div>
<div id="editWindow" url="${ctx}/Ml3Agent/editInfo"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/Ml3Agent/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["realName","mobile"]
        };

        var topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })

</script>
</body>
</html>