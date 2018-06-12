<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-10-17
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body class="">
<div region="north" style="padding:5px;" border="false">
    <div class="form-group row">
        <div class="col-md-2">
            <span>VIP合伙人：</span>
            <select id="unitsId" style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;">
                <option value="">请选择</option>
                <c:forEach items="${unitsList}" var="units">
                    <option value="${units.id}">${units.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-2">
            <span>合伙人：</span>
            <select id="agentId" style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;">
                <option value="">请选择</option>
            </select>
        </div>
        <div class="col-md-2">
            <span>手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />
        </div>
        <div class="col-md-3">
            <span>余额：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="uMoneyMin" maxlength="20"/>
            <span>~</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="uMoneyMax" maxlength="20"/>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-md-2">
            <span>排序列表：</span>
            <select id="orderKey" style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;">
                <option value="">请选择</option>
                <option id="1" value="money">余额</option>
                <option id="rechargeMoney" value="recharge_money">累计充值资金</option>
                <option id="registerTime" value="register_time">注册时间</option>
            </select>
        </div>
        <div class="col-md-2">
            <span style="display: inline-block">排序方式：</span>
            <select id="orderValue" style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;">
                <option id="asc" value="asc"  style="display: inline-block">正序</option>
                <option id="desc" value="desc" style="display: inline-block">倒序</option>
            </select>
        </div>
        <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
        <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
    </div>

    <!-- 查询 -->
    <%--<div style="overflow: hidden;">--%>
        <%--<label class="input-inline">--%>
            <%--<span>VIP合伙人：</span>--%>
            <%--<select id="unitsId" class="form-control span1" style="width: 25%; display: inline-block">--%>
                <%--<option value="">请选择</option>--%>
                <%--<c:forEach items="${unitsList}" var="units">--%>
                    <%--<option value="${units.id}">${units.name}</option>--%>
                <%--</c:forEach>--%>
            <%--</select>--%>
            <%--<span>合伙人：</span>--%>
            <%--<select id="agentId" class="form-control span1" style="width: 25%; display: inline-block">--%>
                <%--<option value="">请选择</option>--%>
            <%--</select>--%>
        <%--</label>--%>
        <%--<label class="input-inline juntuan_phone">--%>
            <%--<span>手机号：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />--%>
            <%--<span>余额：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="uMoneyMin" maxlength="20"/>--%>
            <%--<span>~</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="uMoneyMax" maxlength="20"/>--%>
            <%--<span>排序列表：</span>--%>
            <%--<select id="orderKey" class="form-control span1" style="width: 10%; display: inline-block">--%>
                <%--<option value="">请选择</option>--%>
                <%--<option id="1" value="money">余额</option>--%>
                <%--<option id="rechargeMoney" value="recharge_money">累计充值资金</option>--%>
                <%--<option id="registerTime" value="register_time">注册时间</option>--%>
            <%--</select>--%>
            <%--<span style="display: inline-block">排序方式：</span>--%>
            <%--<select id="orderValue" class="form-control span1 zheng" style="width: 10%; display: inline-block">--%>
                <%--<option id="asc" value="asc"  style="display: inline-block">正序</option>--%>
                <%--<option id="desc" value="desc" style="display: inline-block">倒序</option>--%>
            <%--</select>--%>
            <%--&nbsp;&nbsp;&nbsp;&nbsp;--%>
            <%--<button id="queryListBt" class="btn btn-info margin-r10">查询</button>--%>
            <%--<button id="clearBarBt" class="btn btn-info margin-r10">清空</button>--%>
            <%--<button class="btn btn-info margin-r10" onclick="exportExcel()">导出</button>--%>
        <%--</label>--%>
    <%--</div>--%>
    <%--    <shiro:hasPermission name="user:edit">
            <button id="editInfoBt" class="btn btn-info margin-r10">修改信息</button>
        </shiro:hasPermission>--%>
    <%--    <shiro:hasPermission name="user:getCash">
            <button class="btn btn-info margin-r10 editWindow" data-url="${ctx}/user/getCash" data-title="线下提现">线下提现</button>
        </shiro:hasPermission>--%>
    <%--<shiro:hasPermission name="user:sendCoupon">--%>
    <%--<button class="btn btn-info margin-r10 editWindow" data-url="${ctx}/user/sendCoupon" data-title="赠送代金券">赠送代金券</button>--%>
    <%--</shiro:hasPermission>--%>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/user/useroperatecenter?id=${id}',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right',
	                loadFilter: _loadFilter
	            ">

        <thead>
        <tr>
            <th field="agentRealName" align="center" data-options="width:100">所属合伙人</th>
            <th field="mobile" align="center" data-options="width:100">手机号</th>
            <th field="chnName" align="center" data-options="width:100">客户姓名</th>
            <%--<th field="tradePassword" align="center" data-options="width:100">交易密码</th>--%>
            <th field="money" align="center" data-options="width:100">余额</th>
            <%--<th field="couponMoney" align="center" data-options="width:100">代金券总额</th>--%>
            <th field="rechargeMoney" align="center" data-options="width:100">累计充值资金</th>
            <%--<th field="tradeCount" align="center" data-options="width:100">累计交易总量</th>--%>
            <th field="status" align="center" data-options="width:100,
            formatter:function(value,row,index){
                return value == '0' ? '正常' : '停用';
            }
            ">用户状态</th>
            <th field="registerTime" align="center" data-options="width:100">注册时间</th>
            <th field="lastLoginTime" align="center" data-options="width:100">最后一次登录时间</th>
            <th field="lastLoginIp" align="center" data-options="width:100">最后一次登录地址</th>
            <th field="xx" align="center" data-options="width:100,
            formatter:_operation_html
            ">操作</th>
        </tr>
        </thead>
    </table><br/>
    <div style="float: right">
        <span style="font-size: 15px;">总余额：</span><label id="totalMoney" style="font-size: 15px;"></label><br/>
        <span style="font-size: 15px;">累计充值总额：</span><label id="totalRechargeMoney" style="font-size: 15px;"></label><br/>
        <span style="font-size: 15px;">累计交易总量：</span><label id="totalTradeCount" style="font-size: 15px;"></label>
    </div>
</div>
<div id="addWindow" url="${ctx}/user/add"></div>
<div id="editWindow" url="${ctx}/user/edit"></div>
<div id="infoWindow" url="${ctx}/user/info"></div>
<div id="moneyRecordWindow" url="${ctx}/user/moneyRecord"></div>
<div id="editWindowCommon"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/user/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["mobile","chnName","uMoneyMin","uMoneyMax","orderKey","orderValue", "unitsId", "agentId"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function exportExcel(){
        window.location.href = '${ctx}/user/exportExcel?' + $.param(topSearchBar.getQueryDatas());
    }
    function detail(id){
        openWindow("详细信息", 'infoWindow', 'id='+id, 500, 300);
    }
    function moneyRecord(id){
        openCenterWindow("充值提现记录", 'moneyRecordWindow', 'id='+id);
    }
    function _operation_html(value,row,index){
        return '<a href="javascript:detail(\''+row.id+'\');">详细信息</a>'+'    <a href="javascript:moneyRecord(\''+row.id+'\');">充值提现记录</a>';
    }
    //    $(function () {
    //        $("#queryListBt").click(function () {
    //            var str = $("#orderKey").val();
    //            alert(str);
    //        })
    //    })

    function _loadFilter(data) {
        console.log(JSON.stringify(data));
        if(data.innerUserTotal){
            var str = data.innerUserTotal.totalMoney;
            var str1 = data.innerUserTotal.totalRechargeMoney;
            var str2 = data.innerUserTotal.totalTradeCount;
            if(str == null || str == ''){
                document.getElementById('totalMoney').innerHTML='0元';
            }else{
                document.getElementById('totalMoney').innerHTML=str+'元';
            }
            if(str1 == null || str1 == ''){
                document.getElementById('totalRechargeMoney').innerHTML='0元';
            }else{
                document.getElementById('totalRechargeMoney').innerHTML=str1+'元';
            }
            if(str2 == null || str2 == ''){
                document.getElementById('totalTradeCount').innerHTML='0元';
            }else{
                document.getElementById('totalTradeCount').innerHTML=str2+'元';
            }
        }else if(data.innerUserTotal == ''){
            document.getElementById('totalMoney').innerHTML='0元';
            document.getElementById('totalRechargeMoney').innerHTML='0元';
            document.getElementById('totalTradeCount').innerHTML='0元';
        }
        return data.g;
    }

    $('select[id="unitsId"]').on("click", function () {
        var unitsId = $(this).val();
        $.ajax({
            url: ctx + '/Ml3Agent/findMl3AgentById',
            type: 'GET',
            data: {unitsId: unitsId},
            success: function (data) {
                console.log(JSON.stringify(data.agentList));
                $("select[id='agentId']").children().remove();
                var option = [];
                option.push('<option value="">请选择</option>')
                for (var i = 0; i < data.agentList.length; i++){
                    var agent = data.agentList[i];
                    option.push('<option value="' + agent.id + '">' + agent.realName + '</option>');
                }
                $("select[id='agentId']").append(option.join(''));
            }
        });
    })
</script>
</body>
</html>