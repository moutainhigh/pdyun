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
    <!-- 查询  class="form-group row"-->
    <div>
        <label class="input-inline">
            <span>用户手机号：</span>
            <input class="easyui-textbox" style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;" placeholder="手机号" type="text" id="mobile" />

        </label>
        <%--<label class="input-inline">
            <span>类型：</span>
            <select style="height:35px;" name="goodsType" >
                <option value="">--请选择--</option>
                    <option value="1">正积分</option>
                    <option value="1">负积分</option>
            </select>
        </label>--%>
    </div>
    <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
    <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
    <button id="export" class="btn btn-info margin-r10" onclick="exportExcel()">导出</button>

</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/subgoods/getIntegralList',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="id" hidden="true">id</th>
            <th field="userName" align="center" data-options="width:100">用户名</th>
            <th field="mobile" align="center" data-options="width:100">手机号</th>
            <th field="type" align="center" data-options="width:100,
                formatter: function(value, row, index){
                    if('1' == value){
                        return '正积分';
                    }else{
                        return '负积分';
                    }
                }">类型</th>
            <th field="integral" align="center" data-options="width:100">积分变更</th>
            <th field="integralBefore" align="center" data-options="">积分变更前</th>
            <th field="integralAfter" align="center" data-options="width:100">积分变更后</th>
            <th field="createTime" align="center" data-options="
                formatter: function(value, row, index){
                   return value.substring(0,value.length-2);
                }">积分变更时间</th>
            <th field="remark" align="center">备注</th>
        </tr>
        </thead>
    </table>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/contract/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["mobile"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    });



    function exportExcel(){
        window.location.href = '${ctx}/subgoods/exportExcel?' + $.param(topSearchBar.getQueryDatas());
    }



    <%--function _past(s){--%>
        <%--var row = $('#listDataGrid').datagrid('getSelected');--%>
        <%--if(!row){--%>
            <%--layer.msg('请选择', {--%>
                <%--offset: getLayerCenter()--%>
            <%--});--%>
            <%--return;--%>
        <%--}--%>
        <%--var str = s == '1' ? '确认上架 ['+(row.goodsName)+'] 商品？' : '确认下架 ['+(row.goodsName)+'] 商品？';--%>
        <%--$.messager.confirm('确认', str, function (r) {--%>
            <%--if(r){--%>
                <%--$.ajax({--%>
                    <%--url:'${ctx}/subgoods/' + (s == '1' ? 'open' : 'close'),--%>
                    <%--data: {id: row.id},--%>
                    <%--type: 'POST',--%>
                    <%--success: function(result){--%>
                        <%--console.log(result);--%>
                        <%--if (result.code == 0) {--%>
                            <%--layer.msg(result.msg);--%>
                            <%--/*showSuccess();*/--%>
                            <%--$('#listDataGrid').datagrid('reload');--%>
                        <%--} else {--%>
                            <%--layer.msg(result.msg, {--%>
                                <%--offset: getLayerCenter()--%>
                            <%--});--%>
                        <%--}--%>
                    <%--}--%>
                <%--});--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>
</script>

</body>
</html>