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
            <%--<span>会员姓名：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="uname" />--%>
            <span>会员手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />
            <%--<span>平仓类型：</span>
            <select style="width:200px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;" id="status">
                <option value="">--请选择--</option>
                <option value="1">正常</option>
                <option value="2">休市平仓</option>
                <option value="3">无效平仓</option>
            </select>--%>
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
            <th field="serialNo" align="center" data-options="width:80">流水号</th>
            <th field="unitsName" align="center" data-options="width:80">所属VIP合伙人</th>
            <th field="uname" align="center" data-options="width:80">客户姓名</th>
            <th field="mobile" align="center" data-options="width:80">会员手机号</th>
            <th field="goodsName" align="center" data-options="width:60,
                formatter:function(value,row,index){
                    return value;
                }
            ">商品名称</th>
            <th field="money" align="center" data-options="width:60,
                formatter:function(value,row,index){
                    return (Number(value)+Number(row.couponMoney)) + (Number(row.couponMoney) != 0 ? '（'+row.couponMoney+'）' : '');
                }
            ">总额(券额)</th>
            <%--<th field="type" align="center" data-options="width:50,--%>
                <%--formatter:function(value,row,index){--%>
                    <%--return Number(row.couponMoney) == 0 ? '资金' : (Number(row.money) == 0 ? '券':'资金+券');--%>
                <%--}--%>
            <%--">付费方式</th>--%>
            <%--<th field="offTime" align="center" data-options="width:40">时间间隔</th>--%>
            <%--<th field="buyUpDown" align="center" data-options="width:50,--%>
                <%--formatter:function(value,row,index){--%>
                    <%--return value == 0 ? '<span style=\'color:red\'>买涨↑</span>' : '<span style=\'color:green\'>买跌↓</span>';--%>
                <%--}--%>
            <%--">买涨买跌</th>--%>
            <th field="buyPoint" align="center" data-options="width:60">建仓价</th>
            <th field="sellPoint" align="center" data-options="width:60">平仓价</th>
            <th field="sellType" align="center" data-options="width:40,
                formatter:function(value,row,index){
                    if(Number(row.buyPoint) === Number(row.sellPoint)){
                        return '平';
                    }else if(row.difMoney > 0){
                        return '<span style=\'color:red\'>赢<span>';
                    }else{
                        return '<span style=\'color:green\'>亏<span>';
                    }
                }
            ">盈亏平</th>
            <th field="difMoney" align="center" data-options="width:50">盈亏</th>
            <th field="buyTime" align="center" data-options="width:100">建仓时间</th>
            <th field="sellTime" align="center" data-options="width:120">平仓时间</th>
            <th field="holdNum" align="center" data-options="width:60">数量</th>
            <th field="serviceFee" align="center" data-options="width:60">认购服务费</th>
            <th field="feeBuy" align="center" data-options="width:60">买入手续费</th>
            <th field="feeSell" align="center" data-options="width:60">卖出手续费</th>
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
            queryColumns: ["uname","mobile","sellType"]
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