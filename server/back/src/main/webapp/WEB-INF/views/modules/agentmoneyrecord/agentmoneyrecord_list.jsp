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
    <%--<div>--%>
        <%--<label class="input-inline">--%>
            <%--<span>名称：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="name" />--%>
            <%--<button id="queryListBt" class="btn btn-info margin-r10">查询</button>--%>
            <%--<button id="clearBarBt" class="btn btn-info margin-r10">清空</button>--%>
        <%--</label>--%>
    <%--</div>--%>
    <!-- 筛选条件 结束 -->
    <%--<shiro:hasPermission name="agentmoneyrecord:add">--%>
        <%--<button id="addInfoBt" class="btn btn-info margin-r10">添加</button>--%>
    <%--</shiro:hasPermission>--%>
    <%--<shiro:hasPermission name="agentmoneyrecord:edit">--%>
        <%--<button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>--%>
    <%--</shiro:hasPermission>--%>
    <%--<shiro:hasPermission name="agentmoneyrecord:delete">--%>
        <%--<button id="deleteBt" class="btn btn-info margin-r10">删除</button>--%>
    <%--</shiro:hasPermission>--%>
    <shiro:hasPermission name="agentmoneyrecord:out:past">
        <button onclick="_past(1)" class="btn btn-info margin-r10">审核通过</button>
        <button onclick="_past(2)" class="btn btn-info margin-r10">审核不通过</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/agentmoneyrecord/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="serialNo" align="center" data-options="width:100">流水号</th>
            <th field="name" align="center" data-options="width:100">经纪人姓名</th>
            <th field="money" align="center" data-options="width:100">金额</th>
            <th field="fee" align="center" data-options="width:100">手续费</th>
            <th field="thirdSerialNo" align="center" data-options="width:100">第三方流水号</th>
            <th field="status" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value == '0' ? '处理中' : value == '1' ? '成功':'失败';
                    }
           ">状态</th>
            <th field="createTime" align="center" data-options="width:100">创建时间</th>
            <th field="completeTime" align="center" data-options="width:100">完成时间</th>
            <th field="remark" align="center" data-options="width:100">备注</th>
            <th field="xx" align="center" data-options="width:100,
            formatter:_operation_html
            ">操作</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/agentmoneyrecord/add"></div>
<div id="editWindow" url="${ctx}/agentmoneyrecord/edit"></div>
<div id="infoWindow" url="${ctx}/agentmoneyrecord/info"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/agentmoneyrecord/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["name"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function detail(id){
        openWindow("详细信息", 'infoWindow', 'id='+id, 400, 300);
    }
    function _operation_html(value,row,index) {
        return '<a href="javascript:detail(\'' + row.id + '\');">详细信息</a>';
    }



        function _past(s){
            var row = $('#listDataGrid').datagrid('getSelected');
            if(!row){
                layer.msg('请选择', {
                    offset: getLayerCenter()
                });
                return;
            }
            var str = s == '1' ? '确认通过审核？' : '确认审核不通过？';
            if(s == '1'){
                $.messager.confirm('确认', str, function (r) {
                    if(r){
                        $.ajax({
                            url:'${ctx}/agentmoneyrecord/out/pastin',
                            data: {id: row.id, s: s},
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
            }else {
                $.messager.prompt('确认', str, function (r) {
                    if(typeof r != 'undefined'){
                        if(r == null || r.replace(/ /g, '') == ''){
                            alert('请填写原因！');
                            return;
                        }
                        $.ajax({
                            url:'${ctx}/agentmoneyrecord/out/past',
                            data: {id: row.id, s: s, failureReason: r},
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
        }
</script>
</body>
</html>