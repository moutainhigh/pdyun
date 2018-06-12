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
<body class="easyui-layout">
<div region="north" style="padding:5px;" border="false">
    <div style="overflow: auto; height:60px;width: 100%; line-height: 30px; font-size: 14px; font-weight: bold;">
        有效流水：<span id="validMoney" class="money" style="font-weight: normal;"></span>
        &nbsp;&nbsp;&nbsp;&nbsp;总建仓笔数：<span id="allPens" class="pens" style="font-weight: normal;"></span>
        &nbsp;&nbsp;&nbsp;&nbsp;总平仓笔数：<span id="sellPens" class="pens" style="font-weight: normal;"></span>
        &nbsp;&nbsp;&nbsp;&nbsp;盈利的笔数：<span id="winPens" class="pens" style="font-weight: normal;"></span>
        &nbsp;&nbsp;&nbsp;&nbsp;平局的笔数：<span id="drawPens" class="pens" style="font-weight: normal;"></span>
        &nbsp;&nbsp;&nbsp;&nbsp;亏损的笔数：<span id="losePens" class="pens" style="font-weight: normal;"></span>
        &nbsp;&nbsp;&nbsp;&nbsp;推广费用：<span id="extensionMoney" class="money" style="font-weight: normal;"></span>
        &nbsp;&nbsp;&nbsp;&nbsp;提现总额：<span id="outMoney" class="money" style="font-weight: normal;"></span>
        &nbsp;&nbsp;&nbsp;&nbsp;总盈亏：<span id="winAndLoseMoney" class="money" style="font-weight: normal;"></span>
        <br/>
        充值总额：<span id="inMoney" class="money" style="font-weight: normal;"></span>
        &nbsp;&nbsp;&nbsp;&nbsp;客户余额：<span id="money" class="money" style="font-weight: normal;"></span>
        &nbsp;&nbsp;&nbsp;&nbsp;持仓总额：<span id="holdMoney" class="money" style="font-weight: normal;"></span>
        &nbsp;&nbsp;&nbsp;&nbsp;认购服务费：<span id="serviceFeeSum" class="money" style="font-weight: normal;"></span>
        &nbsp;&nbsp;&nbsp;&nbsp;买入手续费：<span id="feeBuySum" class="money" style="font-weight: normal;"></span>
        &nbsp;&nbsp;&nbsp;&nbsp;卖出手续费：<span id="feeSellSum" class="money" style="font-weight: normal;"></span>
        <%--&nbsp;&nbsp;&nbsp;&nbsp;交易管理费：<span id="tradeManageMoney" class="money" style="font-weight: normal;"></span>--%>
    </div>
    <!-- 查询 -->
    <div>
        <label class="input-inline">
            <span>交易模式：</span>
            <select style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;" id="model">
                <option value="">--请选择--</option>
                <option value="0">市场模式</option>
                <%--<option value="1">时间模式</option>--%>
                <%--<option value="2">点位模式</option>--%>
            </select>
            <span>VIP合伙人：</span>
            <select style="width:200px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;" id="unitsId">
                <option value="">--请选择--</option>
                <c:forEach var="it" items="${units}">
                    <option value="${it.id}">${it.name}</option>
                </c:forEach>
            </select>
            <span>会员姓名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="chnName" />
            <span>会员手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="userMobile" />
            <span>合伙人：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />
            <c:if test="${isUnits}">
                <span>合伙人手机号：</span>
                <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="agentMobile" />
            </c:if>
            <span>开始日期：</span>
            <input class="easyui-datetimebox" data-options="width:150, height:35" type="text" id="startDate" />
            <span>结束日期：</span>
            <input class="easyui-datetimebox" data-options="width:150, height:35" type="text" id="endDate" />
        </label>
        <label class="input-inline">
            <button id="queryListBt" class="btn btn-info margin-r10" onclick="queryResult()">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/tradestatistics/find',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">
        <thead>
        <tr>
            <th field="serialNo" align="center" data-options="width:80">流水号</th>
            <th field="unitsName" align="center" data-options="width:60">VIP合伙人</th>
            <th field="agentMobile" align="center" data-options="width:80">代理手机号</th>
            <th field="uname" align="center" data-options="">会员姓名</th>
            <th field="mobile" align="center" data-options="width:80">会员手机号</th>
            <th field="goodsName" align="center" data-options="width:60,
                formatter:function(value,row,index){
                    return value;
                }
            ">商品名称</th>
            <th field="money" align="center" data-options="width:60,
                formatter:function(value,row,index){
                    return (Number(value)+Number(row.couponMoney)) + (Number(row.couponMoney) != 0 ? '（'+row.couponMoney+'）' : '');
                }
            ">总额(券额)</th>
          <%--  <th field="buyUpDown" align="center" data-options="width:50,
                formatter:function(value,row,index){
                    return value == 0 ? '<span style=\'color:red\'>买涨↑</span>' : '<span style=\'color:green\'>买跌↓</span>';
                }
            ">买涨买跌</th>--%>
            <th field="holdNum" align="center" data-options="width:60">数量</th>
            <th field="serviceFee" align="center" data-options="width:60">认购服务费</th>
            <th field="feeBuy" align="center" data-options="width:60">买入手续费</th>
            <th field="feeSell" align="center" data-options="width:60">卖出手续费</th>
            <th field="buyPoint" align="center" data-options="width:60">建仓价</th>
            <th field="sellPoint" align="center" data-options="width:60">平仓价</th>
            <th field="sellType" align="center" data-options="width:40,
                formatter:function(value,row,index){
                    if(Number(row.buyPoint) === Number(row.sellPoint)){
                        return '平';
                    }else if(row.difMoney > 0){
                        return '<span style=\'color:red\'>赢<span>';
                    }else{
                        return '<span style=\'color:green\'>亏<span>';
                    }
                }
            ">盈亏平</th>
            <th field="difMoney" align="center" data-options="width:50">盈亏</th>
            <th field="buyTime" align="center" data-options="width:100">建仓时间</th>
            <th field="sellTime" align="center" data-options="width:120">平仓时间</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/trade/add"></div>
<div id="editWindow" url="${ctx}/trade/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = new TopSearchBar();
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/trade/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["unitsId", "mobile", "agentMobile", "startDate", "endDate", "userMobile", "model", "chnName"]
        };
        topSearchBar.initBar(toolBarInitObj);

        queryResult();
    });
    
    function queryResult() {
        $.ajax({
            url: ctx + '/tradestatistics/result',
            type: 'POST',
            data: topSearchBar.getQueryDatas(false),
            success: function(data){
                showResult(data);
            }
        });
    }

    function _loadFilter(data) {
        if(data.extObj != null){
            showResult(data.extObj);
        }
        return data.gridPager;
    }

    function showResult(obj) {
        $('.money').html('0.00');
        $('.pens').html('0');
        for(var attr in obj){
            for(var attr1 in obj[attr]){
                $('#'+attr1).html(obj[attr][attr1]);
            }
        }
    }
</script>
</body>
</html>