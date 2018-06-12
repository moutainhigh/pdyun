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
            <span>开 始 时 间：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="sellTimeMin" />
            <span>结 束 时 间 ：</span>
            <input class="easyui-datetimebox" data-options="width:150,height:35" type="text" id="sellTimeMax" />

            &nbsp;&nbsp;
            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>

    </div>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/trade/yingkuijuntuan',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right',
	                loadFilter: function(data){
	                var str = JSON.stringify(data.yingkuiTotal.totalMoney);
	                var str1 = JSON.stringify(data.yingkuiTotal.totalDifMoney);
	                if(str == null ){
	                document.getElementById('t1').innerHTML='0元';
	                }
	                if(str1 == null ){
	                document.getElementById('t2').innerHTML='0元';
	                }
	                if(str != null){
	                     document.getElementById('t1').innerHTML=str+'元';
	                }
	                if(str1 != null){
	                     document.getElementById('t2').innerHTML=str1+'元';
	                }
	                    return data.g;
	                }
	            ">

        <thead>
        <tr>
            <th field="junTuanChnName" align="center" data-options="width:100">军团长姓名</th>
            <th field="junTuanMobile" align="center" data-options="width:100">合伙人</th>
            <th field="totalMoney" align="center" data-options="width:100">交易总金额</th>
            <th field="totalDifMoney" align="center" data-options="width:100">盈亏总金额</th>
            <th field="sellTime" align="center" data-options="width:100">交割时间</th>
        </tr>
        </thead>
    </table><br/><br/>
    <div style="float: right">
        <span style="font-size: 15px;">交易统计：</span><label id="t1" style="font-size: 15px;"></label><br/><br/>
        <span style="font-size: 15px;">盈亏统计：</span><label id="t2" style="font-size: 15px;"></label>
    </div>
</div>
<div id="addWindow" url="${ctx}/trade/add"></div>
<div id="editWindow" url="${ctx}/trade/edit"></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/trade/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["sellTimeMin","sellTimeMax"]
        };
        var topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
//        $("#queryListBt").click(function () {
//            document.getElementById("fanyong").innerHTML="";
//        })

    })
</script>
</body>
</html>