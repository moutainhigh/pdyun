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
            <span>市场管理部：</span>
            <select style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;" id="centerId">
                <option value="">--请选择--</option>
                <c:forEach var="it" items="${centerList}">
                    <option value="${it.id}">${it.name}</option>
                </c:forEach>
            </select>
            <span>VIP合伙人：</span>
            <select style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;" id="unitsId">
                <option value="">--请选择--</option>
                <c:forEach var="it" items="${unitsList}">
                    <option value="${it.id}">${it.name}</option>
                </c:forEach>
            </select>
            <span>合伙人：</span>
            <select style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;" id="agentId">
                <option value="">--请选择--</option>
                <c:forEach var="it" items="${agentList}">
                    <option value="${it.id}">${it.name}</option>
                </c:forEach>
            </select>
            <span>认购商品：</span>
            <select style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;" id="goodsId">
                <option value="">--请选择--</option>
                <c:forEach var="it" items="${goodsList}">
                    <option value="${it.id}">${it.goodsName}</option>
                </c:forEach>
            </select>

            <span>用户手机号：</span>
            <input class="easyui-textbox" data-options="width:150,height:35" type="text" id="mobile" />

            <button id="queryListBt" class="btn btn-info margin-r10">查询</button>
            <button id="clearBarBt" class="btn btn-info margin-r10">清空</button>
        </label>
    </div>
    <button class="btn btn-info margin-r10" onclick="operate('pass')">通过</button>
    <button class="btn btn-info margin-r10" onclick="operate('frozen')">冻结</button>
    <button class="btn btn-info margin-r10" onclick="operate('refuse')">驳回</button>
</div>
<div region="center" style="padding:5px;" border="false">
    <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
           data-options="
	                url: '${ctx}/subgoods/takeGoodsList',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">
        <thead>
        <tr>
            <th field="id" hidden="true">提货id</th>
            <th field="userChnName" align="center" data-options="width:100">用户名</th>
            <th field="userMobile" align="center" data-options="width:100">用户手机号</th>
            <th field="goodsName" align="center" data-options="width:100">商品名称</th>
            <th field="goodsNum" align="center" data-options="width:100">提货数量</th>
            <th field="money" align="center" data-options="width:100">提货金额</th>
            <th field="serviceFee" align="center" data-options="width:100">认购服务费</th>
            <th field="buyFee" align="center" data-options="width:100">买入手续费</th>
            <th field="createTime" align="center" data-options="
            formatter: function(value, row, index){
                   return value.substring(0,value.length-2);
                }
            ">提货时间</th>

            <th field="status" align="center" data-options="width:100,
            formatter:function(value,row,index){
                <%--状态： 1-提货   2-冻结   3-驳回    4-通过--%>
                return value == '1' ? '提货' : (value == '2'?'冻结':(value == '3'?'驳回':'通过'));
            }
            ">状态</th>
            <th field="receiverName" align="center" data-options="width:100">收货人</th>
            <th field="receiverMobile" align="center" data-options="width:100">收货人手机号</th>
            <th field="region" align="center" data-options="width:100,
            formatter:function(value,row,index){
                return value+row.address;
            }">地址</th>
            <th field="centerName" align="center" data-options="width:100">所属市场管理部</th>
            <th field="unitsName" align="center" data-options="width:100">所属VIP合伙人</th>
            <th field="agentName" align="center" data-options="width:100">所属合伙人</th>
        </thead>
    </table>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var operationObj = {
            deleteUrl: ctx + '/sub/delete'
        };
        new BaseOperationBt(operationObj);
        var toolBarInitObj = {
            queryColumns: ["centerId","unitsId","agentId","goodsId","mobile"]
        };
        var topSearchBar = new TopSearchBar();
        topSearchBar.initBar(toolBarInitObj);
    })

    function operate(s){
        var row = $('#listDataGrid').datagrid('getSelected');
        if(!row){
            layer.msg('请选择', {
                offset: getLayerCenter()
             });
            return;
        }
        if(row.status != '1' && row.status != '2'){
            layer.msg("只有提货状态才能操作", {
                offset: getLayerCenter()
            });
            return;
        }
        console.log(s+":"+row.id+","+row.status);
        $.ajax({
            url: '${ctx}/subgoods/goodsOperate',
            data: {id: row.id,
                   operate:s
                  },
            type: 'POST',
            success: function (result) {
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
    <%--function _past(s){--%>
        <%--var row = $('#listDataGrid').datagrid('getSelected');--%>
        <%--if(!row){--%>
            <%--layer.msg('请选择', {--%>
                <%--offset: getLayerCenter()--%>
            <%--});--%>
            <%--return;--%>
        <%--}--%>
        <%--var str = s == '1' ? '确认开启 ['+(row.name)+'] 合约？' : '确认关闭 ['+(row.name)+'] 合约？';--%>
        <%--$.messager.confirm('确认', str, function (r) {--%>
            <%--if(r){--%>
                <%--$.ajax({--%>
                    <%--url:'${ctx}/contract/' + (s == '1' ? 'open' : 'close'),--%>
                    <%--data: {id: row.id},--%>
                    <%--type: 'POST',--%>
                    <%--success: function(result){--%>
                        <%--if (result.code == 0) {--%>
                            <%--showSuccess();--%>
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

    $("select[id='centerId']").on("click", function () {
        var centerId = $(this).val();
        console.log(centerId);
        $.ajax({
            url: ctx + '/Ml3MemberUnits/findMl3MemberUnits',
            type: 'get',
            data: {centerId: centerId},
            success: function (data) {
                console.log(JSON.stringify(data));
                $("select[id='unitsId']").children().remove();
                var option = [];
                option.push('<option value="">请选择</option>')
                for (var i = 0; i < data.unitsList.length; i++){
                    var units = data.unitsList[i];
                    option.push('<option value="' + units.id + '">' + units.name + '</option>');
                }
                $("select[id='unitsId']").append(option.join(''));
            }
        });
    });

    $('select[id="unitsId"]').on("click", function () {
        var unitsId = $(this).val();
        $.ajax({
            url: ctx + '/Ml3Agent/findMl3AgentById',
            type: 'GET',
            data: {unitsId: unitsId},
            success: function (data) {
                console.log(JSON.stringify(data.agentList));
                $("select[id='agentId']").children().remove();
                var option = [];
                option.push('<option value="">请选择</option>')
                for (var i = 0; i < data.agentList.length; i++){
                    var agent = data.agentList[i];
                    option.push('<option value="' + agent.id + '">' + agent.realName + '</option>');
                }
                $("select[id='agentId']").append(option.join(''));
            }
        });
    })
</script>
</body>
</html>