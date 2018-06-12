<%@ page import="com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean" %>
<%@ page import="java.util.List" %><%--
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
            <div class="form-group row">
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
                <div class="col-md-2">
                    <label>账号</label>
                    <input type="text" class="form-control span1" name="account" placeholder="账号:20字以内" maxlength="100" required value="${model.account}">
                </div>
                <div class="col-md-2">
                    <label>手机号</label>
                    <input type="text" class="form-control span1" name="mobile" placeholder="手机号:20字以内" maxlength="100" required value="${model.mobile}">
                </div>
                <div class="col-md-2">
                    <label>密码</label>
                    <input type="text" class="form-control span1" name="safePassword" placeholder="密码:20字以内" maxlength="100" required value="${model.safePassword}">
                </div>
            </div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/Ml3Agent/updateOc')">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var str = "${model.unitsName}";
        var sel = document.getElementById("se");
        for(var i = 0;i<sel.options.length;i++){
            if(sel.options[i].text == str){
                sel.options[i].selected = true;
                break;
            }
        }
    })
</script>
</body>
</html>