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
    <!-- 查询 -->
    <div>
        <label class="input-inline">
            <span>VIP合伙人名称：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="name" />
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <!-- 筛选条件 结束 -->
    <button onclick="_past(1)" class="btn btn-info margin-r10">启用</button>
    <button onclick="_past(0)" class="btn btn-info margin-r10">停用</button>
    <shiro:hasPermission name="Ml3MemberUnits:add">
        <button id="addInfoBt" class="btn btn-info margin-r10">添加</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="Ml3MemberUnits:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <%--<shiro:hasPermission name="Ml3MemberUnits:delete">--%>
        <%--<button id="deleteBt" class="btn btn-info margin-r10">删除</button>--%>
    <%--</shiro:hasPermission>--%>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/Ml3MemberUnits/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="centerName" align="center" data-options="width:100">所属市场管理部名称</th>
            <th field="name" align="center" data-options="width:100">VIP合伙人名称</th>
            <th field="bondMoney" align="center" data-options="width:100">保证金</th>
            <th field="realName" align="center" data-options="width:100">真实姓名</th>
            <th field="idCard" align="center" data-options="width:100">身份证</th>
            <th field="bankAccountName" align="center" data-options="width:100">银行开户姓名</th>
            <th field="bankAccount" align="center" data-options="width:100">银行账号</th>
            <th field="bankName" align="center" data-options="width:100">开户行</th>
            <th field="bankChildName" align="center" data-options="width:100">开户银行支行名</th>
            <th field="status" align="center" data-options="width:100,
                formatter:function(value,index,row){
                    return value == 0?'正常':'停用';
                }
            ">状态</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/Ml3MemberUnits/add"></div>
<div id="editWindow" url="${ctx}/Ml3MemberUnits/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/Ml3MemberUnits/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["name"]
        };
        var topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function _past(s){
        var row = $('#listDataGrid').datagrid('getSelected');
        if(!row){
            layer.msg('请选择', {
                offset: getLayerCenter()
            });
            return;
        }
        var str = s == '1' ? '确认开启该VIP合伙人？' : '确认停用该VIP合伙人？';
        $.messager.confirm('确认', str, function (r) {
            if(r){
                $.ajax({
                    url:'${ctx}/Ml3MemberUnits/' + (s == '1' ? 'open' : 'close'),
                    data: {id: row.id},
                    type: 'POST',
                    success: function(result){
                        if (result.code == 0) {
                            showSuccess();
                            $('#listDataGrid').datagrid('reload');
                        } else {
                            layer.msg(result.msg, {
                                offset: getLayerCenter()
                            });
                        }
                    }
                });
            }
        });
    }
</script>
</body>
</html>