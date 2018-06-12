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
            <span>会员姓名：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="uname" />
            <span>会员手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />
            <span>开始时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="createTimeMin" />
            <span>结束时间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="createTimeMax" />
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <button class="btn btn-info margin-r10" onclick="exportExcel()">导出</button>
    <button class="btn btn-info margin-r10" onclick="exportPassExcel()">导出审核通过</button>
    <shiro:hasPermission name="moneyrecord:out:past">
        <button onclick="_past(1)" class="btn btn-info margin-r10">审核通过</button>
        <button onclick="_past(2)" class="btn btn-info margin-r10">审核不通过</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/moneyrecord/listout',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="serialNo" align="center" data-options="width:230">流水号</th>
            <%--<th field="userId" align="center" data-options="width:100">会员id</th>--%>
            <th field="uname" align="center" data-options="width:100">会员姓名</th>
            <th field="mobile" align="center" data-options="width:100">会员手机号</th>
            <th field="money" align="center" data-options="width:100">金额</th>
            <th field="fee" align="center" data-options="width:100">手续费</th>
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
            <th field="createTime" align="center" data-options="width:150">创建时间</th>
            <th field="completeTime" align="center" data-options="width:150">完成时间</th>
            <th field="chnName" align="center" data-options="width:100">姓名</th>
            <th field="bankName" align="center" data-options="width:100">银行名称</th>
            <th field="openBankName" align="center" data-options="width:100">支行名称</th>
            <th field="bankAccount" align="center" data-options="width:100">银行卡号</th>
            <th field="failureReason" align="center" data-options="width:100">失败原因</th>
            <th field="remark" align="center" data-options="width:100">备注</th>
        </tr>
        </thead>
    </table>
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
            queryColumns: ["uname","mobile","createTimeMin","createTimeMax"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function exportExcel(){
        window.location.href = '${ctx}/moneyrecord/exportExcelout?' + $.param(topSearchBar.getQueryDatas());
    }
    function exportPassExcel() {
        window.location.href = '${ctx}/moneyrecord/exportPassExcel?' + $.param(topSearchBar.getQueryDatas());
    }
    function _past(s){
        var row = $('#listDataGrid').datagrid('getSelected');
        if(!row){
            layer.msg('请选择', {
                offset: getLayerCenter()
            });
            return;
        }
        if(row.status != '0'){
            layer.msg('此记录已经做过审核！', {
                offset: getLayerCenter()
            });
            return;
        }
        var str = s == '1' ? '确认通过审核？' : '确认审核不通过？';
        if(s == '1'){
            $.messager.confirm('确认', str, function (r) {
                if(r){
                    $.ajax({
                        url:'${ctx}/moneyrecord/out/pastin',
                        data: {id: row.id, s: s},
                        type: 'POST',
                        success: function(result){
                            if (result.code == 0) {
                                showSuccess();
                                $('#listDataGrid').datagrid('reload');
                            } else {
                                layer.msg(result.msg, {
                                    offset: getLayerCenter()
                                });
                            }
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
                    $.ajax({
                        url:'${ctx}/moneyrecord/out/past',
                        data: {id: row.id, s: s, failureReason: r},
                        type: 'POST',
                        success: function(result){
                            if (result.code == 0) {
                                showSuccess();
                                $('#listDataGrid').datagrid('reload');
                            } else {
                                layer.msg(result.msg, {
                                    offset: getLayerCenter()
                                });
                            }
                        }
                    });
                }
            });
        }
    }
</script>
</body>
</html>