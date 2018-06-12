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
    <shiro:hasPermission name="moneyrecord:add">
        <button id="addInfoBt" class="btn btn-info margin-r10">添加</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="moneyrecord:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="moneyrecord:delete">
        <button id="deleteBt" class="btn btn-info margin-r10">删除</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/moneyrecord/list',
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
            <th field="money" align="center" data-options="width:100">金额</th>
            <th field="fee" align="center" data-options="width:100">手续费</th>
            <th field="type" align="center" data-options="width:100">类型：0 充值 1 提现</th>
            <th field="thirdSerialNo" align="center" data-options="width:100">第三方流水号</th>
            <th field="status" align="center" data-options="width:100">状态 0 处理中 1 成功 2 失败</th>
            <th field="createTime" align="center" data-options="width:100">创建时间</th>
            <th field="completeTime" align="center" data-options="width:100">完成时间</th>
            <th field="chnName" align="center" data-options="width:100">姓名</th>
            <th field="bankName" align="center" data-options="width:100">银行名称</th>
            <th field="bankAccount" align="center" data-options="width:100">银行卡号</th>
            <th field="failureReason" align="center" data-options="width:100">失败原因</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/moneyrecord/add"></div>
<div id="editWindow" url="${ctx}/moneyrecord/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/moneyrecord/delete'
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