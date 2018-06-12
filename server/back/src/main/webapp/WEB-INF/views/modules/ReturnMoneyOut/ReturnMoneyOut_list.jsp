<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-12-7
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
    <div>
        <label class="input-inline">
            <span>军团长姓名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="userChnName" />
            <span>合伙人：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="userMobile" />
            <span>开始时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="createTimeMin" />
            <span>结束时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="createTimeMax" />
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <button class="btn btn-info margin-r10" onclick="exportAlaExcel()">导出代付文件</button>
    <shiro:hasPermission name="ReturnMoneyOut:out:past">
        <button onclick="_past(1)" class="btn btn-info margin-r10">审核通过</button>
        <button onclick="_past(2)" class="btn btn-info margin-r10">审核不通过</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/ReturnMoneyOut/list',
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
                    onCheckAll: _onCheckAll
	            ">

        <thead>
        <tr>
            <th field="serialNo" align="center" data-options="width:100">流水号</th>
            <th field="userChnName" align="center" data-options="width:100">军团长名</th>
            <th field="userMobile" align="center" data-options="width:100">合伙人</th>
            <th field="money" align="center" data-options="width:100">金额</th>
            <th field="fee" align="center" data-options="width:100">手续费</th>
            <th field="thirdSerialNo" align="center" data-options="width:100">第三方流水号</th>
            <th field="reviewStatus" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value == 0 ? '处理中': (value == 1 ? '通过' : '不通过');
                }
            ">审核状态</th>
            <th field="status" align="center" data-options="width:100,
                formatter:function(value,row,index){
                    return value == 0 ? '处理中' : value == 1 ? '成功':'失败';
                }
            ">状态</th>
            <th field="createTime" align="center" data-options="width:100">创建时间</th>
            <th field="completeTime" align="center" data-options="width:100">完成时间</th>
            <th field="chnName" align="center" data-options="">姓名</th>
            <th field="bankName" align="center" data-options="">银行名称</th>
            <th field="openBankName" align="center" data-options="">银行名称</th>
            <th field="bankAccount" align="center" data-options="">银行卡号</th>
            <th field="failureReason" align="center" data-options="width:100">失败原因</th>
            <th field="remark" align="center" data-options="width:100">备注</th>
            <th field="xx" align="center" data-options="width:100,
            formatter:_operation_html
            ">操作</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/ReturnMoneyOut/add"></div>
<div id="editWindow" url="${ctx}/ReturnMoneyOut/edit"></div>
<div id="infoWindow" url="${ctx}/ReturnMoneyOut/info"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/ReturnMoneyOut/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["userChnName","userMobile","createTimeMin","createTimeMax"]
        };
        var topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function detail(id){
        openWindow("详细信息", 'infoWindow', 'id='+id, 400, 300);
    }
    function _operation_html(value,row,index) {
        return '<a href="javascript:detail(\'' + row.id + '\');">详细信息</a>';
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
                        url:'${ctx}/returnmoneyout/crown/pass',
                        data: {ids: _ids.join(',')},
                        type: 'POST',
                        success: function(result){
                            layer.closeAll();
                            /*layer.alert(result.msg);*/
                            layer.alert(result);
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
                        url:'${ctx}/returnmoneyout/ala/nopass',
                        data: {ids: _ids.join(','), failureReason: r},
                        type: 'POST',
                        success: function(result){
                            layer.closeAll();
                            layer.alert(result);
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

    function exportAlaExcel(){
        window.location.href = '${ctx}/ReturnMoneyOut/exportAlaExcel?ids=' + _ids().join(',');
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