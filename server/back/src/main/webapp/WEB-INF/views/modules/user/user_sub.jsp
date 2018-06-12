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
    <style type="text/css">
        select{
            width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;
        }
    </style>
</head>
<body>
<div region="north" style="padding:5px;" border="false">
    <!-- 查询 -->
    <div>
        <label class="input-inline">
            <span>&nbsp;VIP&nbsp;&nbsp;合伙人：</span>
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
            <br><br>

            <span>认&nbsp;购&nbsp;商&nbsp;品&nbsp;：</span>
            <select style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;" id="goodsSelect">
                <option value="">--请选择--</option>
                <c:forEach var="it" items="${goodsList}">
                    <option value="${it.id}">${it.goodsName}</option>
                </c:forEach>
            </select>


            <span>认&nbsp;购&nbsp;总&nbsp;额&nbsp;：</span>
            <input type="text"  id="subMoney" name="subMoney" style="width:150px; height:35px;border: 1px solid #D4D4D4; border-radius: 5px 5px 5px 5px;" placeholder="认购金额(元)" maxlength="100" required />

        </label>
    </div>
    <button class="btn btn-info margin-r10" onclick="subCommit()">确认认购</button>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
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

    function subCommit(){
        var centerId = $('#centerId').val();
        var unitsId = $('#unitsId').val();
        var agentId = $('#agentId').val();
        var goodsId = $('#goodsSelect').val();
        var goodsName = $("#goodsSelect").find("option:selected").text();
        var subMoney = $('#subMoney').val();
        if((centerId==null||centerId=='') && (unitsId==null||unitsId=='') && (agentId==null||agentId=='')){
            layer.msg("请选择要认购的客户!");
            return;
        }
        console.log("goodsId="+goodsId+",null="+(goodsId==null)+",=>"+(goodsId==''));
        if(goodsId==null || goodsId==''){
            layer.msg("请选择要认购的商品!");
            return;
        }
        if(subMoney==null || subMoney==''){
            layer.msg("请输入要认购的金额!");
            return;
        }
        var obj = {
            centerId:centerId,
            unitsId:unitsId,
            agentId:agentId,
            goodsId:goodsId,
            goodsName:goodsName,
            subMoney:subMoney
        };
        $.ajax({
            url: ctx + '/sub/record/addSubRecord',
            type: 'post',
            data: obj,
            success: function (data) {
//                console.log("=========>>>>"+JSON.stringify(data));
                if(data.code == 0){
                    layer.msg("认购成功!");
                    setTimeout(function(){
                        window.location.replace("${ctx}/user/sub?r="+Math.random());
                    },1500);
                }else{
                    layer.msg(data.msg);
                }
            }
        });
    }
</script>
</body>
</html>