<%--
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
            <input type="hidden" name="id" value="${model.id}">
            <div class="form-group row">
                <div class="col-md-2">
                    <label>标题</label>
                    <input type="text" class="form-control span1" name="title" placeholder=""  maxlength="100" required value="${model.title}">
                    <input id="content" type="hidden" name="content"/>
                </div>
                <div class="col-md-2">
                    <label>类型</label>
                    <select class="form-control span1" name="type" required id="type">
                        <option value="1">通知</option>
                        <option value="2">公告</option>
                        <option value="3">热门咨询</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <label>状态</label>
                    <select class="form-control span1" name="status" required id="status">
                        <option value="0">正常</option>
                        <option value="1">关闭</option>
                    </select>
                </div>

            </div>
            <div class="form-group row">
                <div class="col-xs-2" style="width: 830px">
                    <label class="control-label">图文详情</label>
                    <div id="ckEditor" >${model.content}</div>
                </div>
            </div>

            <button type="submit" class="btn btn-primary">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script type="application/javascript">
    $(document).ready(function(){
        var type= ${model.type};
        var status = ${model.status};
        $("#type option[value='"+type+"']").attr("selected","selected");
        $("#status option[value='"+status+"']").attr("selected","selected");
    });


    var CkEditor = new CkEditorCustom({
        height: 300,
        $targetInput: $("#content")
    });
    $(":submit").on("click", function () {
        if (CkEditor.setInputDatas() == false) {
            layer.msg('请填写内容', {
                offset: getLayerCenter()
            });
            return false;
        }
        infoAjaxSubmit(this,'${ctx}/article/update');
    });
</script>
</body>
</html>