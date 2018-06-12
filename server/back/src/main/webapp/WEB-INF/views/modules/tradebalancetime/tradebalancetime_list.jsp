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
    <%--<div>--%>
        <%--<label class="input-inline">--%>
            <%--<span>名称：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="name" />--%>
            <%--<button id="queryListBt" class="btn btn-info margin-r10">查询</button>--%>
            <%--<button id="clearBarBt" class="btn btn-info margin-r10">清空</button>--%>
        <%--</label>--%>
    <%--</div>--%>
    <!-- 筛选条件 结束 -->
    <%--<shiro:hasPermission name="tradebalancetime:add">--%>
        <%--<button id="addInfoBt" class="btn btn-info margin-r10">添加</button>--%>
    <%--</shiro:hasPermission>--%>
    <shiro:hasPermission name="tradebalancetime:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <%--<shiro:hasPermission name="tradebalancetime:delete">--%>
        <%--<button id="deleteBt" class="btn btn-info margin-r10">删除</button>--%>
    <%--</shiro:hasPermission>--%>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/tradebalancetime/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="id" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value=='trade' ? '交易时间':'结算时间'
                }

            ">设置类型</th>
            <th field="openTime" align="center" data-options="width:100">开始时间</th>
            <th field="closeTime" align="center" data-options="width:100">结束时间</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/tradebalancetime/add"></div>
<div id="editWindow" url="${ctx}/tradebalancetime/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/tradebalancetime/delete'
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