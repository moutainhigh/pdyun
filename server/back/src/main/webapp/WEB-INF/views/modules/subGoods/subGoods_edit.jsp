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
<body style="background: #ffffff;border-top:3px solid #3c8dbc;">
<div class="panel panel-default">
    <div class="panel-body" style="padding:10px 10px 10px 20px;border: none">
        <form>
            <input type="hidden" name="id" value="${subGoods.id}">
            <div class="form-group row">
                <div class="col-md-2">
                    <label>商品名称</label>
                    <input type="text" class="form-control span1" name="goodsName" placeholder="请输入要修改的商品名称"  maxlength="50" required value="${subGoods.goodsName}">
                </div>
                <div class="col-md-2">
                    <label>商品认购比例</label>
                    <input type="text" class="form-control span1" name="subScale" placeholder="请输入商品认购比例" maxlength="50" required value="${subGoods.subScale}">
                </div>
                <div class="col-md-2">
                    <label>商品基准价(现价)</label>
                    <input type="text" class="form-control span1" name="goodsCost" placeholder="请输入商品最新价格" value="${subGoods.goodsCost}">
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>认购总数(不同规格请用英文逗号分隔',')</label>
                    <input type="text" class="form-control span1" name="subTotalNum" placeholder=""  maxlength="50" required value="${subGoods.subTotalNum}">
                </div>
                <div class="col-md-2">
                    <label>持仓数量(不同规格请用英文逗号分隔',')</label>
                    <input type="text" class="form-control span1" name="subMakeNum" placeholder=""  maxlength="50" required value="${subGoods.subMakeNum}">
                </div>
                <div class="col-md-2">
                    <label>发货数量(不同规格请用英文逗号分隔',')</label>
                    <input type="text" class="form-control span1" name="subSendNum" placeholder=""  maxlength="50" required value="${subGoods.subSendNum}">
                </div>
            </div>
            <%--<div class="form-group row">--%>
                <%--<div class="col-md-2">--%>
                    <%--<label>状态</label>--%>
                    <%--<select class="form-control span1" name="model" required>--%>
                        <%--<option ${model.status == 0 ? 'selected="selected"' : ''} value="0">认购</option>--%>
                        <%--<option ${model.status == 1 ? 'selected="selected"' : ''} value="1">时间模式</option>--%>
                        <%--<option ${model.status == 2 ? 'selected="selected"' : ''} value="2">购销</option>--%>
                        <%--<option ${model.status == 3 ? 'selected="selected"' : ''} value="3">下架</option>--%>
                    <%--</select>--%>
                <%--</div>--%>
            <%--</div>--%>
            <button type="submit" id="sub" class="btn btn-primary" >提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function(){
        $("#sub").click(function () {
            infoAjaxSubmit(this,'${ctx}/subgoods/update');
        })
    })
</script>
</body>
</html>