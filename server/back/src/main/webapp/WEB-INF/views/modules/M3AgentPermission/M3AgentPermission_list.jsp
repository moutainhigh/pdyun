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
    <shiro:hasPermission name="M3AgentPermission:add">
        <button id="addMenuBt" class="btn btn-info margin-r10">添加菜单</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="M3AgentPermission:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="M3AgentPermission:delete">
        <button id="deleteBt" class="btn btn-info margin-r10">删除</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-treegrid" style="width: 100%;" singleSelect="true"
           data-options="
	                url: '${ctx}/M3AgentPermission/list',
	                lines: true,
	                striped: true,
	                fitColumns:true,
	                resizeHandle:'right',
	                idField:'id',
	                treeField:'name',
	                onExpand:datagridLoadSuccess,
	                onCollapse:datagridLoadSuccess,
	                onLoadSuccess:datagridLoadSuccess
	            ">

        <thead>
        <tr>
            <th field="name" align="left" data-options="width:100">名称</th>
            <th field="aliasName" align="left" data-options="width:100">别名</th>
            <th field="isShow" align="left" data-options="width:100,
                formatter:function(value,index,row){
                    return value == 1?'是':'否'
                }
            ">
                是否在菜单中显示
            </th>
            <th field="permission" align="left" data-options="width:100">权限标识</th>
            <th field="href" align="left" data-options="width:100">链接</th>
            <th field="icon" align="left" data-options="width:100">图标标识</th>
            <th field="sort" align="left" data-options="width:100">排序</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/M3AgentPermission/add"></div>
<div id="editWindow" url="${ctx}/M3AgentPermission/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/M3AgentPermission/delete'
        };
        new BaseOperationBt(operationObj);

        $("#addMenuBt").on("click", function () {
            var row = $("#listDataGrid").datagrid('getSelected');
            if (row) {
                openFullWindow("添加菜单", "addWindow", "id=" + row.id);
            } else {
                layer.msg('请选择',{
                    offset:getLayerCenter()
                });
            }
        });
    })
</script>
</body>
</html>
