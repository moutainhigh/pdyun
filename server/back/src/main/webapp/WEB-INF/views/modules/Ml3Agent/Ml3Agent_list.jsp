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
    <%--<div>--%>
        <%--<label class="input-inline">--%>
            <%--<span>名称：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="name" />--%>
            <%--<button id="queryListBt" class="btn btn-info margin-r10">查询</button>--%>
            <%--<button id="clearBarBt" class="btn btn-info margin-r10">清空</button>--%>
        <%--</label>--%>
    <%--</div>--%>
    <!-- 筛选条件 结束 -->
    <button onclick="_past(1)" class="btn btn-info margin-r10">启用</button>
    <button onclick="_past(0)" class="btn btn-info margin-r10">停用</button>
    <shiro:hasPermission name="Ml3Agent:add">
        <button id="addInfoBt" class="btn btn-info margin-r10">添加</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="Ml3Agent:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <%--<shiro:hasPermission name="Ml3Agent:delete">--%>
        <%--<button id="deleteBt" class="btn btn-info margin-r10">删除</button>--%>
    <%--</shiro:hasPermission>--%>
    <%--<button id="UnitsBt" class="btn btn-info margin-r10">VIP合伙人用户</button>--%>
    <%--<button id="ocBt" class="btn btn-info margin-r10">市场管理部用户</button>--%>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/Ml3Agent/list',
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
            <th field="unitsName" align="center" data-options="width:100">所属VIP合伙人名称</th>
            <th field="roleType" align="center" data-options="width:100,
                formatter:function(value,index,row){
                    return value == 0?'合伙人':value == 1?'VIP合伙人用户':'市场管理部用户';
                }
            ">角色</th>
            <th field="account" align="center" data-options="width:100">账号</th>
            <th field="mobile" align="center" data-options="width:100">手机号</th>
            <%--<th field="safePassword" align="center" data-options="width:100">密码</th>--%>
            <th field="createTime" align="center" data-options="width:100">注册时间</th>
            <th field="lastLoginTime" align="center" data-options="width:100">上次登录时间</th>
            <th field="lastLoginIp" align="center" data-options="width:100">上次登录IP</th>
            <th field="status" align="center" data-options="width:100,
                formatter:function(value,index,row){
                    return value == 0?'正常':'停用';
                }
            ">状态</th>
            <th field="xx" align="center" data-options="width:100,
            formatter:_operation_html
            ">操作</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/Ml3Agent/add"></div>
<div id="editWindow" url="${ctx}/Ml3Agent/edit"></div>
<div id="unitsWindow" url="${ctx}/Ml3Agent/units" ></div>
<div id="ocWindow" url="${ctx}/Ml3Agent/oc" ></div>
<div id="updPwdWindow" url="${ctx}/Ml3Agent/updPwd"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/Ml3Agent/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["mobile"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);

        $("#UnitsBt").on("click", function () {
                openFullWindow("VIP合伙人用户", "unitsWindow");
        });

        $("#ocBt").on("click", function () {
            openFullWindow("市场管理部用户", "ocWindow");
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
        var str = s == '1' ? '确认启用该用户？' : '确认停用该用户？';
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
    function detail(id){
        openWindow("修改密码", 'updPwdWindow', 'id='+id, 300, 200);
    }
    function _operation_html(value,row,index){
        return '<a href="javascript:detail(\''+row.id+'\');">修改密码</a>';
    }
</script>
</body>
</html>