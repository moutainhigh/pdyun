<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body style="background: #ffffff;border-top:3px solid #3c8dbc;">
<div class="panel panel-default" style="margin:5px;">
    <div class="panel-body" style="padding:10px 10px 10px 20px;border: none; overflow-x:hidden;">
        <form enctype="multipart/form-data">
			<input type="hidden" name="id" value="${id}"/>
            <div class="form-group row">
            	<div class="col-md-6">
	                <label>公众号二维码图片</label>
	                <input type="file" class="form-control span1" name="file">
	            </div>
            </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/weChatPublic/uploadQrcode')" style="float: right;">上传</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>

</script>
</body>
</html>
