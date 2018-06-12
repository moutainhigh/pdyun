<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-10-17
To change this template use File | Settings | File Templates.
持仓明细
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>


<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <style type="text/css">
    select{
        width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;
    }
</style>
</head>
<body class="easyui-layout">
<div region="north" style="padding:5px;" border="false">
    <!-- 查询 -->
    <div>
        <label class="input-inline">
            <span>市场管理部：</span>
            <select style="height:35px;" id="centerId">
                <option value="">--请选择--</option>
                <c:forEach var="it" items="${centerList}">
                    <option value="${it.id}">${it.name}</option>
                </c:forEach>
            </select>
            <span>VIP合伙人：</span>
            <select style="height:35px;" id="unitsId">
                <option value="">--请选择--</option>
                <c:forEach var="it" items="${unitsList}">
                    <option value="${it.id}">${it.name}</option>
                </c:forEach>
            </select>
            <span>合伙人：</span>
            <select style="height:35px;" id="agentId">
                <option value="">--请选择--</option>
                <c:forEach var="it" items="${agentList}">
                    <option value="${it.id}">${it.name}</option>
                </c:forEach>
            </select>
            <%--<span>会员姓名：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="uname" />--%>
            <span>会员手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="umobile" />
            <span>合伙人：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />
            <%--<span>代理手机号：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="agentMobile" />--%>
            <span>持仓状态：</span>
            <select style="height:35px;" id="status">
                <option value="1">认购</option>
                <option value="2">挂单</option>
            </select>
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <button class="btn btn-info margin-r10" onclick="transHold()">持仓划转</button>
    <button class="btn btn-info margin-r10" onclick="exportExcel()">导出</button>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/trade/listHold',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right',
	                loadFilter: _loadFilter,
	                frozenColumns:[[
                        {field:'ck',checkbox:true}
                    ]],
	                singleSelect: true,
                    checkOnSelect: false,
                    selectOnCheck: false,
	            ">

        <thead>
        <tr>
            <th field="serialNo" align="center" data-options="width:100">流水号</th>
            <th field="unitsName" align="center" data-options="width:60">所属VIP合伙人</th>
            <th field="agentMobile" align="center" data-options="width:60">所属合伙人</th>
            <th field="umobile" align="center" data-options="width:60">会员手机号</th>
            <th field="chnName" align="center" data-options="width:60">会员名称</th>
            <th field="goodsName" align="center" data-options="width:60,
                formatter:function(value,row,index){
                    return value;
                }
            ">商品名称</th>
            <th field="money" align="center" data-options="width:60">剩余总额</th>
            <th field="holdNum" align="center" data-options="width:60">剩余数量</th>
            <th field="status" align="center" data-options="width:60,
                formatter: function(value, row, index){
                    var statusStr;
                    if(value == 1){
                        statusStr = '认购';
                    }else if(value == 2){
                        statusStr = '挂单';
                    }
                    return statusStr;
                }
            ">持仓状态</th>
            <th field="buyPoint" align="center" data-options="width:60">建仓价</th>
            <th field="buyTime" align="center" data-options="width:150">建仓时间</th>
            <th field="serviceFee" align="center" data-options="width:60">认购服务费</th>
            <th field="feeBuy" align="center" data-options="width:60">买入手续费</th>
            <th field="feeSell" align="center" data-options="width:60">卖出手续费</th>
        </tr>
        </thead>
    </table>
    <br/>
    <div style="float: right">
        <span style="font-size: 15px;">持仓手续费：</span><label id="feeSum" style="font-size: 15px;"></label>元<br/>
        <span style="font-size: 15px;">持仓总额：</span><label id="moneyTotal" style="font-size: 15px;"></label>元<br/>
        <span style="font-size: 15px;">累计交易总量：</span><label id="holdNum" style="font-size: 15px;"></label>
    </div>
</div>
<div id="addWindow" url="${ctx}/trade/add"></div>
<div id="editWindow" url="${ctx}/trade/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/trade/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["mobile","agentMobile","unitsId","uname","umobile", "centerId", "agentId", "status"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function exportExcel(){
        window.location.href = '${ctx}/trade/exportExcelHold?' + $.param(topSearchBar.getQueryDatas());
    }

    function _loadFilter(data) {
        console.log(JSON.stringify(data));

        //$("#feeSum").text(data.tradeBean.feeSum);
        var trade = data.tradeBean;
        for(var key in trade){
            console.log("#"+key);
            $('#'.concat(key)).text(trade[key]);
        }
        return data.page;
    }

    $("select[id='centerId']").on("click", function () {
        var centerId = $(this).val();
        console.log(centerId);
        $.ajax({
            url: ctx + '/Ml3MemberUnits/findMl3MemberUnits',
            type: 'get',
            data: {centerId: centerId},
            success: function (data) {
                console.log(JSON.stringify(data));
                $("select[id='unitsId']").children().remove();
                var option = [];
                option.push('<option value="">请选择</option>')
                for (var i = 0; i < data.unitsList.length; i++){
                    var units = data.unitsList[i];
                    option.push('<option value="' + units.id + '">' + units.name + '</option>');
                }
                $("select[id='unitsId']").append(option.join(''));
            }
        });
    });

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

    function transHold() {
        var rows = $('#listDataGrid').datagrid('getChecked');
        console.log(rows);
        if(rows.length != 1){
            layer.msg("只能操作一条记录");
            return;
        }

        layer.open({
            type:　2,
            title: '划转持仓客户',
            skin: 'layui-layer-rim', //加上边框
            area: ['600px', '600px'], //宽高
            content: ctx + '/trade/transHold?id=' + rows[0].id,
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