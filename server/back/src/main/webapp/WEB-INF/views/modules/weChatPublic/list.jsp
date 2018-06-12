<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
</head>
<body class="easyui-layout">
<div region="north" style="padding:5px;" border="false">
    <!-- 查询 -->
    <div>
        <label class="input-inline">
            <span>名称：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="name" />
            <%--<span>类型：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="type" />--%>
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <button id="addInfoBt" class="btn btn-info margin-r10">添加</button>
    <button class="btn btn-info margin-r10 editWindow" data-url="${ctx}/weChatPublic/edit" data-title="编辑">修改</button>
    <button class="btn btn-info margin-r10 editWindow" data-url="${ctx}/weChatPublic/uploadQrcode" data-title="上传二维码图片">上传二维码图片</button>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/weChatPublic/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">
        <thead>
        <tr>
            <th field="id" align="center" data-options="width:100">ID</th>
            <th field="name" align="center" data-options="width:100">名称</th>
            <th field="appId" align="center" data-options="width:100">appId</th>
            <th field="secret" align="center" data-options="width:100">appSecret</th>
            <th field="domainName" align="center" data-options="width:100">域名</th>
            <th field="status" align="center" data-options="width:100, formatter: _f_status">状态</th>
            <th field="aaa" align="center" data-options="width:100, formatter: _f_operate">操作</th>
        </tr>
        </thead>
    </table>
</div>
<div id="editWindowCommon"></div>
<div id="addWindow" url="${ctx}/weChatPublic/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
//            deleteUrl: ctx + '/user/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ['name']
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })

    function _f_status(value, row) {
        return value == '0' ? '默认' : '主要';
    }

    function _f_operate(value, row) {
        return '<a href="javascript:del(\''+row.id+'\')">删除</a>  <a href="javascript:showQrcode(\''+row.id+'\')">二维码图片</a>';
    }

    function del(id) {
        if(confirm('确定删除？')){
            $.ajax({
                url: ctx + '/weChatPublic/del?id='+id,
                success: function(data){
                    layer.msg(data);
                    $('#listDataGrid').datagrid('reload')
                }
            });
        }
    }

    function showQrcode(id) {
        window.open(ctx+'/weChatPublic/showQrcode?id='+id);
    }
</script>
</body>
</html>