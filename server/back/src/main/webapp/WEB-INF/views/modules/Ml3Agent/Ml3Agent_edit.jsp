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
            <input type="hidden" name="id" value="${model.id}">
            <div class="form-group row">
                <div class="col-md-2">
                    <label>账号</label>
                    <input type="text" class="form-control span1" name="account" placeholder="账号:20字以内" maxlength="100" required value="${model.account}">
                </div>
                <div class="col-md-2">
                    <label>手机号</label>
                    <input type="text" class="form-control span1" name="mobile" placeholder="手机号:20字以内" maxlength="100" required value="${model.mobile}">
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
            <div class="form-group row">

                <%--<div class="col-md-2">--%>
                    <%--<label>所属市场管理部</label>--%>
                    <%--<select  style="width: 250px;height: 34px;" id="se1"  class="form-control span1" name="centerId">--%>
                        <%--<option>请选择</option>--%>
                        <%--<%--%>
                            <%--List<Ml3OperateCenterBean> OcList1 = (List<Ml3OperateCenterBean>)request.getAttribute("list1");--%>
                            <%--for(int i = 0;i<OcList1.size();i++){--%>
                        <%--%>--%>
                        <%--<option id="<%=OcList1.get(i).getId()%>" value="<%=OcList1.get(i).getId()%>"><%=OcList1.get(i).getName()%></option>--%>
                        <%--<%--%>
                            <%--}--%>
                        <%--%>--%>
                    <%--</select>--%>
                <%--</div>--%>
                <%--<div class="col-md-2">--%>
                    <%--<label>用户角色</label>--%>
                    <%--<select  style="width: 250px;height: 34px;" id="se2"  class="form-control span1" name="roleType">--%>
                        <%--<option>请选择</option>--%>
                        <%--<option id="0" value="0">合伙人</option>--%>
                        <%--<option id="1" value="1">VIP合伙人用户</option>--%>
                        <%--<option id="2" value="2">市场管理部用户</option>--%>
                    <%--</select>--%>
                <%--</div>--%>
            </div>

            <button type="submit" class="btn btn-primary" id="sub">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function(){
        $("#sub").click(function () {
            var account = $("input[name=account]").val();//输入的账号
            if(escape(account).indexOf( "%u" )>=0){
            alert("不能输入汉字，请重新输入");
                return;
            }
            infoAjaxSubmit(this,'${ctx}/Ml3Agent/update')
        })
        var str = "${model.unitsName}";
        var sel = document.getElementById("se");
        for(var i = 0;i<sel.options.length;i++){
            if(sel.options[i].text == str){
                sel.options[i].selected = true;
                break;
            }
        }

        var str1 = "${model.centerName}";
        var sel2 = document.getElementById("se1");
        for(var j = 0;j<sel2.options.length;j++){
            if(sel2.options[j].text == str1){
                sel2.options[j].selected = true;
                break;
            }
        }

        var str2 = "${model.roleType}";
        var sel3 = document.getElementById("se2");
        for(var j = 0;j<sel3.options.length;j++){
            if(sel3.options[j].id == str2){
                sel3.options[j].selected = true;
                break;
            }
        }


    });
</script>
</body>
</html>