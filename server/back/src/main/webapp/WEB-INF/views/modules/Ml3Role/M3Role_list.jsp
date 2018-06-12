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
    <!-- 筛选条件 结束 -->
    <shiro:hasPermission name="M3Role:add">
        <button id="addInfoBt" class="btn btn-info margin-r10">添加</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="M3Role:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="M3Role:delete">
        <button id="deleteBt" class="btn btn-info margin-r10">删除</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="role_permission:add">
        <button id="setMenuBt" class="btn  btn-info margin-r10">设置权限</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/M3Role/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="name" align="center" data-options="width:100">角色名称</th>
            <th field="enname" align="center" data-options="width:100">英文名称</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/M3Role/add" width="255" height="270"></div>
<div id="editWindow" url="${ctx}/M3Role/edit" width="255" height="270"></div>
<div id="setMenuWindow" url="${ctx}/M3Role/setMenu" ></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/M3Role/delete'
        };
        new BaseOperationBt(operationObj);
        $("#setMenuBt").on("click", function () {
            var row = $("#listDataGrid").datagrid('getSelected');
            if (row) {
                    openFullWindow("设置菜单权限", "setMenuWindow", "id=" + row.id);
            }else{
                layer.msg('请选择');
            }
        });
    })
</script>
</body>
</html>