<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body class="easyui-layout">
<div region="north" style="padding:5px;" border="false">
    <!-- 查询 -->
    <%--<div>--%>
        <%--<label class="input-inline">--%>
            <%--<span>名称：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="name" />--%>
            <%--<button id="queryListBt" class="btn btn-info margin-r10">查询</button>--%>
            <%--<button id="clearBarBt" class="btn btn-info margin-r10">清空</button>--%>
        <%--</label>--%>
    <%--</div>--%>
    <!-- 筛选条件 结束 -->
    <shiro:hasPermission name="article:add">
        <button id="addInfoBt" class="btn btn-info margin-r10">添加</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="article:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="article:delete">
        <button id="deleteBt" class="btn btn-info margin-r10">删除</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/weChatPublic/articles/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">
        <thead>
        <tr>
            <th field="picurl" align="center" data-options="width:80, formatter: _f_picurl">图片</th>
            <th field="title" align="center" data-options="width:100">标题</th>
            <th field="createTime" align="center" data-options="width:100">创建时间</th>
            <th field="url" align="center" data-options="width:50, formatter: _f_url">查看图文</th>
            <th field="aaa" align="center" data-options="width:100, formatter: _f_operate">操作</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/weChatPublic/articles/edit"></div>
<div id="editWindow" url="${ctx}/weChatPublic/articles/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/weChatPublic/articles/del'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: []
        };
        var topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })

    function _f_picurl(value, row) {
        return '<img src="'+value+'" width="100px" />';
    }
    function _f_url(value, row) {
        return '<a href="'+value+'" target="_blank">预览</a>';
    }
    function _f_operate(value, row) {
        return '<a href="javascript:messageSend(\''+row.id+'\')">立刻推送</a>';
    }



    function messageSend(id){
        layer.alert(wechatPublicSelectHtml.join(''), {
            icon: 6,
            title: '请选择公众号：'
        }, function () {
            layer.load(0);
            $.ajax({
                url: ctx + '/weChatPublic/msgsend/toAll',
                type: 'POST',
                data: {
                    type: 'tuwen', content: id, wechatPublicId: $('#wechatPublicId').val()
                },
                success: function(data){
                    layer.closeAll();
                    layer.msg(data, {
                        offset: getLayerCenter()
                    });
                },
                error: function(){
                    layer.closeAll();
                }
            });
        });
    }

    var wechatPublicSelectHtml = [];
    $.ajax({
        url: ctx + '/weChatPublic/all',
        type: 'POST',
        success: function(data){
            data.splice(0, 0, {
                name: '--请选择--',
                id: null
            });
            wechatPublicSelectHtml.push('<select id="wechatPublicId">');
            for (var i = 0; i < data.length; i++){
                wechatPublicSelectHtml.push('<option value="'+data[i].id+'">'+data[i].name+'</option>');
            }
            wechatPublicSelectHtml.push('</select>');
        }
    });
</script>
</body>
</html>