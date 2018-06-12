<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-11-7
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
            <span>经纪人姓名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="name" />
            <span>经纪人手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />
            <span>会员姓名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="username" />
            <span>会员手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="usermobile" />
            </label>
        <label class="input-inline">
            <span>交 易 金 额 ：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="trademoney" />
            <span>交 易 流 水 号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="serialNo" />
            <span>开始时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="sellTimeMin" />
            <span>结 束 时 间 ：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="sellTimeMax" />
            &nbsp;&nbsp;
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <!-- 筛选条件 结束 -->
    <%--<shiro:hasPermission name="agentmoneychange:add">--%>
        <%--<button id="addInfoBt" class="btn btn-info margin-r10">添加</button>--%>
    <%--</shiro:hasPermission>--%>
    <%--<shiro:hasPermission name="agentmoneychange:edit">--%>
        <%--<button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>--%>
    <%--</shiro:hasPermission>--%>
    <%--<shiro:hasPermission name="agentmoneychange:delete">--%>
        <%--<button id="deleteBt" class="btn btn-info margin-r10">删除</button>--%>
    <%--</shiro:hasPermission>--%>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/agentmoneychange/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="name" align="center" data-options="width:100">经纪人姓名</th>
            <th field="mobile" align="center" data-options="width:100">经纪人手机号</th>
            <th field="username" align="center" data-options="width:100">会员用户姓名</th>
            <th field="serialNo" align="center" data-options="width:100">交易流水号</th>
            <th field="trademoney" align="center" data-options="width:100">交易金额</th>
            <th field="fee" align="center" data-options="width:100">手续费</th>
            <th field="difMoney" align="center" data-options="width:100">返佣金额</th>
            <th field="selltime" align="center" data-options="width:100">交割时间</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/agentmoneychange/add"></div>
<div id="editWindow" url="${ctx}/agentmoneychange/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/agentmoneychange/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["name","mobile","username","usermobile","trademoney","serialNo","sellTimeMin","sellTimeMax"]
        };
        var topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
</script>
</body>
</html>