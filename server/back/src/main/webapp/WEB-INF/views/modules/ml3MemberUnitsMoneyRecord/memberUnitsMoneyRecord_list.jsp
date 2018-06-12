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
        <label class="input-inline">
            <%--<span>流水号：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="serialNo" />--%>
            <%--<span>第三方流水号：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="thirdSerialNo" />--%>
            <span>VIP合伙人名称：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="unitsName" />
            &nbsp;&nbsp;&nbsp;<span>开始时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="createTime" />
            &nbsp;&nbsp;&nbsp;<span>结束时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="endTime" />
            &nbsp;&nbsp;&nbsp;<span>审核状态：</span>
            <select id="reviewStatus" class="form-control span1" style="width: 10%; display: inline-block">
                <option value="">全部</option>
                <option value="0">处理中</option>
                <option value="1">通过</option>
                <option value="2">不通过</option>
            </select>
            &nbsp;&nbsp;&nbsp;<button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            &nbsp;&nbsp;&nbsp;<button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <%--<button class="btn btn-info margin-r10" onclick="exportExcel()">导出</button>--%>
    <%--<button class="btn btn-info margin-r10" onclick="exportAlaExcel()">导出代付文件</button>--%>
    <shiro:hasPermission name="moneyrecord:out:past">
        <button onclick="_past(1)" class="btn btn-info margin-r10">审核通过</button>
        <button onclick="_past(2)" class="btn btn-info margin-r10">审核不通过</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/Ml3MemberUnitsMoneyRecord/findMemberUnitsMoneyRecordList',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right',
	                frozenColumns:[[
                        {field:'ck',checkbox:true}
                    ]],
                    singleSelect: false,
                    checkOnSelect: false,
                    selectOnCheck: false,
                    onCheck: _onCheck,
                    onCheckAll: _onCheckAll,
                    loadFilter: function(data){
                            var str = data.moneyOutTotal;
                            var str1 = data.noMoneyOutTotal;
                            var str2 = data.alreadyMoneyOutTotal;

                            if(str == null || str == ''){
                            document.getElementById('moneyOutTotal').innerHTML='0元';
                            }else{
                             document.getElementById('moneyOutTotal').innerHTML=str+'元';
                            }
                            if(str1 == null || str1 == ''){
                            document.getElementById('noMoneyOutTotal').innerHTML='0元';
                            }else{
                             document.getElementById('noMoneyOutTotal').innerHTML=str1+'元';
                            }
                            if(str2 == null || str2 == ''){
                            document.getElementById('alreadyMoneyOutTotal').innerHTML='0元';
                            }else{
                             document.getElementById('alreadyMoneyOutTotal').innerHTML=str2+'元';
                            }
                        return data.g;
                        }
	            ">

            <thead>
            <tr>
                <th field="unitsName" align="center" data-options="width:100">VIP合伙人名称</th>
                <th field="money" align="center" data-options="width:100">申请金额</th>
                <th field="fee" align="center" data-options="width:100">手续费(1%)</th>
                <th field="reviewStatus" align="center" data-options="width:100,
                formatter:function(value, row, index){
                    return value == 0 ? '处理中': (value == 1 ? '通过' : '不通过');
                }">审核状态</th>
                <th field="bankName" align="center" data-options="width:100">开户行</th>
                <th field="openBankName" align="center" data-options="width:100">开户支行</th>

                <th field="bankAccount" align="center" data-options="width:100">银行账户</th>
                <th field="realName" align="center" data-options="width:100">开户名称</th>

                <th field="createTime" align="center" data-options="width:100, formatter: formatDateBoxFull">申请时间</th>
                <%--<th field="type" align="center" data-options="width:100,--%>
                <%--formatter:function(value,row,index){--%>
                <%--return value == '0' ? '充值' : '提现';--%>
                <%--}--%>
                <%--">类型</th>--%>
                <th field="completeTime" align="center" data-options="width:100, formatter: formatDateBoxFull">完成时间</th>
                <th field="remark" align="center" data-options="width:100">备注</th>
            </tr>
            </thead>
    </table><br/><br/>
    <div style="float: right">
        <span style="font-size: 15px;">已出金总金额：</span><label id="alreadyMoneyOutTotal" style="font-size: 15px;"></label><br/>
        <span style="font-size: 15px;">未出金总金额：</span><label id="noMoneyOutTotal" style="font-size: 15px;"></label><br/>
        <span style="font-size: 15px;">出金总金额：</span><label id="moneyOutTotal" style="font-size: 15px;"></label>
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
            queryColumns: ["unitsName", "createTime", "endTime","reviewStatus"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function exportExcel(){
        window.location.href = '${ctx}/moneyrecord/exportExcelout?' + $.param(topSearchBar.getQueryDatas());
    }

    function _past(s){
        var rows = $('#listDataGrid').datagrid('getChecked');
        var str = s == '1' ? '确认通过审核？' : '确认审核不通过？';
        var _ids = [];
        for(var i=0; i<rows.length; i++){
            _ids.push(rows[i].id);
        }
        if(s == '1'){
            $.messager.confirm('确认', str, function (r) {
                if(r){
                    layer.load(1, {
                        shade: [0.5,'#fff']
                    });
                    $.ajax({
                        url:'${ctx}/Ml3MemberUnitsMoneyRecord/memberUnitsMoneyPass/pass',
                        data: {ids: _ids.join(',')},
                        type: 'POST',
                        success: function(result){
                            layer.closeAll();
                            layer.alert(result.msg);
                            $('#listDataGrid').datagrid('uncheckAll');
                            $('#listDataGrid').datagrid('reload');
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
                    layer.load(1, {
                        shade: [0.5,'#fff']
                    });
                    $.ajax({
                        url:'${ctx}/Ml3MemberUnitsMoneyRecord/memberUnitsMoneyPass/noPass',
                        data: {ids: _ids.join(','), failureReason: r},
                        type: 'POST',
                        success: function(result){
                            layer.closeAll();
                            layer.alert(result.msg);
                            $('#listDataGrid').datagrid('uncheckAll');
                            $('#listDataGrid').datagrid('reload');
                        }
                    });
                }
            });
        }
    }
    
    function _onCheck(rowIndex, rowData) {
        if(rowData.reviewStatus != '0' || rowData.status != '0'){
            $("#listDataGrid").datagrid('uncheckRow', rowIndex);
            layer.msg("不能重复审核");
        }
    }
    
    function _onCheckAll(rows) {
        for(var i=0; i<rows.length; i++){
            if(rows[i].reviewStatus != '0' || rows[i].status != '0'){
                $("#listDataGrid").datagrid('uncheckRow', i);
            }
        }
    }

    function exportAlaExcel(){
        window.location.href = '${ctx}/moneyrecord/exportAlaExcel?ids=' + _ids().join(',');
    }
    function _ids(){
        var rows = $('#listDataGrid').datagrid('getChecked');
        var ids = [];
        for(var i = 0; i < rows.length; i++){
            if(rows[i].status == '0'){
                ids.push(rows[i].id);
            }
        }
        return ids;
    }
</script>
<script type="text/javascript" src="${ctxStatic}/modules/yizhifu_daifu_error.js"></script>
</body>
</html>