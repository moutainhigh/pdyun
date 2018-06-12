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
    <!-- 查询 -->
    <div>
        <label class="input-inline" style="width: 160rem;">
            <span>VIP合伙人名称：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="unitsName" />
            <span>流水号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="serialNo" />
            <span>会员姓名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="uname" />
            <span>会员手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />
            <span>开始时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="createTimeMin" />
            <span>结束时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="createTimeMax" />
            <span>状态：</span>
            <select id="status" class="form-control span1" style="width: 10%; display: inline-block">
                <option value="">全部</option>
                <option value="0">处理中</option>
                <option value="1">成功</option>
                <option value="2">失败</option>
            </select>
        </label>
    </div>
    <div>
        <label>
            <span>支&nbsp;付&nbsp;渠&nbsp;道：&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <select id="channel" class="form-control span1" style="width: 49%; display: inline-block">
                <option value="">全部</option>
                <c:forEach items="${channelList}" var="channel" varStatus="index">
                    <option>${channel}</option>
                </c:forEach>
            </select>
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <button class="btn btn-info margin-r10" onclick="exportExcel()">导出</button>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/moneyrecord/listin',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right',
	                loadFilter: function(data){
                    	   var str = data.moneyInTotal;
                    	   var str1 = data.noMoneyInTotal;
                    	   var str2 = data.alreadyMoneyInTotal;
                        <%--if(null != str1 && '' != str1){--%>
                            if(str1 == null || str1 == ''){
                                document.getElementById('noMoneyInTotal').innerHTML='0元';
                            }else{
                                document.getElementById('noMoneyInTotal').innerHTML=str1+'元';
                            }
                            if(str2 == null || str2 == ''){
                                document.getElementById('alreadyMoneyInTotal').innerHTML='0元';
                            }else{
                                document.getElementById('alreadyMoneyInTotal').innerHTML=str2+'元';
                            }
                            $('#moneyInTotal').text((Number(str1) + Number(str2)) + '元');
                        <%--}--%>
                        return data.g;
                    }
	            ">

        <thead>
        <tr>
            <th field="unitsName" align="center" data-options="width:130">所属VIP合伙人</th>
            <th field="channel" align="center" data-options="width:130">充值方式</th>
            <th field="serialNo" align="center" data-options="width:130">流水号</th>
            <th field="uname" align="center" data-options="width:100">会员姓名</th>
            <th field="mobile" align="center" data-options="width:100">会员手机号</th>
            <th field="money" align="center" data-options="width:100">金额</th>
            <th field="fee" align="center" data-options="width:70">手续费</th>
            <%--<th field="type" align="center" data-options="width:100,--%>
                <%--formatter:function(value,row,index){--%>
                <%--return value == 0 ? '充值' : '提现';--%>
            <%--}--%>
            <%--">类型</th>--%>
            <th field="thirdSerialNo" align="center" data-options="width:100">第三方流水号</th>
            <th field="status" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value == 0 ? '处理中': (value == 1 ? '成功' : '失败');
                }
            ">状态</th>
            <th field="createTime" align="center" data-options="width:130">创建时间</th>
            <th field="completeTime" align="center" data-options="width:130">完成时间</th>
            <%--<th field="chnName" align="center" data-options="width:100">姓名</th>--%>
            <%--<th field="bankName" align="center" data-options="width:100">银行名称</th>--%>
            <%--<th field="bankAccount" align="center" data-options="width:100">银行卡号</th>--%>
            <%--<th field="failureReason" align="center" data-options="width:100">失败原因</th>--%>
            <th field="remark" align="center" data-options="width:100">备注</th>
        </tr>
        </thead>
    </table><br/><br/>
    <div style="float: right">
        <span style="font-size: 15px;">已入金总金额：</span><label id="alreadyMoneyInTotal" style="font-size: 15px;"></label><br/>
        <span style="font-size: 15px;">未入金总金额：</span><label id="noMoneyInTotal" style="font-size: 15px;"></label><br/>
        <span style="font-size: 15px;">入金总金额：</span><label id="moneyInTotal" style="font-size: 15px;"></label>
    </div>
</div>
<div id="addWindow" url="${ctx}/moneyrecord/add"></div>
<div id="editWindow" url="${ctx}/moneyrecord/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/moneyrecord/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["serialNo","uname","mobile","createTimeMin","createTimeMax","status","channel","unitsName"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function exportExcel(){
        window.location.href = '${ctx}/moneyrecord/exportExcelin?' + $.param(topSearchBar.getQueryDatas());
    }
</script>
</body>
</html>