<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/18
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
    <div region="north" style="padding:5px;" border="false;" height="auto">
        <!-- 查询 -->
        <div class="form-group row">
            <%--<div class="col-md-2">--%>
                <%--<span>交易模式：</span>--%>
                <%--<select style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;" id="model">--%>
                    <%--<option value="">--请选择--</option>--%>
                    <%--<option value="0">市场模式</option>--%>
                <%--</select>--%>
            <%--</div>--%>
            <%--<div class="col-md-2">--%>
                <%--<span>流水号：</span>--%>
                <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="serialNo" />--%>
            <%--</div>--%>
            <div class="col-md-2">
                <span>原订单流水号：</span>
                <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="holdSerialNo" />
            </div>
            <div class="col-md-2">
                <span>客户姓名：</span>
                <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="chnName" />
            </div>
            <div class="col-md-2">
                <span>客户手机号：</span>
                <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />
            </div>
                <div class="col-md-2">
                    <span>原持仓客户姓名：</span>
                    <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="oldHoldChnName" />
                </div>
                <div class="col-md-2">
                    <span>原持仓客户手机：</span>
                    <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="oldHoldMobile" />
                </div>
            <%--<div class="col-md-2">
                <span>市场管理部：</span>
                <select style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;" id="centerId">
                    <option value="">--请选择--</option>
                    <c:forEach var="it" items="${operateCenter}">
                        <option value="${it.id}">${it.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2">
                <span>VIP合伙人：</span>
                <select style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;" id="unitsId">
                    <option value="">--请选择--</option>
                    <c:forEach var="it" items="${units}">
                        <option value="${it.id}">${it.name}</option>
                    </c:forEach>
                </select>
            </div>--%>
        </div>
        <div class="form-group row">

            <%--<div class="col-md-2">
                <span>开始日期：</span>
                <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="startDate" />
            </div>
            <div class="col-md-2">
                <span>结束日期：</span>
                <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="endDate" /><br/>
            </div>--%>
            <div class="col-md-2">
                <button id="queryListBt" class="btn btn-info margin-r10" onclick="queryResult()">查询</button>
                <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
            </div>
        </div>
    </div>
    <div region="center" style="padding:5px;" border="false">
        <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
               data-options="
                        url: '${ctx}/transTrade/list',
                        lines: true,
                        idField: 'id',
                        striped: true,
                        fitColumns:true,
                        onLoadSuccess:datagridLoadSuccess,
                        resizeHandle:'right'
                    ">
            <thead>
            <tr>
                <th field="holdSerialNo" align="center" data-options="width:80">原订单流水号</th>

                <th field="oldHoldChnName" align="center" data-options="width:80">原持仓客户姓名</th>
                <th field="oldHoldMobile" align="center" data-options="width:80">原持仓客户手机号</th>
                <th field="chnName" align="center" data-options="width:80">客户姓名</th>
                <th field="mobile" align="center" data-options="width:80">会员手机号</th>
                <th field="goodsName" align="center" data-options="width:60,
                    formatter:function(value,row,index){
                        return value;
                    }
                ">商品名称</th>
                <th field="money" align="center" data-options="width:60">金额</th>
                <%--<th field="type" align="center" data-options="width:50,
                    formatter:function(value,row,index){
                        return Number(row.couponMoney) == 0 ? '资金' : (Number(row.money) == 0 ? '券':'资金+券');
                    }
                ">付费方式</th>--%>
                <%--<th field="buyUpDown" align="center" data-options="width:50,--%>
                <%--formatter:function(value,row,index){--%>
                <%--return value == 0 ? '<span style=\'color:red\'>买涨↑</span>' : '<span style=\'color:green\'>买跌↓</span>';--%>
                <%--}--%>
                <%--">买涨买跌</th>--%>
                <th field="transHoldNum" align="center" data-options="width:60">划转数量</th>
                <th field="buyPoint" align="center" data-options="width:60">原订单买入价</th>
                <%--<th field="sellPoint" align="center" data-options="width:60">平仓价</th>--%>
                <th field="serviceFee" align="center" data-options="width:60">认购服务费</th>
                <th field="feeBuy" align="center" data-options="width:60">买入手续费</th>
                <%--<th field="feeSell" align="center" data-options="width:60">卖出手续费</th>--%>
                <%--<th field="status" align="center" data-options="width:60,
                    formatter:function(value,row,index){
                        if(Number(value) === Number(1)){
                            return '持仓';
                        }else if(Number(value) === Number(2)){
                            return '挂单';
                        }else if(Number(value) === Number(3)){
                            return '平仓(交割)';
                        }else{
                            return '下架';
                        }
                    }
                ">订单状态</th>--%>
                <%--<th field="sellType" align="center" data-options="width:40,
                    formatter:function(value,row,index){
                        if(Number(row.buyPoint) === Number(row.sellPoint)){
                            return '平';
                        }else if(row.difMoney > 0){
                            return '<span style=\'color:red\'>赢<span>';
                        }else{
                            return '<span style=\'color:green\'>亏<span>';
                        }
                    }
                ">盈亏平</th>--%>
                <%--<th field="difMoney" align="center" data-options="width:50">盈亏</th>--%>
                <th field="createTime" align="center" data-options="width:100">划转时间</th>
                <%--<th field="sellTime" align="center" data-options="width:120">平仓时间</th>--%>
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
            queryColumns: ["chnName", "mobile", "holdSerialNo", "oldHoldChnName", "oldHoldMobile"]
        };
        topSearchBar.initBar(toolBarInitObj);
        queryResult();
    })
</script>
</body>
</html>
