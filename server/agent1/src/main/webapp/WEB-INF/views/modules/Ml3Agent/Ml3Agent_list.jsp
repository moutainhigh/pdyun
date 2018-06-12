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
            <span>代理名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="realName" />
            <span>手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <!-- 筛选条件 结束 -->
        <button id="addInfoBt" class="btn btn-info margin-r10">添加代理</button>
    <shiro:hasPermission name="Ml3Agent:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
        <%--<button id="deleteBt" class="btn btn-info margin-r10">删除</button>--%>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/Ml3Agent/list?id=${id}',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="id" align="center" data-options="width:100">代理id</th>
            <th field="account" align="center" data-options="width:100">账号</th>
            <th field="realName" align="center" data-options="width:100">代理姓名</th>
            <th field="mobile" align="center" data-options="width:100">手机号</th>
            <th field="status" align="center" data-options="width:100,
                 formatter:function(value,row,index){
                return value == '0' ? '正常' : '停用';
            }
            ">状态</th>
            <th field="xx" align="center" data-options="width:100,
            formatter:_operation_html
            ">操作</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/Ml3Agent/add" width="300" height="400"></div>
<div id="editWindow" url="${ctx}/Ml3Agent/edit"></div>
<div id="updatePwdWindow" url="${ctx}/Ml3Agent/updatePwd"></div>
<div id="userListWindow" url="${ctx}/Ml3Agent/userList"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/Ml3Agent/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["realName",",mobile"]
        };

        var topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function updatePwd(id){
        openWindow("修改密码", 'updatePwdWindow', 'id='+id, 325, 230);
    }
    function userList(id){
        openCenterWindow("客户列表", 'userListWindow', 'id='+id);
    }
    function _operation_html(value,row,index){
        return '<a href="javascript:_past(1);">启用</a>  '+'<a href="javascript:_past(0);">停用</a>  '+'<a href="javascript:updatePwd(\''+row.id+'\');">修改密码</a>  '+'    <a href="javascript:userList(\''+row.id+'\');">查看客户列表</a>';
    }
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
</script>
</body>
</html>