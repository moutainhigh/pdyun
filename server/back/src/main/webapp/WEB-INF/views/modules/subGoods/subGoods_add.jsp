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
<%--<div class="panel panel-default">--%>
    <div class="panel-body" style="padding:10px 10px 10px 20px;border: none">
        <form enctype="multipart/form-data">
            <div class="form-group row">
                <div class="col-md-2">
                    <label>商品图片</label>
                    <input type="file" name="imgPath" placeholder="请选择图片"  maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>商品详情图片</label>
                    <input type="file" name="imgDetailPath" placeholder="请选择图片"  maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>商品代码</label>
                    <input type="text" class="form-control span1" name="goodsCode" placeholder="请输入商品代码" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>商品名称</label>
                    <input type="text" class="form-control span1" name="goodsName" placeholder="请输入商品名称" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>商品总数</label>
                    <input type="text" class="form-control span1" name="goodsTotalNum" placeholder="请输入商品总数" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>可认购百分比(%)</label>
                    <input type="text" class="form-control span1" name="subScale" placeholder="请输入可认购百分比(%)" maxlength="100" required>
                </div>

                <%--<div class="col-md-2">--%>
                    <%--<label>是否启用</label>--%>
                    <%--<select class="form-control span1" name="mIsuse" required>--%>
                        <%--<option selected="selected" value="0">是</option>--%>
                        <%--<option value="1">否</option>--%>
                    <%--</select>--%>
                <%--</div>--%>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>认购原始价格</label>
                    <input type="text" class="form-control span1" name="goodsSubPrice" placeholder="请输入认购原始价格" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>认购总数(不同规格请用英文逗号分隔',')</label>
                    <input type="text" class="form-control span1" name="subTotalNum" placeholder="请输入认购总数" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>持仓数量(不同规格请用英文逗号分隔',')</label>
                    <input type="text" class="form-control span1" name="subMakeNum" placeholder="请输入认购持仓数量" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>发货数量(不同规格请用英文逗号分隔',')</label>
                    <input type="text" class="form-control span1" name="subSendNum" placeholder="请输入认购发货数量" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>认购天数</label>
                    <input type="text" class="form-control span1" name="subDays" placeholder="请输入认购天数" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>所属客户手机号</label>
                    <input type="text" class="form-control span1" name="userMobile" placeholder="请输入商品所属客户手机号" maxlength="100" required>
                </div>

            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>产品种类：</label>
                    <select style="height:35px;" name="goodsType" >
                        <option value="">--请选择--</option>
                        <c:forEach var="it" items="${goodsTypeList}">
                            <option value="${it.id}">${it.typeName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/subgoods/add')">提交</button>
        </form>
    </div>
<%--</div>--%>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>