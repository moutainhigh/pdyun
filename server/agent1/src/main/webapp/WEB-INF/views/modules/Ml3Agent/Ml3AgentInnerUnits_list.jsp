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
            <span>合伙人手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />
            &nbsp;&nbsp;&nbsp;&nbsp;
            <span>合伙人账号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="account" />
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <button class="btn btn-info margin-r10" onclick="updateUser()">编辑</button>
    <button id="editInfoBt" class="btn btn-info margin-r10">修改密码</button>
    <button onclick="_past0()" class="btn btn-info margin-r10">添加合伙人</button>
    <button class="btn btn-info margin-r10" onclick="_past1(0)">关闭合伙人</button>
    <%--<button class="btn btn-info margin-r10" onclick="_past1(1)">开启合伙人</button>--%>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/Ml3Agent/ml3agentinnerunits?id=${id}',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">
        <thead>
        <tr>
            <th field="account" align="center" data-options="width:100">账号</th>
            <th field="mobile" align="center" data-options="width:100">手机号</th>
            <th field="totalRechargeMoney" align="center" data-options="width:100">累计充值资金</th>
            <th field="totalTradeCount" align="center" data-options="width:100">累计交易总量</th>
            <th field="totalCount" align="center" data-options="width:100">累计会员总数</th>
            <th field="agentReturnFeeMoney" align="center" data-options="
                formatter: function(value, row, index){
                    return value != 0 ? (value + '元') : value;
                }
            ">返合伙人手续费余额</th>
            <th field="agentReturnFeeTotalMoney" align="center" data-options="
            formatter: function(value, row, index){
            return value != 0 ? (value + '元') : value;
            }
            ">返合伙人手续费累计金额</th>
            <th field="agentReturnFeePercent" align="center" data-options="
                formatter: function(value, row, index){
                    return value != 0 ? (value + '%') : value;
                }
            ">返合伙人手续费比例</th>
            <th field="agentReturnServiceMoney" align="center" data-options="
                formatter: function(value, row, index){
                    return value != 0 ? (value + '元') : value;
                }
            ">返合伙人服务费余额</th>
            <th field="agentReturnServiceTotalMoney" align="center" data-options="
            formatter: function(value, row, index){
            return value != 0 ? (value + '元') : value;
            }
            ">返合伙人服务费累计金额</th>
            <th field="agentReturnServicePercent" align="center" data-options="
                formatter: function(value, row, index){
                    return value != 0 ? (value + '%') : value;
                }
            ">返合伙人服务费比例</th>
            <th field="agentInviteCode" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value;
                }
            ">唯一邀请码</th>
            <th field="status" align="center" data-options="width:100,
            formatter:function(value,row,index){
                return value == '0' ? '正常' : '停用';
            }
            ">合伙人状态</th>
            <th field="subTimes" align="center" data-options="width:100">剩余认购次数</th>
            <th field="createTime" align="center" data-options="width:100">注册时间</th>
            <th field="lastLoginTime" align="center" data-options="width:100">最近登录时间</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addMl3AgentWindow" url="${ctx}/user/insertMl3Agent"></div>
<div id="editWindow" url="${ctx}/Ml3Agent/editMl3AgentPwd" width="300" height="230"></div>
<div id="getJunTuanWindow" url="${ctx}/user/getJunTuanList"></div>
<div id="insertMl3AgentWindow" url="${ctx}/user/insertMl3Agent"></div>
<div id="updateMl3AgentInfo" url="${ctx}/Ml3Agent/updateMl3AgentInfo"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/user/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["mobile", "account"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function getJunTuanList(id){
        openCenterWindow("军团成员记录", 'getJunTuanWindow', 'id='+id);
    }
    function detail(id){
        openCenterWindow("审核记录", 'getAgentReviewWindow', 'id='+id);
    }
    //    function _operation_html(value,row,index){
    //        return '<a href="javascript:getAgentUser(\''+row.id+'\');">军团成员记录</a>'+'&nbsp;&nbsp;<a href="javascript:detail(\''+row.id+'\');">审核记录</a>';
    //    }
    function _operation_html(value,row,index){
        return '<a href="javascript:getJunTuanList(\''+row.id+'\');">军团成员记录</a>';
    }

    //    function insertMl3Agent(id){
    //        openCenterWindow("添加合伙人", 'insertMl3AgentWindow', 'id='+id);
    //    }
    //关闭合伙人
    function _past1(s){
        var row = $('#listDataGrid').datagrid('getSelected');
        if(!row){
            layer.msg('请选择', {
                offset: getLayerCenter()
            });
            return;
        }
        var str = s == '1' ? '确认关闭该合伙人？' : '确认关闭该合伙人?';
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

    function updateUser(){
        var row = $('#listDataGrid').datagrid('getSelected');
        if(!row){
            layer.msg('请选择', {
                offset: getLayerCenter()
            });
            return;
        }
        openWindow("编辑", 'updateMl3AgentInfo','id='+row.id,800,500);
    }

    //开启合伙人
    function _past0(){
        openWindow("添加合伙人", 'insertMl3AgentWindow',null,1200,500);
                <%--$.ajax({--%>
                    <%--url:'${ctx}/Ml3Agent/' + (s == '1' ? 'open' : 'close'),--%>
                    <%--data: {id: row.id},--%>
                    <%--type: 'POST',--%>
                    <%--success: function(result){--%>
                        <%--if (result.code == 0) {--%>
                            <%--showSuccess();--%>
                            <%--$('#listDataGrid').datagrid('reload');--%>
                        <%--} else {--%>
                            <%--layer.msg(result.msg, {--%>
                                <%--offset: getLayerCenter()--%>
                            <%--});--%>
                        <%--}--%>
                    <%--}--%>
                <%--});--%>
            <%----%>
    }

    /*平移客户*/
    function migrateUser(){
        //判断哪些checkbox被选择
        var rows = $("#listDataGrid").datagrid("getChecked");
        if(0 == rows.length){
            layer.msg('请选择修改代理');
            return;
        }
        var ids = [];
        for(var  i = 0; i < rows.length; i++){
            ids.push(rows[i].id);
        }
        layer.open({
            type:　2,
            title: '修改代理返佣比例',
            skin: 'layui-layer-rim', //加上边框
            area: ['600px', '600px'], //宽高
            content: ctx + '/Ml3Agent/updateAgent?ids=' + ids.join(','),
            btn: ['关闭'],
            yes: function () {
                layer.closeAll();
                $("#listDataGrid").datagrid("reload");
            }
        })
    }
</script>
</body>
</html>