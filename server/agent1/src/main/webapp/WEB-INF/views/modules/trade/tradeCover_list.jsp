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
            <span>会员姓名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="uname" />
            <span>会员手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <button class="btn btn-info margin-r10" onclick="exportExcel()">导出</button>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/trade/listCover',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="serialNo" align="center" data-options="width:165">流水号</th>
            <th field="uname" align="center" data-options="width:100">会员姓名</th>
            <th field="mobile" align="center" data-options="width:120">会员手机号</th>
            <th field="code" align="center" data-options="width:100">合约代码</th>
            <th field="contractName" align="center" data-options="width:100">合约名称</th>
            <%--<th field="buyUpDown" align="center" data-options="width:100,--%>
                <%--formatter:function(value,row,index){--%>
                    <%--return value == 0 ? '<span style=\'color:red\'>买涨</span>' : '<span style=\'color:green\'>买跌</span>';--%>
                <%--}--%>
            <%--">买涨买跌</th>--%>
            <th field="money" align="center" data-options="width:100">购买金额</th>
            <th field="fee" align="center" data-options="width:100">手续费</th>
            <th field="buyPoint" align="center" data-options="width:100">建仓价</th>
            <th field="sellPoint" align="center" data-options="width:100">平仓价</th>
            <th field="difMoney" align="center" data-options="width:100">盈亏金额（含手续费）</th>
            <%--<th field="pointValue" align="center" data-options="width:100">点值</th>--%>
            <%--<th field="profitMax" align="center" data-options="width:100">止盈百分比</th>--%>
            <%--<th field="lossMax" align="center" data-options="width:100">止损百分比</th>--%>
            <th field="type" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value == 0 ? '资金' : '代金券';
                }
            ">付费方式</th>
            <th field="buyTime" align="center" data-options="width:160">建仓时间</th>
            <th field="sellType" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value == 0 ? '手动平仓' : (value == 1 ? '止盈止损平仓':'休市平仓');
                }
            ">平仓类型</th>
            <th field="sellTime" align="center" data-options="width:160">平仓（交割）时间</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/trade/add"></div>
<div id="editWindow" url="${ctx}/trade/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/trade/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["uname","mobile"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function exportExcel(){
        window.location.href = '${ctx}/trade/exportExcelCover?' + $.param(topSearchBar.getQueryDatas());
    }
</script>
</body>
</html>