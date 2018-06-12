<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/18
  Time: 14:44
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
            <input type="hidden" name="id" value="${id}" id="id">
            <div class="form-group row">
                <div class="col-md-2">
                    <label>划转数量</label>
                    <input type="text" class="form-control span1" name="transNum" id="transNum" placeholder="请输入划转数量"  maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>划转客户手机号</label>
                    <input type="text" class="form-control span1" name="mobile" id="mobile" placeholder="请输入划转客户手机号" maxlength="100" required>
                </div>
            </div>
        </form>
        <button class="btn btn-info" id="btn">确定划转客户</button>
    </div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script type="application/javascript">
    $("#btn").on("click", function () {
        var id = $("#id").val();
        var transNum = $("#transNum").val();
        var mobile = $('#mobile').val();
        $.ajax({
            url: ctx + '/trade/transHold',
            type: 'POST',
            data: {
                id: id,
                transNum: transNum,
                mobile: mobile
            },
            success: function (data) {
                layer.msg(data.msg);
            },
            error: function (error) {
                console.log(error);
            }
        })
    });
</script>
</body>
</html>
