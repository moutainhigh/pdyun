<%--
Created by CodeGenerator.
User: zhangbowen
Date: 2015-9-8
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
    <!-- 筛选条件 结束 -->
    <shiro:hasPermission name="sysuser:add">
        <button id="addInfoBt" class="btn btn-info margin-r10">添加</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="sysuser:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="sysuser:delete">
        <button id="deleteBt" class="btn btn-info margin-r10">删除</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid" style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/sysuser/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
                <th field="loginName" align="center" data-options="width:100">登录名</th>
                <th field="no" align="center" data-options="width:100">工号</th>
                <th field="name" align="center" data-options="width:100">姓名</th>
                <th field="email" align="center" data-options="width:100">邮箱</th>
                <th field="phone" align="center" data-options="width:100">电话</th>
                <th field="mobile" align="center" data-options="width:100">手机</th>
                <th field="loginIp" align="center" data-options="width:100">最后登陆IP</th>
                <th field="loginDate" align="center" data-options="width:100">最后登陆时间</th>
                <th field="remarks" align="center" data-options="width:100">备注</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/sysuser/add"></div>
<div id="editWindow" url="${ctx}/sysuser/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/sysuser/delete'
        };
        new BaseOperationBt(operationObj);
    })
</script>
</body>
</html>
