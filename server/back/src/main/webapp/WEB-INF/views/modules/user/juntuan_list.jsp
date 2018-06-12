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
            <%--<span>军团长姓名：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="chnName" />--%>
            <span>手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />
            <span>排序列表：</span>
            <select id="orderKey" class="form-control span1" style="width: 10%; display: inline-block">
                <option value="">--请选择--</option>
                <option id="totalCount" value="totalCount">军团会员总数</option>
                <option id="totalMoney" value="totalMoney">累计充值资金</option>
                <option id="totalTradeCount" value="totalTradeCount">有效交易流水</option>
                <option id="registerTime" value="registerTime">注册时间</option>
            </select>
            <span style="display: inline-block">排序方式：</span>
            <select id="orderValue" class="form-control span1 zheng" style="width: 10%; display: inline-block">
                <option value="">--请选择--</option>
                <option id="asc" value="asc"  style="display: inline-block">正序</option>
                <option id="desc" value="desc" style="display: inline-block">倒序</option>
            </select>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <%--<shiro:hasPermission name="user:editjuntuanPwd">--%>
        <%--<button id="editInfoBt" class="btn btn-info margin-r10">修改密码</button>--%>
    <%--</shiro:hasPermission>--%>
    <%--<button class="btn btn-info margin-r10" onclick="_past1(1)">开启合伙人</button>--%>
    <%--<button onclick="_past1(1)" class="btn btn-info margin-r10">开启合伙人</button>--%>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/user/juntuanlist',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>

            <%--<th field="userHeader" align="center" data-options="width:100,--%>
            <%--formatter:function(value,row,index){--%>
                <%--if(value!=null){--%>
                        <%--return '<img src='+value+' width=\'50\'>';--%>
                <%--}--%>
                <%--}--%>
            <%--">头像</th>--%>
            <th field="unitsName" align="center" data-options="width:100">所属VIP合伙人</th>
            <th field="agentRealName" align="center" data-options="width:100">所属合伙人</th>
            <th field="mobile" align="center" data-options="width:100">手机号</th>
            <th field="totalCount" align="center" data-options="width:100">军团会员总数</th>
            <th field="totalMoney" align="center" data-options="width:100">累计充值资金</th>
            <th field="totalTradeCount" align="center" data-options="width:100">有效交易流水</th>
            <%--<th field="agentInviteCode" align="center" data-options="width:100">唯一邀请码</th>--%>
            <th field="status" align="center" data-options="width:100,
            formatter:function(value,row,index){
                return value == '0' ? '正常' : '停用';
            }
            ">状态</th>
            <th field="registerTime" align="center" data-options="width:100">注册时间</th>
            <th field="lastLoginTime" align="center" data-options="width:100">最近登录时间</th>
            <th field="xx" align="center" data-options="width:100,
            formatter:_operation_html
            ">操作</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addMl3AgentWindow" url="${ctx}/user/insertMl3Agent"></div>
<div id="editWindow" url="${ctx}/user/editjuntuanPwd" width="300" height="230"></div>
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
            queryColumns: ["chnName","mobile","orderKey","orderValue"]
        };
        console.log(toolBarInitObj);
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
    function _past1(s){
        var row = $('#listDataGrid').datagrid('getSelected');
        if(!row){
            layer.msg('请选择', {
                offset: getLayerCenter()
            });
            return;
        }
        var str = s == '1' ? '确认开启该军团长为合伙人？' : '';
        $.messager.confirm('确认', str, function (r) {
            if(r){
                openWindow("开启合伙人", 'addMl3AgentWindow', 'id='+row.id ,width="300",height="500");
                <%--window.location.href='${ctx}/user/insertMl3Agent?id='+row.id,width='300',height='500';--%>
            }
        });
    }
</script>
</body>
</html>