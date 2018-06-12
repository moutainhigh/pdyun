<%@ page import="java.util.List" %>
<%@ page import="com.rmkj.microcap.common.modules.sys.bean.SysDictBean" %><%--
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
            <div class="form-group row">
                <div class="col-md-2">
                    <label>代金券面值</label>
                    <input type="text" class="form-control span1" name="money" placeholder="请输入代金券面值"  maxlength="100" required id="m">
                </div>
                <div class="col-md-2">
                    <label>类型</label><br/>
                    <select  style="width: 242px;height: 34px;" id="se"  class="form-control span1">
                        <option>请选择</option>
                    <%
                        List<SysDictBean> list = (List<SysDictBean>)request.getAttribute("list");
                        for(int i = 0;i<list.size();i++){
                            %>
                        <%--<input type="hidden" value="<%=list.get(i).getType()%>" name="type">--%>

                        <option id="<%=list.get(i).getId()%>" value="<%=list.get(i).getValue()%>"><%=list.get(i).getLabel()%></option>
                    <%
                        }
                    %>
                    </select>

                    <%--<input type="text" class="form-control span1" name="type" placeholder="请输入代金券类型" maxlength="100" required value="">--%>
                </div>
                <%--<div class="col-md-2">--%>
                    <%--<label>标签</label>--%>
                    <%--<input type="text" class="form-control span1" name="label" placeholder="例如：20元代金券" maxlength="100" required>--%>
                <%--</div>--%>
                <%--<div class="col-md-2">--%>
                    <%--<label>排序</label>--%>
                    <%--<input type="text" class="form-control span1" name="sort" placeholder="请输入代金券的排序" maxlength="100" required>--%>
                <%--</div>--%>
            </div>

            <button type="submit" class="btn btn-primary" id="sub">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function () {
        var selectid;
        $("#se").change(function () {
            selectid = $('#se option:selected').attr("value");
        })
        $("#sub").click(function () {
            var selectmoney = $("#m").val();
            infoAjaxSubmit(this,'${ctx}/cashcoupon/save?type='+selectid+'&money='+selectmoney);
        })
    })
</script>
</body>
</html>