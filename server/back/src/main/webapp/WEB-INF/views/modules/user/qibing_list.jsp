<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-11-4
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
        <label class="input-inline juntuan_phone">
            <span>军团长姓名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="juntuanChnName" />
            <span>合伙人：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="juntuanMobile" />
            <span>排序列表：</span>
            <select id="orderKey" class="form-control span1" style="width: 10%; display: inline-block">
                <option id="money" value="money">余额</option>
                <option id="rechargeMoney" value="rechargeMoney">累计充值资金</option>
                <option id="tradeCount" value="tradeCount">累计交易总量</option>
                <option id="registerTime" value="registerTime">注册时间</option>
            </select>
            <span style="display: inline-block">排序方式：</span>
            <select id="orderValue" class="form-control span1 zheng" style="width: 10%; display: inline-block">
                <option id="asc" value="asc"  style="display: inline-block">正序</option>
                <option id="desc" value="desc" style="display: inline-block">倒序</option>
            </select>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <shiro:hasPermission name="user:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">修改信息</button>
    </shiro:hasPermission>
    <%--<button class="btn btn-info margin-r10" onclick="_past1(1)">开启合伙人</button>--%>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/user/qibinglist',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="juntuanChnName" align="center" data-options="width:100">所属军团长</th>
            <th field="userHeader" align="center" data-options="width:100,
            formatter:function(value,row,index){
                if(value!=null){
                        return '<img src='+value+' width=\'50\'>';
                }
                }
            ">用户头像</th>
            <th field="chnName" align="center" data-options="width:100">用户姓名</th>
            <th field="jType" align="center" data-options="width:100">级别</th>
            <th field="mobile" align="center" data-options="width:100">手机号</th>
            <th field="money" align="center" data-options="width:100">余额</th>
            <th field="rechargeMoney" align="center" data-options="width:100">累计充值资金</th>
            <th field="tradeCount" align="center" data-options="width:100">累计交易总量</th>
            <th field="status" align="center" data-options="width:100,
            formatter:function(value,row,index){
                return value == '0' ? '正常' : '停用';
            }
            ">用户状态</th>
            <th field="registerTime" align="center" data-options="width:100">注册时间</th>
            <th field="lastLoginTime" align="center" data-options="width:100">最近登录时间</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addMl3AgentWindow" url="${ctx}/user/insertMl3Agent"></div>
<div id="editWindow" url="${ctx}/user/edit"></div>
<div id="getJunTuanWindow" url="${ctx}/user/getJunTuanList"></div>
<%--<div id="insertMl3AgentWindow" url="${ctx}/user/insertMl3Agent"></div>--%>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/user/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["juntuanChnName","juntuanMobile","orderKey","orderValue"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })

</script>
</body>
</html>