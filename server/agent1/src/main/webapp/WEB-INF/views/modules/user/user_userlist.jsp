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
            <span>客户姓名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="chnName" />
            <span>手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" maxlength="20"/>
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/user/userlist?agentInviteCode=${agentInviteCode}',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="id" align="center" data-options="width:100">客户ID</th>
            <th field="chnName" align="center" data-options="width:100">姓名</th>
            <th field="mobile" align="center" data-options="width:100">手机号</th>
            <th field="money" align="center" data-options="width:100">余额</th>
            <th field="registerTime" align="center" data-options="width:100">注册日期</th>
            <th field="status" align="center" data-options="width:100,
            formatter:function(value,row,index){
                return value == '0' ? '正常' : '停用';
            }
            ">用户状态</th>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/user/add"></div>
<div id="editWindow" url="${ctx}/user/edit"></div>
<div id="infoWindow" url="${ctx}/user/info"></div>
<div id="moneyRecordWindow" url="${ctx}/user/moneyRecord"></div>
<div id="editWindowCommon"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/user/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["chnName","mobile"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })

</script>
</body>
</html>