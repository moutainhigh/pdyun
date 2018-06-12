<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body style="background: #ffffff;border-top:3px solid #3c8dbc;">
<div class="panel panel-default">
    <div class="panel-body" style="padding:10px 10px 10px 20px;border: none">
        <form enctype="multipart/form-data">
            <input type="hidden" name="id" value="${it.id}">
            <div class="form-group row">
                <div class="col-md-7">
                    <label>标题</label>
                    <input type="text" class="form-control span1" name="title" placeholder=""  maxlength="100" required value="${it.title}">
                    <input id="content" type="hidden" name="content"/>
                    <input id="picurl" type="hidden" name="picurl" value="${it.picurl}"/>
                </div>
                <div class="col-md-7">
                    <label>描述</label>
                    <textarea class="form-control span1" name="description" placeholder="" required>${it.description}</textarea>
                </div>
                <div class="col-md-7">
                    <label>图文链接地址（不填就是默认链接到图文详情）</label>
                    <input type="text" class="form-control span1" name="url" placeholder="" value="${it.url}">
                </div>
                <%--<div class="col-md-2">--%>
                    <%--<label>状态</label>--%>
                    <%--<select class="form-control span1" name="status" required>--%>
                        <%--<option selected="selected" value="0">正常</option>--%>
                        <%--<option value="1">关闭</option>--%>
                    <%--</select>--%>
                <%--</div>--%>
                <div class="form-group row">
                    <div class="col-md-7">
                        <input type="hidden" name="type" value="${type}"/>
                        <label>图文图片</label>
                        <input type="file" class="form-control span1" name="file">
                        <%--<button type="submit" class="btn btn-primary" onclick="uploadPic(this);" style="float: right;">上传</button>--%>
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-xs-2" style="width: 830px">
                    <label class="control-label">图文详情</label>
                    <div id="ckEditor" >${it.content}</div>
                </div>
            </div>
            <button id="form_s" type="submit" class="btn btn-primary">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script type="application/javascript">
    var CkEditor = new CkEditorCustom({
        height: 300,
        $targetInput: $("#content")
    });
    $("#form_s").on("click", function () {
        if (CkEditor.setInputDatas() == false) {
            layer.msg('请填写内容', {
                offset: getLayerCenter()
            });
            return false;
        }
        infoAjaxSubmit(this,'${ctx}/weChatPublic/articles/save');
    });

//    function uploadPic(the){
//        infoAjaxSubmit(the, ctx + '/pic/upload', function (result) {
//            $('#picurl').val(result);
//        });
//    }
</script>
</body>
</html>