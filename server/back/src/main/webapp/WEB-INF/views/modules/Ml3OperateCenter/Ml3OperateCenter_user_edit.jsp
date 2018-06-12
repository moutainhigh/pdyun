<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/14
  Time: 14:25
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
                    <input type="hidden" name="id" value="${ml3AgentBean.id}">
                    <div class="form-group row">
                        <div class="col-md-2">
                            <label>管理员账号</label>
                            <input type="text" class="form-control span1" name="account" placeholder="管理员账号:20字以内"  maxlength="100" value="${ml3AgentBean.account}" required>
                        </div>
                        <div class="col-md-2">
                            <label>中文姓名</label>
                            <input type="text" class="form-control span1" name="realName" placeholder="中文姓名:20字以内" maxlength="100" value="${ml3AgentBean.realName}" required>
                        </div>
                        <div class="col-md-2">
                            <label>手机号</label>
                            <input type="text" class="form-control span1" name="mobile" placeholder="手机号:请输入正确的手机号码" maxlength="100" value="${ml3AgentBean.mobile}" required>
                        </div>
                        <div class="col-md-2">
                            <label>是否启用</label>
                            <select class="form-control span1" name="status" required>
                                <c:if test="${ml3AgentBean.status eq '0'}">
                                    <option selected="selected" value="0">是</option>
                                    <option value="1">否</option>
                                </c:if>
                                <c:if test="${ml3AgentBean.status eq '1'}">
                                    <option selected="selected" value="1">否</option>
                                    <option value="0">是</option>
                                </c:if>
                            </select>
                        </div>
                    </div>
                    <%--<div class="form-group row">--%>
                        <%--<div class="col-md-2">--%>
                            <%--<label>是否内置</label>--%>
                            <%--<select class="form-control span1" name="mIsposwerful" required>--%>
                                <%--<option value="0">是</option>--%>
                                <%--<option selected="selected" value="1">否</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/Ml3OperateCenter/updateOperateCenterUser')">提交</button>
                </form>
            </div>
        </div>

        <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    </body>
</html>