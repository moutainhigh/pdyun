<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<link href="${ctxStatic }/login/css/login.css" rel="stylesheet" type="text/css">
	<title>登录</title>
</head>
<body>
<script type="text/javascript">
if (self != top) {
	top.location = self.location;
};
</script>
<div class="login">
	<form method="post" id="form" action="${ctx}/login" role="form" >
        <div class="logo"><img src="${ctxStatic}/login/img/02(1).png" /></div>
		<div class="login_form">
			<div class="user">
                <div class="user_box"><label>用户名：</label><input class="text_value" name="username" type="text" placeholder="用户名"></div>
				<div class="user_box"><label>密&nbsp;&nbsp;&nbsp;码：</label><input class="text_value" name="password" type="password" placeholder="密码"></div>
				<input type="hidden" name="pwd" id="pwd">
				<div class="user_box user_box2"><label>验证码：</label><sys:validateCode name="captcha" /></div>
                <button class="button" id="submit" type="submit">登 录</button>
			</div>
		</div>
	</form>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
	$(function(){
		$("#submit").click(function () {
			var pwd = $("input[name=password]").val();
			var str = document.getElementById("pwd").value = pwd;
			var s = document.cookie="str="+str;
		})
		var message = "${error_msg}";
		if(message){
			layer.msg(message);
		}
	})
</script>
</body>
</html>


