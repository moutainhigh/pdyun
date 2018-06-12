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
    <div class="form-group row">
        <div class="col-md-2">
            <label class="input-inline">
                <span>商品名称：</span>
                <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="goodsName" />
            </label>
        </div>
        <div class="col-md-2">
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </div>
    </div>

    <shiro:hasPermission name="subgoods:add">
        <button id="addInfoBt" class="btn btn-info margin-r10">添加</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="subgoods:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <button onclick="_past(1)" class="btn btn-info margin-r10">上架</button>
    <button onclick="_past(0)" class="btn btn-info margin-r10">下架</button>
    <%--<shiro:hasPermission name="subgoods:delete">--%>
        <%--<button id="deleteBt" class="btn btn-info margin-r10">删除</button>--%>
    <%--</shiro:hasPermission>--%>
    <!-- 筛选条件 结束 -->
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/subgoods/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="imgLoadPath" align="center" data-options="width:100,
                formatter: function(value, row, index){
                    return '<img src='+ value +' width=\'20\'/>';
                }
            ">商品图片</th>
            <%--<th field="status" align="center" data-options="width:100,
            formatter:function(value,row,index){
                return value == '0' ? '开启' : '关闭';
            }
            ">状态</th>--%>
            <th field="goodsCode" align="center" data-options="width:100">商品代码</th>
            <th field="goodsName" align="center" data-options="width:100">商品名称</th>
            <th field="goodsTotalNum" align="center" data-options="width:100">商品总数</th>
            <th field="goodsLeftNum" align="center" data-options="width:100">剩余商品数量</th>
            <th field="subScale" align="center" data-options="">认购比例</th>
            <th field="goodsSubPrice" align="center" data-options="width:100">认购原始价格(元)</th>
            <th field="goodsCost" align="center" data-options="width:100">商品基准价(现价)(元)</th>
            <th field="status" align="center" data-options="width:100,
            formatter: function(value, row, index){

                if('1' == value){
                    return '认购';
                }else if('2' == value){
                    return '购销';
                }else if('3' == value){
                    return '下架'
                }else{}
            }">状态</th>
            <th field="subTotalNum" align="center">认购总数</th>
            <th field="subMakeNum" align="center">认购持仓数量</th>
            <th field="subSendNum" align="center">认购发货数量</th>
            <th field="userMobile" align="center">所属客户手机号码</th>
            <th field="createTime" align="center">发行时间</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/subgoods/add"></div>
<div id="editWindow" url="${ctx}/subgoods/update"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/contract/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["goodsName"]
        };
        var topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })
    function _past(s){
        var row = $('#listDataGrid').datagrid('getSelected');
        if(!row){
            layer.msg('请选择', {
                offset: getLayerCenter()
            });
            return;
        }
        var str = s == '1' ? '确认上架 ['+(row.goodsName)+'] 商品？' : '确认下架 ['+(row.goodsName)+'] 商品？';
        $.messager.confirm('确认', str, function (r) {
            if(r){
                $.ajax({
                    url:'${ctx}/subgoods/' + (s == '1' ? 'open' : 'close'),
                    data: {id: row.id},
                    type: 'POST',
                    success: function(result){
                        console.log(result);
                        if (result.code == 0) {
                            layer.msg(result.msg);
                            /*showSuccess();*/
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
</script>
</body>
</html>