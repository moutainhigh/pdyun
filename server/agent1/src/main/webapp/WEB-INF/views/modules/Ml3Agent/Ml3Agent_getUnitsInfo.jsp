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
    <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/Ml3Agent/getUnitsInfo?id=${id}',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="name" align="center" data-options="width:100">VIP合伙人名称</th>
            <%--<th field="agentInviteCode" align="center" data-options="width:100">邀请码</th>--%>
            <%--<th field="money" align="center" data-options="width:100">保证金余额</th>--%>
            <%--<th field="bondMoney" align="center" data-options="width:100">保证金</th>--%>
            <th field="unitsReturnFeeMoney" align="center" data-options="
                formatter: function(value, row, index){
                    return value != 0 ? (value + '元') : value;
                }
            ">返手续费余额</th>
            <th field="unitsReturnFeeTotalMoney" align="center" data-options="
                formatter: function(value, row, index){
                    return value != 0 ? (value + '元') : value;
                }
            ">返手续费累计余额</th>
            <th field="unitsReturnFeePercent" align="center" data-options="
                formatter: function(value, row, index){
                    return value != 0 ? (value + '%') : value;
                }
            ">返手续费比例</th>
            <th field="unitsReturnServiceMoney" align="center" data-options="
                formatter: function(value, row, index){
                    return value != 0 ? (value + '元') : value;
                }
            ">返服务费余额</th>
            <th field="unitsReturnServiceTotalMoney" align="center" data-options="
                formatter: function(value, row, index){
                    return value != 0 ? (value + '元') : value;
                }
            ">返服务费累计余额</th>
            <th field="unitsReturnServicePercent" align="center" data-options="
                formatter: function(value, row, index){
                    return value != 0 ? (value + '%') : value;
                }
            ">返服务费比例</th>
            <th field="realName" align="center" data-options="width:100">真实姓名</th>
            <th field="idCard" align="center" data-options="width:100">身份证号</th>
            <th field="bankAccountName" align="center" data-options="width:100">银行开户姓名</th>
            <th field="bankAccount" align="center" data-options="width:100">银行账号</th>
            <th field="bankName" align="center" data-options="width:100">开户行名称</th>
            <th field="bankChildName" align="center" data-options="width:100">开户行支行名称</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/Ml3Agent/add" width="300" height="400"></div>
<div id="editWindow" url="${ctx}/Ml3Agent/editUnitsInfo01"></div>
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