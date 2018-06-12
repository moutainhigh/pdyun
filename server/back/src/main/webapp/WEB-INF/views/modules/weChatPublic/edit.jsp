<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body style="background: #ffffff;border-top:3px solid #3c8dbc;">
<div class="panel panel-default" style="margin:5px;">
    <div class="panel-body" style="padding:10px 10px 10px 20px;border: none; overflow-x:hidden;">
        <form>
			<input type="hidden" name="id" value="${it.id}"/>
            <div class="form-group row">
            	<div class="col-md-6">
	                <label>公众号名称</label>
	                <input type="text" class="form-control span1" name="name" value="${it.name}" required>
	            </div>
				<div class="col-md-6">
					<label>appId</label>
					<input type="text" class="form-control span1" name="appId" value="${it.appId}" required>
				</div>
				<div class="col-md-6">
					<label>appSecret</label>
					<input type="text" class="form-control span1" name="secret" value="${it.secret}">
				</div>
				<div class="col-md-6">
					<label>域名</label>
					<input type="text" class="form-control span1" name="domainName" value="${it.domainName}" required>
				</div>
				<div class="col-md-6" id="d3" style="">
					<label id="lab3">状态</label>
					<select class="form-control span1" name="status">
						<option value="0" ${it.status == '0' ? 'selected="selected"' : ''} >默认</option>
						<option value="1" ${it.status == '1' ? 'selected="selected"' : ''}>主要</option>
					</select>
				</div>
            </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/weChatPublic/save')" style="float: right;">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>

</script>
</body>
</html>
