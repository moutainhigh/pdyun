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
<body>
<div region="north" style="padding:5px;" border="false">
    <!-- 查询 -->
    <div class="row">
        <%--<label class="input-inline">--%>
        <div class="col-sm-2">
            <span>流水号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="serialNo" />
        </div>
        <div class="col-sm-2">
            <span>第三方流水号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="thirdSerialNo" />
        </div>
        <div class="col-sm-2">
            <span>VIP合伙人名称：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="memberName" />
        </div>
        <%--<div class="col-sm-2">--%>
            <%--<span>合伙人手机号：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="agentMobile" />--%>
        <%--</div>--%>
        <div class="col-sm-2">
            <span>开始时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="startDate" />
        </div>
        <div class="col-sm-2">
            <span>结束时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="endDate" />
        </div>
    </div>
    <div class="row" style="margin-top: 10px;">
        <div class="col-sm-2">
            <span>审核状态：</span>
            <select id="reviewStatus" class="form-control span1" style="width: 50%; display: inline-block">
                <option value="">全部</option>
                <option value="0">处理中</option>
                <option value="1">通过</option>
                <option value="2">不通过</option>
            </select>
        </div>
        <div class="col-sm-2">
            <span>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</span>
            <select id="status" class="form-control span1" style="width: 55%; display: inline-block">
                <option value="">全部</option>
                <option value="0">处理中</option>
                <option value="1">成功</option>
                <option value="2">失败</option>
            </select>
        </div>
        <div class="col-sm-2">
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </div>
    </div>
    <div class="row" style="margin-top: 10px;">
        <div class="col-sm-4">
            <button class="btn btn-info margin-r10" onclick="exportExcel()">导出</button>
            <%--<button class="btn btn-info margin-r10" onclick="exportAlaExcel()">导出代付文件</button>--%>
            <%--<shiro:hasPermission name="moneyrecord:out:past">--%>
                <%--<button onclick="_past(1)" class="btn btn-info margin-r10">审核通过</button>--%>
                <%--<button onclick="_past(2)" class="btn btn-info margin-r10">审核不通过</button>--%>
            <%--</shiro:hasPermission>--%>
        </div>
    </div>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/returnFeeWithdraw/returnFeeList',
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
                        console.log(data);
                        return data;
                    }
	            ">

        <thead>
        <tr>
            <th field="serialNo" align="center" data-options="">流水号</th>
            <%--<th field="userId" align="center" data-options="width:100">会员id</th>--%>
            <th field="unitsName" align="center" data-options="">VIP合伙人名称</th>
            <th field="realName" align="center" data-options="width:100">VIP合伙人姓名</th>
            <th field="money" align="center" data-options="width:100">金额</th>
            <th field="fee" align="center" data-options="width:100">手续费</th>
            <%--<th field="type" align="center" data-options="width:100,--%>
            <%--formatter:function(value,row,index){--%>
            <%--return value == 0 ? '充值' : '提现';--%>
            <%--}--%>
            <%--">类型</th>--%>
            <th field="thirdSerialNo" align="center" data-options="width:100">第三方流水号</th>
            <th field="reviewStatus" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value == 0 ? '处理中': (value == 1 ? '通过' : '不通过');
                }
            ">审核状态</th>
            <th field="status" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value == 0 ? '处理中': (value == 1 ? '成功' : '失败');
                }
            ">状态</th>
            <th field="createTime" align="center" data-options="width:150">创建时间</th>
            <th field="completeTime" align="center" data-options="width:150">完成时间</th>
            <th field="chnName" align="center" data-options="width:100">姓名</th>
            <th field="bankName" align="center" data-options="width:100">银行名称</th>
            <th field="openBankName" align="center" data-options="width:100">开户行名称</th>
            <th field="bankAccount" align="center" data-options="width:100">银行卡号</th>
            <th field="failureReason" align="center" data-options="width:100">失败原因</th>
            <th field="remark" align="center" data-options="width:100">备注</th>
        </tr>
        </thead>
    </table><br/><br/>
    <%--<div style="float: right">--%>
        <%--<span style="font-size: 15px;">已出金总金额：</span><label id="alreadyMoneyOutTotal" style="font-size: 15px;"></label><br/>--%>
        <%--<span style="font-size: 15px;">未出金总金额：</span><label id="noMoneyOutTotal" style="font-size: 15px;"></label><br/>--%>
        <%--<span style="font-size: 15px;">出金总金额：</span><label id="moneyOutTotal" style="font-size: 15px;"></label>--%>
    <%--</div>--%>
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
            queryColumns: ["serialNo", "thirdSerialNo", "memberName","startDate","endDate","reviewStatus","status"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function exportExcel(){
        window.location.href = '${ctx}/moneyrecord/exportExcelout?' + $.param(topSearchBar.getQueryDatas());
    }

    function _past(s){
        var rows = $('#listDataGrid').datagrid('getChecked');
        if(rows.length == 0){
            layer.msg('请选择');
            return;
        }
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
                        url:'${ctx}/returnFeeWithdraw/pass',
                        data: {ids: _ids.join(',')},
                        type: 'POST',
                        success: function(result){
                            console.log(JSON.stringify(result));
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
                        url:'${ctx}/returnFeeWithdraw/nopass',
                        data: {ids: _ids.join(','), failureReason: r},
                        type: 'POST',
                        success: function(result){
                            console.log(JSON.stringify(result));
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
            if(rows[i].reviewStatus != '0' || rowData.status != '0'){
                $("#listDataGrid").datagrid('uncheckRow', i);
            }
        }
    }

    /*function exportAlaExcel(){
        window.location.href = '${ctx}/returnFeeWithdraw/exportAlaExcel?ids=' + _ids().join(',');
    }*/
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
</body>
</html>