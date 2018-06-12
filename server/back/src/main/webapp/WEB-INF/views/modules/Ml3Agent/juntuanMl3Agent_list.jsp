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
            <span>VIP合伙人：</span>
            <select id="unitsId" class="form-control span1" style="width: 10%; display: inline-block">
                <option value="">请选择</option>
                <c:forEach items="${requestScope.memberUnits}" var="units">
                    <option value="${units.id}">${units.name}</option>
                </c:forEach>
            </select>
            <span>合伙人姓名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="agentRealName" />
            <span>合伙人手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="agentMobile" />
            <span>排序列表：</span>
            <select id="orderKey" class="form-control span1" style="width: 10%; display: inline-block">
                <option id="totalMoney" value="totalMoney">累计充值资金</option>
                <option id="totalTradeCount" value="totalTradeCount">累计交易总量</option>
                <option id="totalCount" value="totalCount">累计会员总数</option>
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
<%--    <shiro:hasPermission name="user:editjuntuanPwd">
    <button id="editInfoBt" class="btn btn-info margin-r10">修改密码</button>
    </shiro:hasPermission>--%>
    <button class="btn btn-info margin-r10" onclick="updateUser()">编辑</button>
    <button class="btn btn-info margin-r10" onclick="updateReturnPercent()">修改合伙人返佣比例</button>
    <button class="btn btn-info margin-r10" onclick="_past1(0)">关闭合伙人</button>
    <button onclick="_past1(1)" class="btn btn-info margin-r10">开启合伙人</button>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/Ml3Agent/agentlist',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right',
	                loadFilter: function(data){
	                    queryTotalUserByUnits();
	                    return data;
	                }
	            ">

        <thead>
        <tr>
            <th field="unitsName" align="center" data-options="width:100">所属VIP合伙人</th>
            <th field="account" align="center" data-options="width:100">账号</th>
            <th field="agentRealName" align="center" data-options="whdth:60">合伙人姓名</th>
            <th field="agentMobile" align="center" data-options="width:100">手机号</th>
            <th field="totalMoney" align="center" data-options="width:100">累计充值资金</th>
            <th field="totalTradeCount" align="center" data-options="width:100">累计交易总量</th>
            <th field="agentReturnFeeMoney" align="center">返手续费余额</th>
            <th field="agentReturnFeeTotalMoney" align="center">返手续费累计金额</th>
            <th field="agentReturnFeePercent" align="center">返手续费比例</th>
            <th field="agentReturnServiceMoney" align="center">返服务费余额</th>
            <th field="agentReturnServiceTotalMoney" align="center">返服务费累计金额</th>
            <th field="agentReturnServicePercent" align="center">返服务费比例</th>
            <th field="agentInviteCode" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value;
                }
            ">唯一邀请码</th>
            <th field="totalCount" align="center" data-options="width:100">累计会员总数</th>
            <th field="agentStatus" align="center" data-options="width:100,
            formatter:function(value,row,index){
                return value == '0' ? '正常' : '停用';
            }
            ">合伙人状态</th>
            <th field="registerTime" align="center" data-options="width:100">注册时间</th>
            <th field="lastLoginTime" align="center" data-options="width:100">最近登录时间</th>
            <%--<th field="xx" align="center" data-options="width:100,--%>
            <%--formatter:_operation_html--%>
            <%--">操作</th>--%>
        </tr>
        </thead>
    </table>
    <br/>
    <div style="float: right">
        <span style="font-size: 15px;">用户总数：</span><label id="totalUser" style="font-size: 15px;"></label><br/>
    </div>
</div>

<div id="addMl3AgentWindow" url="${ctx}/user/insertMl3Agent"></div>
<div id="editWindow" url="${ctx}/Ml3Agent/editMl3AgentPwd" width="300" height="230"></div>
<div id="getJunTuanWindow" url="${ctx}/user/getJunTuanList"></div>
<%--<div id="insertMl3AgentWindow" url="${ctx}/user/insertMl3Agent"></div>--%>
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
            queryColumns: ["unitsId","agentRealName","agentMobile","orderKey","orderValue"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);

        queryTotalUserByUnits();
    })
    
    function queryTotalUserByUnits(){
        $.ajax({
            url: ctx + '/Ml3Agent/agentTotal',
            type: 'POST',
            data: topSearchBar.getQueryDatas(false),
            success: function (data) {
                for(var key in data){
                    $('#' + key).text(data[key]);
                }
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
                    url:'${ctx}/Ml3Agent/' + (s == '1' ? 'open1' : 'close1'),
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
    
    function updateReturnPercent() {
        var rows = $('#listDataGrid').datagrid('getChecked');
        if(rows.length > 1 || rows.length == 0){
            layer.msg('只允许操作一条数据');
            return false;
        }

        layer.open({
            type:　2,
            title: '修改代理返佣比例',
            skin: 'layui-layer-rim', //加上边框
            area: ['600px', '600px'], //宽高
            content: ctx + '/Ml3Agent/updateReturnPercent?id=' + rows[0].id,
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