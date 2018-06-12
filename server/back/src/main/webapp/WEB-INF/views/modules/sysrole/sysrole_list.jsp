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
    <shiro:hasPermission name="sysrole:add">
        <button id="addInfoBt" class="btn btn-info margin-r10">添加</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="sysrole:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="sysrole:delete">
        <button id="deleteBt" class="btn btn-info margin-r10">删除</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="role_permission:add">
        <button id="setMenuBt" class="btn  btn-info margin-r10">设置权限</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid" style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/sysrole/list',
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
<div id="addWindow" url="${ctx}/sysrole/add" width="255" height="270"></div>
<div id="editWindow" url="${ctx}/sysrole/edit" width="255" height="270"></div>
<div id="setMenuWindow" url="${ctx}/sysrole/setMenu" ></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/sysrole/delete'
        };
        new BaseOperationBt(operationObj);

        $("#setMenuBt").on("click", function () {
            var row = $("#listDataGrid").datagrid('getSelected');
            if (row) {
                if(row.id=='1'){
                    layer.msg('系统管理员拥有所有权限');
                }else {
                    openFullWindow("设置菜单权限", "setMenuWindow", "id=" + row.id);
                }
            }else{
                layer.msg('请选择');
            }
        });
    })
</script>
</body>
</html>
