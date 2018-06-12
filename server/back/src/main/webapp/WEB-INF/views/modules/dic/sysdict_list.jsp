<%--
Created by CodeGenerator.
User: zhangbowen
Date: 2015-8-11
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
    <div class="searchBar">
        <label class="input-inline">
            <span>数据值：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="value" />
            <span>标签名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="label" />
            <span>类型：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="type" />
            <span>描述：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="description" />
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <!-- 筛选条件 结束 -->
    	<shiro:hasPermission name="sys_dic:add">
        	<button id="addInfoBt" class="btn btn-info margin-r10">添加</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="sys_dic:edit">
        	<button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="sys_dic:delete">
        	<button id="deleteBt" class="btn btn-info margin-r10">删除</button>
        </shiro:hasPermission>
</div>
<div region="center" border="false" >
    <table id="listDataGrid" class="easyui-datagrid" singleSelect="true" pagination="true" style="width: 100%"
           data-options="
	                url: '${ctx}/dic/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                resizeHandle:'right',
	                onLoadSuccess:datagridLoadSuccess
	            ">

        <thead>
        <tr>
                <th field="value" align="center" data-options="width:100">数据值</th>
                <th field="label" align="center" data-options="width:100">标签名</th>
                <th field="type" align="center" data-options="width:100">类型</th>
                <th field="description" align="center" data-options="width:100">描述</th>
                <th field="sort" align="center" data-options="width:100">排序（升序)</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/dic/add" width="255" height="495"></div>
<div id="editWindow" url="${ctx}/dic/edit" width="255" height="495"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function(){
        var operationObj = {
            deleteUrl:ctx+'/dic/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["value","label","type","description"]
        };
        var topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    });
</script>
</body>
</html>
