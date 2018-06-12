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
            <span>开始时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="createTimeMin" />
            <span>结束时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="createTimeMax" />
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
</div>
<div region="center" style="padding:5px;" border="false">
    <div style="width: 100%;height: 100%;">
        <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;height: 100%;" singleSelect="true" pagination="true"
               data-options="
	                url: '${ctx}/user/moneyRecord?id=${id}',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

            <thead>
            <tr>
                <th field="serialNo" align="center" data-options="width:255">单号</th>
                <%--<th field="userId" align="center" data-options="width:100">申请用户ID</th>--%>
                <th field="chnName" align="center" data-options="width:100">姓名</th>
                <%--<th field="mobile" align="center" data-options="width:130">手机号</th>--%>
                <th field="channel" align="center" data-options="width:100">充值渠道</th>
                <th field="bankName" align="center" data-options="width:100">银行名称</th>
                <th field="bankAccount" align="center" data-options="width:100">银行卡号</th>
                <th field="money" align="center" data-options="width:100">金额</th>
                <th field="type" align="center" data-options="width:100,
                    formatter: function(value,row,index){
                        return value == '0' ? '充值' : '提现';
                    }
                ">类型</th>
                <th field="createTime" align="center" data-options="width:150">申请时间</th>
                <th field="status" align="center" data-options="width:80,
                    formatter: function(value,row,index){
                        if(row.type == 0){
                            return value == '0' ? '进行中' : (value == '1' ? '通过' : '未通过');
                        }else{
                            return value == '0' ? '未审核' : (value == '1' ? '通过' : '未通过');
                        }
                    }
                ">状态</th>
                <%--<th field="tAdmin" align="center" data-options="width:100">处理人</th>--%>
                <th field="completeTime" align="center" data-options="width:160">处理成功时间</th>
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
            queryColumns: ["createTimeMin", "createTimeMax"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
</script>
</body>
</html>
