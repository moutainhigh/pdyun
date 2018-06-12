<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-10-17
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
    <!-- 查询 -->
    <div>
        <label class="input-inline">
            <span>名称：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="name" />
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <!-- 筛选条件 结束 -->
    <shiro:hasPermission name="trade:add">
        <button id="addInfoBt" class="btn btn-info margin-r10">添加</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="trade:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="trade:delete">
        <button id="deleteBt" class="btn btn-info margin-r10">删除</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/trade/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="serialNo" align="center" data-options="width:100">流水号</th>
            <th field="userId" align="center" data-options="width:100">会员用户id</th>
            <th field="money" align="center" data-options="width:100">购买金额</th>
            <th field="type" align="center" data-options="width:100">类型 0 资金 1 代金券</th>
            <th field="fee" align="center" data-options="width:100">手续费</th>
            <th field="contractName" align="center" data-options="width:100">合约名称</th>
            <th field="pointValue" align="center" data-options="width:100">波动一个点，盈亏多少钱</th>
            <th field="profitMax" align="center" data-options="width:100">止盈百分比</th>
            <th field="lossMax" align="center" data-options="width:100">止损值</th>
            <%--<th field="buyUpDown" align="center" data-options="width:100">买涨买跌 0 买涨 1 买跌</th>--%>
            <th field="status" align="center" data-options="width:100">状态：0 持仓 1 平仓（交割）</th>
            <th field="buyTime" align="center" data-options="width:100">建仓时间</th>
            <th field="buyPoint" align="center" data-options="width:100">建仓价</th>
            <th field="sellPoint" align="center" data-options="width:100">平仓价</th>
            <th field="difMoney" align="center" data-options="width:100">盈亏金额（含手续费）</th>
            <th field="sellType" align="center" data-options="width:100">平仓类型 0 手动平仓 1 止盈止损平仓 2 休市平仓</th>
            <th field="sellTime" align="center" data-options="width:100">平仓（交割）时间</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/trade/add"></div>
<div id="editWindow" url="${ctx}/trade/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/trade/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["name"]
        };
        var topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
</script>
</body>
</html>