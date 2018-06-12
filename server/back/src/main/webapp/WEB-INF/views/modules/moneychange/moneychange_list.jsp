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
            <span>开始时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="createTimeMin" />
            <span>结束时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="createTimeMax" />
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <button class="btn btn-info margin-r10" onclick="exportExcel()">导出</button>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/moneychange/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <%--<th field="id" align="center" data-options="width:100">单号</th>--%>
            <th field="unitsName" align="center" data-options="width:100">所属VIP合伙人</th>
            <th field="agentRealName" align="center" data-options="width:100">所属合伙人</th>
            <th field="chnName" align="center" data-options="width:100">会员姓名</th>
            <th field="mobile" align="center" data-options="width:100">会员手机号</th>
            <th field="difMoney" align="center" data-options="width:100">变更金额</th>
            <th field="type" align="center" data-options="width:100,
            formatter: _formatter
            ">类型</th>
            <th field="beforeMoney" align="center" data-options="width:100">变更前资金</th>
            <th field="afterMoney" align="center" data-options="width:100">变更后金额</th>
            <%--<th field="code" align="center" data-options="width:100">合约代码</th>--%>
            <%--<th field="contractName" align="center" data-options="width:100">合约名称</th>--%>
            <th field="createTime" align="center" data-options="width:100">创建时间</th>
            <th field="remark" align="center" data-options="width:280">下单流水号</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/moneychange/add"></div>
<div id="editWindow" url="${ctx}/moneychange/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/moneychange/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["uname","mobile","createTimeMin","createTimeMax"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);


    })

    function exportExcel(){
        window.location.href = '${ctx}/moneychange/exportExcel?' + $.param(topSearchBar.getQueryDatas());
    }

    function _formatter(value,row,index) {
        if(value == '0' || value == '1'){
            row.code = '';
            row.contractName = '';
        }
        if(value == '0'){
            return '充值';
        }else if(value == '1'){
            return '提现';
        }else if(value == '2'){
            return '建仓';
        }else if(value == '3'){
            return '平仓';
        }else if(value == '4'){
            return '提现失败';
        }
    }
</script>
</body>
</html>