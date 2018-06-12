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
            <span>合约名称：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="name" />
            <span>合约代码：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="code" />
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <button onclick="_past(1)" class="btn btn-info margin-r10">开启</button>
    <button onclick="_past(0)" class="btn btn-info margin-r10">关闭</button>
    <!-- 筛选条件 结束 -->
    <%--<shiro:hasPermission name="contract:add">--%>
        <%--<button id="addInfoBt" class="btn btn-info margin-r10">添加</button>--%>
    <%--</shiro:hasPermission>--%>
    <shiro:hasPermission name="contract:edit">
        <button id="editInfoBt" class="btn btn-info margin-r10">编辑</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="contract:delete">
        <button id="deleteBt" class="btn btn-info margin-r10">删除</button>
    </shiro:hasPermission>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/contract/list',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">

        <thead>
        <tr>
            <th field="code" align="center" data-options="width:100">代码</th>
            <th field="name" align="center" data-options="width:100">名称</th>
            <th field="orderNo" align="center" data-options="width:100">显示顺序</th>
            <th field="status" align="center" data-options="width:100,
            formatter:function(value,row,index){
                return value == '0' ? '开启' : '关闭';
            }
            ">状态</th>
            <th field="stepMoneys" align="center" data-options="width:100">购买金额</th>
            <%--<th field="offTimes" align="center" data-options="width:100">时间间隔</th>--%>
            <th field="offPoints" align="center" data-options="">交易点位</th>
            <th field="percentProfits" align="center" data-options="width:100">收益率</th>
<%--            <th field="offTimes" align="center" data-options="width:300,
                formatter:function(value,row,index){
                    var offTimes = value.split(',');
                    var percentProfits = row.percentProfits.split(',');
                    var val = [];
                    for(var i = 0;i < offTimes.length;i++){
                        val.push(offTimes[i]+'-'+percentProfits[i]);
                    }
                    return val.join(' ,  ');
                }
            ">时间间隔-收益率</th>--%>
            <th field="pointMoneys" align="center" data-options="width:100">点值(市场交易)</th>
            <th field="fees" align="center" data-options="width:100">手续费(市场模式)</th>
            <th field="profitPercentages" align="center" data-options="width:100">盈利百分比(市场模式)</th>
            <th field="lossPercentages" align="center" data-options="width:100">亏损百分比(市场模式)</th>
            <%--<th field="percentProfits" align="center" data-options="width:100">收益率</th>--%>
            <%--<th field="offTimes" align="center" data-options="width:100">时间间隔</th>--%>
            <th field="beginTime" align="center" data-options="width:100">开市时间</th>
            <th field="endTime" align="center" data-options="width:100">休市时间</th>
            <th field="model" align="center" data-options="
                formatter: function(value, row , index){
                    if('0' == value){
                        return '微盘模式';
                    }else if('1' == value){
                        return '时间模式';
                    }else if('2' == value){
                        return '点位模式';
                    }else if('3' == value){
                        return '全部模式';
                    }else{}
                }
            ">适应交易模式</th>
        </tr>
        </thead>
    </table>
</div>
<div id="addWindow" url="${ctx}/contract/add"></div>
<div id="editWindow" url="${ctx}/contract/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/contract/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["name","code"]
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
        var str = s == '1' ? '确认开启 ['+(row.name)+'] 合约？' : '确认关闭 ['+(row.name)+'] 合约？';
        $.messager.confirm('确认', str, function (r) {
            if(r){
                $.ajax({
                    url:'${ctx}/contract/' + (s == '1' ? 'open' : 'close'),
                    data: {id: row.id},
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
</script>
</body>
</html>