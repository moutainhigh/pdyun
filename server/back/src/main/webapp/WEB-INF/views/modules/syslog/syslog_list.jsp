<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-10-21
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
    <%--<div>--%>
        <%--<label class="input-inline">--%>
            <%--<span>名称：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="name" />--%>
            <%--<button id="queryListBt" class="btn btn-info margin-r10">查询</button>--%>
            <%--<button id="clearBarBt" class="btn btn-info margin-r10">清空</button>--%>
        <%--</label>--%>
    <%--</div>--%>
    <!-- 筛选条件 结束 -->
    <%--<shiro:hasPermission name="syslog:add">--%>
        <%--<button id="addInfoBt" class="btn btn-info margin-r10">添加</button>--%>
    <%--</shiro:hasPermission>--%>
    <%--<shiro:hasPermission name="syslog:edit">--%>
        <%--<button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>--%>
    <%--</shiro:hasPermission>--%>
    <%--<shiro:hasPermission name="syslog:delete">--%>
        <%--<button id="deleteBt" class="btn btn-info margin-r10">删除</button>--%>
    <%--</shiro:hasPermission>--%>
    <button class="btn btn-info margin-r10" onclick="exportExcel()">导出</button>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/syslog/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="mId" align="center" data-options="width:100">用户ID</th>
            <th field="loginName" align="center" data-options="width:100">用户登录名</th>
            <th field="lContent" align="center" data-options="width:100">日志内容</th>
            <th field="lType" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    if(value == 0){
                        return '无';
                    }
                }
            ">类型</th>
            <th field="lCreateTime" align="center" data-options="width:100">时间</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/syslog/add"></div>
<div id="editWindow" url="${ctx}/syslog/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/syslog/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["name"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function exportExcel(){
        window.location.href = '${ctx}/syslog/exportExcel?' + $.param(topSearchBar.getQueryDatas());
    }
</script>
</body>
</html>