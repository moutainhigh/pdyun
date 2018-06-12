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
    <shiro:hasPermission name="Ml3AgentUser:add">
        <button id="addInfoBt" class="btn btn-info margin-r10">添加</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="Ml3AgentUser:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="Ml3AgentUser:delete">
        <button id="deleteBt" class="btn btn-info margin-r10">删除</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/Ml3AgentUser/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="centerName" align="center" data-options="width:100">所属市场管理部名称</th>
            <th field="unitsName" align="center" data-options="width:100">VIP合伙人名称</th>
            <th field="agentAccount" align="center" data-options="width:100">账号</th>
            <th field="agentMobile" align="center" data-options="width:100">手机号</th>
            <th field="agentPassWord" align="center" data-options="width:100">密码</th>
            <th field="agentCreateTime" align="center" data-options="width:100">创建时间</th>
            <th field="agentLastTime" align="center" data-options="width:100">上次登录时间</th>
            <th field="agentLastIp" align="center" data-options="width:100">上次登录IP</th>
            <th field="agentStatus" align="center" data-options="width:100,
            formatter:function(value,index,row){
                    return value == 0?'正常':'停用';
                }
            ">状态</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/Ml3AgentUser/add"></div>
<div id="editWindow" url="${ctx}/Ml3AgentUser/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/Ml3AgentUser/delete'
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