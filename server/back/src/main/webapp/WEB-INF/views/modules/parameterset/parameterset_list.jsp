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
<body class="easyui-layout">
<div region="north" style="padding:5px;" border="false">
    <!-- 查询 -->
    <%--<div>--%>
        <%--<label class="input-inline">--%>
            <%--<span>名称：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="name" />--%>
            <%--<button id="queryListBt" class="btn btn-info margin-r10">查询</button>--%>
            <%--<button id="clearBarBt" class="btn btn-info margin-r10">清空</button>--%>
        <%--</label>--%>
    <%--</div>--%>
    <!-- 筛选条件 结束 -->
    <%--<shiro:hasPermission name="parameterset:add">--%>
        <%--<button id="addInfoBt" class="btn btn-info margin-r10">添加</button>--%>
    <%--</shiro:hasPermission>--%>
    <shiro:hasPermission name="parameterset:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="parameterset:delete">
        <button id="deleteBt" class="btn btn-info margin-r10">删除</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/parameterset/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="subServiceScale" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value+'%';
                }
            ">认购服务费比例(%)</th>
            <th field="subFeeScale" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value+'%';
                }
            ">交易手续费比例(%)</th>
            <th field="integralReturnScale" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value+'%';
                }
            ">负积分返还比例(%)</th>
            <th field="upAndDownPercent" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value+'%';
                }
            ">挂单价格上下浮动比例(%)</th>
            <th field="weekDaySet" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value;
                }
            ">开市时间(天)</th>
            <th field="openTime" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value;
                }
            ">开始时间(时间段)</th>


            <%--<th field="holdCount" align="center" data-options="width:100,--%>
            <%--formatter:function(value,row,index){--%>
                    <%--return value+' 单';--%>
                <%--}--%>
            <%--">最大持仓单数</th>--%>
            <%--<th field="holdMoney" align="center" data-options="width:100,--%>
            <%--formatter:function(value,row,index){--%>
                <%--return value+' 元';--%>
            <%--}--%>
            <%--">最大持仓金额</th>--%>
            <%--<th field="cashMoney" align="center" data-options="width:100,--%>
                <%--formatter:function(value,row,index){--%>
                    <%--return value+' 元';--%>
                <%--}--%>
            <%--">系统实时返现金额</th>--%>
            <%--<th field="cashMoneyRation" align="center" data-options="width:100,--%>
                <%--formatter:function(value,row,index){--%>
                    <%--return value+' 元';--%>
                <%--}--%>
            <%--">每日提现限额</th>--%>
            <%--<th field="cashMoneyCount" align="center" data-options="width:50,--%>
                <%--formatter:function(value,row,index){--%>
                    <%--return value+' 次';--%>
                <%--}--%>
            <%--">每日提现次数</th>--%>
            <%--<th field="qrCodeUrl" align="center" data-options="width:50">推广二维码url</th>--%>
            <%--<th field="qrCodeMenuUrl" align="center" data-options="width:50">微信菜单url</th>--%>
            <%--<th field="ordersMinMoney" align="center" data-options="width:50,--%>
                <%--formatter: function(value,row,index){--%>
                    <%--return value + ' 元';--%>
                <%--}--%>
            <%--">最低建仓金额</th>--%>
            <%--<th field="holdOrdersCount" align="center" data-options="width:50,--%>
                <%--formatter: function(value,row,index){--%>
                    <%--return value + ' 单';--%>
                <%--}--%>
            <%--">同时持仓单数</th>--%>
            <th field="percentBuBing" align="center" data-options="width:50,
                formatter: function(value,row,index){
                    return value + '%';
                }
            ">客2返佣(%)</th>
            <th field="percentQiBing" align="center" data-options="width:50,
                formatter: function(value,row,index){
                    return value + '%';
                }
            ">客1返佣(%)</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/parameterset/add"></div>
<div id="editWindow" url="${ctx}/parameterset/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/parameterset/delete'
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