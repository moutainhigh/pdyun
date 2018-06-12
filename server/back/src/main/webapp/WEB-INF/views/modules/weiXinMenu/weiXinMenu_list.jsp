<%@ page import="java.util.List" %>
<%@ page import="com.rmkj.microcap.modules.weiXinMenu.entity.WeixinMenuBean" %>
<%--
Created by CodeGenerator.
User: zhangbowen
Date: 2016-6-7
To change this template use File | Settings | File Templates.
--%>
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
    <%--<div>--%>
        <%--<label class="input-inline">--%>
            <%--<span>名称：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="name" />--%>
            <%--<span>类型：</span>--%>
            <%--<input class="easyui-textbox" data-options="width:150,height:35" type="text" id="type" />--%>
            <%--<button id="queryListBt" class="btn btn-info margin-r10">查询</button>--%>
            <%--<button id="clearBarBt" class="btn btn-info margin-r10">清空</button>--%>
        <%--</label>--%>
    <%--</div>--%>
    <!-- 筛选条件 结束 -->
    <shiro:hasPermission name="weiXinMenu:add">
        <button id="addInfoBt" class="btn btn-info margin-r10">添加</button>
    </shiro:hasPermission>
        <%--<button id="addInfoBtClick" class="btn btn-info margin-r10">添加子菜单</button>--%>
    <shiro:hasPermission name="weiXinMenu:edit">
        <button id="editInfoBt1" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="weiXinMenu:delete">
        <button id="deleteBt1" class="btn btn-info margin-r10">删除</button>
    </shiro:hasPermission>
    <%--<button id="queryBt" class="btn btn-info margin-r10">查询</button>--%>
    选择公众号：<input id="appId" name="appId" value="">
    <button class="btn btn-info margin-r10" style="color: red;">一级菜单1~3个,二级菜单1~5个,菜单名字不能重复</button>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                <%--url: '${ctx}/weiXinMenu/list',--%>
	                lines: true,
	                idField: 'name',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">
        <thead>
        <tr>
                <th field="name" align="center" data-options="width:100">菜单名称</th>
                <th field="parentName" align="center" data-options="width:100,
                    formatter:function(value,row,index){
                        return row.parentName == null ? '无' : row.parentName;
                    }
                ">父菜单</th>
                <th field="type" align="center" data-options="width:100,
                    formatter:function(value,row,index){
                        if(row.parentName == null){
                            return '一级菜单';
                        }
                        if(row.type == 'click'){
                            return '下发文本';
                        }else if(row.type == 'media_id'){
                            return '下发消息';
                        }else if(row.type == 'view_limited'){
                            return '跳转图文消息';
                        }else{
                            return '默认';
                        }
                    }
                ">类型</th>
                <th field="url" align="center" data-options="width:100,
                    formatter:function(value,row,index){
                        if(row.type == 'click'){
                            return row.content;
                        }else if(row.type == 'media_id'){
                            return row.mediaId;
                        }else{
                            return row.url;
                        }
                    }
                ">描述(跳转URL/文本/资源ID)</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/weiXinMenu/add"></div>
<%--<div id="editWindow" url="${ctx}/weiXinMenu/edit"></div>--%>
<div id="infoWindow" url="${ctx}/weiXinMenu/edit"></div>
<div id="delWindow" url="${ctx}/weiXinMenu/delete"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var topSearchBar = null;
    $(function () {
        var operationObj = {
//            deleteUrl: ctx + '/user/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["appId"]
        };
        topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
        $("#deleteBt1").click(function () {
            var row = $('#listDataGrid').datagrid('getSelected');
            if(!row){
                layer.msg("请选择");
                return;
            }else{
                var str3 = null;
                if(row.parentName == null){
                    str3 =  '一级菜单';
                }else{
                    str3 = '二级菜单';
                }
                if(str3 == "二级菜单"){
                    var rows = $("#listDataGrid").datagrid("getRows");
                    var total = 0;
                    for(var i = 0;i<rows.length;i++){
                        if(rows[i].parentName != null){
                            total = total+1;
                        }
                    }
                    if(total == 1){
                        layer.msg("请直接删除一级菜单！");
                        return;
                    }
                }

            }

//            for(var i=0;i<rows.length;i++)
//            {
//                //获取每一行的数据
//                var str = rows[i].parentName;//获取所有行的parentName
//                var total = 0;
//                if(rows[i].parentName !=null){
//                    total = total+1;
//                    alert(total);
////                    if(total == 1){
////                        alert("请直接删除一级菜单！");
////                        return;
////                    }
//                }
//            }

            <%--infoAjaxSubmit(this,'${ctx}/weiXinMenu/delete?name1='+str+'&type='+str1+'&parentName='+row.parentName);--%>
            <%--window.location.href = '${ctx}/weiXinMenu/delete?name1='+str+'&type='+str1+'&parentName='+row.parentName;--%>
//            openWindow("删除", 'delWindow', 'name1='+str+'&type='+str1+'&parentName='+row.parentName);
            if(confirm("确定删除？")){
                $.ajax(
                        {
                            type : 'GET',
                            url : '${ctx}/weiXinMenu/delete',
                            data : 'appId='+$('#appId').combobox('getValue')+'&name1='+row.name+param('parentName', row.parentName),
                            success : function(data)
                            {
                                <%--window.location.href='${ctx}/weiXinMenu/list';--%>
                                layer.msg("操作成功");
                                $('#listDataGrid').datagrid('reload');
                            },
                            error : function()
                            {
                                layer.msg("操作失败");
                            }
                        });

            }
        });
        $("#editInfoBt1").click(function () {
            var row = $('#listDataGrid').datagrid('getSelected');
            if(!row){
                layer.msg("请选择");
                return;
            }
            openWindow("编辑", 'infoWindow', 'name='+encodeURIComponent(row.name)+param('url', row.url)+param('parentName', row.parentName)
                    +param('key', row.content)
                    +param('mediaId', row.mediaId)
                    +param('content', row.content)
                    +param('type', row.type)
                    , 600, 400);
        });

        $('#appId').combobox({
            url: ctx + '/weChatPublic/all',
            valueField: 'appId',
            textField: 'name',
            panelHeight: 'auto',
            loadFilter: function (data) {
                data.splice(0, 0 , {'appId': '', 'name': '--请选择--'});
                return data;
            },
            onChange: function (n) {
                changeAppId(n);
            }
        });

        function changeAppId(appId){
            var datagrid = $('#listDataGrid');
            datagrid.datagrid('options').url = ctx + '/weiXinMenu/list?appId=' + appId;
            if(appId == null || appId == ''){
                $('#addInfoBt,#editInfoBt1,#deleteBt1').attr('disabled', true);
                datagrid.datagrid('loadData', []);
            }else{
                $('#addInfoBt,#editInfoBt1,#deleteBt1').attr('disabled', false);
                datagrid.datagrid('load');
            }
            $('#addWindow,#infoWindow,#delWindow').each(function () {
                var url = $(this).attr("url");
                var ii = url.indexOf('?');
                if(ii != -1){
                    url = url.substring(0, ii);
                }
                $(this).attr("url", url + '?appId=' + appId);
            });
        }

        $('#queryBt').click(function(){
            changeAppId();
        });
        $('#addInfoBt,#editInfoBt1,#deleteBt1').attr('disabled', true);
    });

    function param(name, val) {
        if(typeof val == 'undefined' || val == null){
            return '';
        }
        return '&'.concat(name).concat('=').concat(encodeURIComponent(val));
    }
</script>
</body>
</html>