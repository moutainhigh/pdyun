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
        <label class="input-inline">
            <span>姓名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="realName" />
            <span>手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <!-- 筛选条件 结束 -->
    <%--<shiro:hasPermission name="agent:add">--%>
        <%--<button id="addInfoBt" class="btn btn-info margin-r10">添加</button>--%>
    <%--</shiro:hasPermission>--%>
    <shiro:hasPermission name="agent:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">修改密码</button>
    </shiro:hasPermission>
    <button onclick="_past1(1)" class="btn btn-info margin-r10">开启经纪人</button>
    <button onclick="_past1(0)" class="btn btn-info margin-r10">停用经纪人</button>
    <shiro:hasPermission name="agent:out:past">
        <button onclick="_past(1)" class="btn btn-info margin-r10">审核通过</button>
        <button onclick="_past(2)" class="btn btn-info margin-r10">审核不通过</button>
    </shiro:hasPermission>
    <%--<shiro:hasPermission name="agent:delete">--%>
    <%--<button id="deleteBt" class="btn btn-info margin-r10">删除</button>--%>
    <%--</shiro:hasPermission>--%>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/agent/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>

            <th field="agentHeader" align="center" data-options="width:100,
            formatter:function(value,row,index){
                if(value!=null){
                        return '<img src='+value+' width=\'50\'>';
                }
                }
            ">头像</th>
            <th field="realName" align="center" data-options="width:100">真实姓名</th>
            <th field="mobile" align="center" data-options="width:100">手机号</th>
            <th field="money" align="center" data-options="width:100">资金</th>
            <th field="totalMoney" align="center" data-options="width:100">累积资金</th>
            <th field="agentInviteCode" align="center" data-options="width:100">唯一邀请码</th>
            <th field="count" align="center" data-options="width:100">邀请用户总数</th>
            <th field="safePassword" align="center" data-options="width:100">安全密码</th>
            <th field="status" align="center" data-options="width:100,
            formatter:function(value,row,index){
                return value == '0' ? '正常' : '停用';
            }
            ">状态</th>
            <th field="reviewStatus" align="center" data-options="width:100,
            formatter:function(value,row,index){
                return value == '0' ? '审核中' : value == '1' ? '通过':'未通过';
            }
            ">审核状态</th>
            <th field="registerTime" align="center" data-options="width:100">注册时间</th>
            <th field="lastLoginTime" align="center" data-options="width:100">最近登录时间</th>
            <th field="xx" align="center" data-options="width:100,
            formatter:_operation_html
            ">操作</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/agent/add"></div>
<div id="editWindow" url="${ctx}/agent/edit"></div>
<div id="getAgentUserWindow" url="${ctx}/agent/getAgentUser"></div>
<div id="getAgentReviewWindow" url="${ctx}/agent/getAgentReview"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/agent/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["realName","mobile"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function getAgentUser(id){
        openCenterWindow("邀请用户记录", 'getAgentUserWindow', 'id='+id);
    }
    function detail(id){
        openCenterWindow("审核记录", 'getAgentReviewWindow', 'id='+id);
    }
    function _operation_html(value,row,index){
        return '<a href="javascript:getAgentUser(\''+row.id+'\');">邀请用户记录</a>'+'&nbsp;&nbsp;<a href="javascript:detail(\''+row.id+'\');">审核记录</a>';
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
                        url:'${ctx}/agent/out/pastin',
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
                        url:'${ctx}/agent/out/past',
                        data: {id: row.id, s: s ,failureReason: r},
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

    function _past1(s){
        var row = $('#listDataGrid').datagrid('getSelected');
        if(!row){
            layer.msg('请选择', {
                offset: getLayerCenter()
            });
            return;
        }
        var str = s == '1' ? '确认开启该用户？' : '确认停用该用户？';
        $.messager.confirm('确认', str, function (r) {
            if(r){
                $.ajax({
                    url:'${ctx}/agent/' + (s == '1' ? 'open' : 'close'),
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