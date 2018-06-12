<%@ page import="com.rmkj.microcap.modules.trade.entity.TradeBean" %><%--
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
    <!-- 查询 -->
    <div>
        <label class="input-inline">
            <span>合伙人：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="jtMobile" name="jtMobile"/>
            <span>会员姓名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="userChnName" name="userChnName" />
            <span>会员手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="userMobile"  name="userMobile"/>
        </label>
        <label class="input-inline">
            <span>交 易 流 水 号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="serialNo"  name="serialNo"/>
            <span>交易金额：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="money"  name="money"/>
            <span>开 始 时 间 ：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="sellTimeMin"  name="sellTimeMin"/>
            <span>结 束 时 间 ：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="sellTimeMax"  name="sellTimeMax"/>

            &nbsp;&nbsp;
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
</div>
<div region="center" style="padding:5px;height: 100%;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/trade/fanyong',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right',
	                loadFilter: function(data){
	                var str = JSON.stringify(data.fanyongTotal.totalReturn);
	                if(str == null){
	                document.getElementById('t').innerHTML='0元';
	                }
	                if(str != null){
	                     document.getElementById('t').innerHTML=str+'元';
	                }
                        return data.g;
	                }
	            ">

        <thead>
        <tr>
            <th field="junTuanChnName" align="center" data-options="width:100">军团长姓名</th>
            <th field="junTuanMobile" align="center" data-options="width:100">合伙人</th>
            <th field="userChnName" align="center" data-options="width:100">会员用户姓名</th>
            <th field="jType" align="center" data-options="width:100">级别</th>
            <th field="serialNo" align="center" data-options="width:100">交易流水号</th>
            <th field="money" align="center" data-options="width:100">交易金额</th>
            <th field="difMoney" align="center" data-options="width:100">盈亏金额</th>
            <th field="returnMoney" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    if(row.jType == '炮兵团'){
                        return value = row.return1Money;
                    }else if(row.jType == '骑兵团'){
                        return value = row.return2Money;
                    }else if(row.jType == '步兵团'){
                        return value = row.return3Money;
                    }
                }
            ">返佣金额</th>
            <th field="sellTime" align="center" data-options="width:100">交割时间</th>
        </tr>
        </thead>
    </table><br/><br/>
    <div style="float: right;">
        <span style="font-size: 15px;">合计返佣:</span>
        <label id="t" style="font-size: 15px;"></label>
    </div>

</div>
<div id="addWindow" url="${ctx}/trade/add"></div>
<div id="editWindow" url="${ctx}/trade/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        $('#clearBarBt').click(function () {
            document.getElementById('t').innerHTML='0';
        })
        var operationObj = {
            deleteUrl: ctx + '/trade/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["jtMobile","userChnName","userMobile","money","serialNo","sellTimeMin","sellTimeMax","t"]
        };
        var topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
        var mobile = $("#jtMobile").val();

    })
</script>
</body>
</html>