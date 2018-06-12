<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/18
  Time: 9:53
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
            <%--<span>客户姓名：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="chnName" />--%>
        </label>
    </div>
</div>
<div region="center" style="padding:5px;" border="false">
    <div style="width: 100%;height: 100%;">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;height: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/Ml3Agent/userList?id=${id}',
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
            <th field="registerTime" align="center" data-options="width:130">注册日期</th>
            <th field="status" align="center" data-options="width:100,
            formatter:function(value,row,index){
                return value == '0' ? '正常' : '停用';
            }
            ">用户状态</th>
        </tr>
        </thead>
    </table>
    </div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {};
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["chnName"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
</script>
</body>
</html>
