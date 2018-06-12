<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-11-17
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
            <input type="hidden" name="id" value="${model.id}">
            <input type="hidden" name="pwd1" value="${model.safePassword}">
            <div class="form-group row">
                <div class="col-md-2">
                    <label>原密码</label>
                    <input type="password" class="form-control span1" name="pwd" placeholder="原密码:20字以内"  maxlength="100" required>
                </div>

            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>新密码</label>
                    <input type="password" class="form-control span1" name="safePassword" placeholder="新密码:20字以内" maxlength="100" required>
                </div>
            </div>
            <button type="submit" id="sub" class="btn btn-primary" >提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function(){

//获取cookie字符串
        var strCookie=document.cookie;
//将多cookie切割为多个名/值对
        var arrCookie=strCookie.split("; ");
        var str;
//遍历cookie数组，处理每个cookie对
        for(var i=0;i<arrCookie.length;i++){
            var arr=arrCookie[i].split("=");
//找到名称为userId的cookie，并返回它的值
            if("str"==arr[0]){
                str=arr[1];
                break;
            }
        }
//        alert(str);
        $("#sub").click(function(){
            var pwd = $("input[name=pwd]").val();//输入的原密码
            <%--var safePassword = $("input[name=safePassword]").val();--%>
            <%--var array = new Array();--%>
            <%--<c:forEach items="${list}" var="item">--%>
            <%--array.push("${item}")--%>
            <%--</c:forEach>--%>
            <%--for(var i = 0;i<array.length;i++){--%>
                <%----%>
            <%--}--%>
            if(pwd != str){
                alert("原密码错误");
                return;
            }

            infoAjaxSubmit(this,'${ctx}/Ml3Agent/update')
        })
    })


</script>
</body>
</html>