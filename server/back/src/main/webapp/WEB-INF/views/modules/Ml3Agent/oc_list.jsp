<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/17
  Time: 16:38
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
    <button onclick="_past(1)" class="btn btn-info margin-r10">启用</button>
    <button onclick="_past(0)" class="btn btn-info margin-r10">停用</button>
    <shiro:hasPermission name="Ml3Agent:addOc">
        <button id="addInfoBt" class="btn btn-info margin-r10">添加</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="Ml3Agent:editOc">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="Ml3Agent:deleteUnits">
        <button id="deleteBt" class="btn btn-info margin-r10">删除</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/Ml3Agent/oc',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="centerName" align="center" data-options="width:100">所属市场管理部</th>
            <th field="account" align="center" data-options="width:100">账号</th>
            <th field="mobile" align="center" data-options="width:100">手机号</th>
            <th field="safePassword" align="center" data-options="width:100">密码</th>
            <th field="createTime" align="center" data-options="width:100">注册时间</th>
            <th field="lastLoginTime" align="center" data-options="width:100">上次登录时间</th>
            <th field="lastLoginIp" align="center" data-options="width:100">上次登录IP</th>
            <th field="status" align="center" data-options="width:100,
                formatter:function(value,index,row){
                    return value == 0?'正常':'停用';
                }
            ">状态</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/Ml3Agent/addOc"></div>
<div id="editWindow" url="${ctx}/Ml3Agent/editOc"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/Ml3Agent/deleteUnits'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["mobile","chnName","uMoneyMin","uMoneyMax"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);

        $("#addInfoBt").on("click", function () {
            openFullWindow("添加", "addWindow");
        });
        $("#editInfoBt").on("click", function () {
            var row = $('#listDataGrid').datagrid('getSelected');
            if(!row){
                layer.msg('请选择', {
                    offset: getLayerCenter()
                });
                return;
            }
            openFullWindow("编辑", "editWindow","id="+row.id);
        });
    })


    function _past(s){
        var row = $('#listDataGrid').datagrid('getSelected');
        if(!row){
            layer.msg('请选择', {
                offset: getLayerCenter()
            });
            return;
        }
        var str = s == '1' ? '确认启用该市场管理部用户？' : '确认停用该市场管理部用户？';
        $.messager.confirm('确认', str, function (r) {
            if(r){
                $.ajax({
                    url:'${ctx}/Ml3Agent/' + (s == '1' ? 'open' : 'close'),
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
