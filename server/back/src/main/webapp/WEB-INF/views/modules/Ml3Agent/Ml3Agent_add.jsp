<%@ page import="com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean" %>
<%@ page import="java.util.List" %>
<%@ page import="com.rmkj.microcap.modules.Ml3OperateCenter.entity.Ml3OperateCenterBean" %><%--
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
            <div class="form-group row">
                <div class="col-md-2">
                    <label>账号</label>
                    <input type="text" class="form-control span1" name="account" placeholder="账号:20字以内" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>手机号</label>
                    <input type="text" class="form-control span1" name="mobile" placeholder="手机号:20字以内" maxlength="100" required>
                </div>

            </div>
            <div class="form-group row">
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>密码</label>
                    <input type="text" class="form-control span1" name="safePassword" placeholder="密码:20字以内" maxlength="100" required>
                </div>
                <div class="col-md-2">
                    <label>所属VIP合伙人</label>
                    <select  style="width: 250px;height: 34px;" id="se"  class="form-control span1" name="unitsId">
                        <option>请选择</option>
                        <%
                            List<Ml3MemberUnitsBean> OcList = (List<Ml3MemberUnitsBean>)request.getAttribute("list");
                            for(int i = 0;i<OcList.size();i++){
                        %>
                        <option id="<%=OcList.get(i).getId()%>" value="<%=OcList.get(i).getId()%>"><%=OcList.get(i).getName()%></option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </div>
            <%--<div class="form-group row">--%>
                <%--<div class="col-md-2">--%>
                    <%--<label>是否屏蔽军团用户手机号</label>--%>
                    <%--<select  style="width: 250px;height: 34px;" id="se1"  class="form-control span1" name="screen">--%>
                        <%--<option>请选择</option>--%>
                        <%--<option id="1" value="是">是</option>--%>
                        <%--<option id="2" value="否">否</option>--%>
                    <%--</select>--%>
                <%--</div>--%>
            <%--</div>--%>
            <button type="submit" id="sub" class="btn btn-primary">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        $("#sub").click(function () {
            var mobile = $("input[name=mobile]").val();//输入的手机号
            var account = $("input[name=account]").val();//输入的账号

            var array = new Array();
            <c:forEach items="${mobileList}" var="item">
            array.push("${item}")
            </c:forEach>
            for(var i = 0;i<array.length;i++){
                if(mobile == array[i]){
                    alert("手机号已存在,请重新输入");
                    return;
                }
            }
            var array1 = new Array();
            <c:forEach items="${accountList}" var="item1">
            array1.push("${item1}")
            </c:forEach>
            for(var i = 0;i<array1.length;i++){
                if(account == array1[i]){
                    alert("账号名已存在,请重新输入");
                    return;
                }
            }
            if(escape(account).indexOf( "%u" )>=0){
                alert("不能输入汉字，请重新输入");
                return;
            }
            infoAjaxSubmit(this,'${ctx}/Ml3Agent/save');
        })
    })
</script>
</body>
</html>