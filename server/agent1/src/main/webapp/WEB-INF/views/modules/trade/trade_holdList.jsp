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
            <span>客户名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="chnName" />
            <span>手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />
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
	                url: '${ctx}/trade/hold?id=${id}',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="chnName" align="center" data-options="width:100">客户名</th>
            <th field="mobile" align="center" data-options="width:100">客户手机号</th>
            <th field="buyTime" align="center" data-options="width:100">下单时间</th>
            <%--<th field="buyUpDown" align="center" data-options="width:100,--%>
                <%--formatter:function(value,row,index){--%>
                    <%--return value == 0 ? '<span style=\'color:red\'>买涨</span>' : '<span style=\'color:green\'>买跌</span>';--%>
                <%--}--%>
            <%--">买涨买跌</th>--%>
            <th field="pointValue" align="center" data-options="width:100">下单点位</th>
            <th field="buyPoint" align="center" data-options="width:100">下单金额</th>
            <th field="model" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value == 0 ? '微盘' : (value == 1 ? '时间':'点位');
                }
            ">交易类型</th>
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
            queryColumns: ["chnName","mobile"]
        };
        var topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
</script>
</body>
</html>