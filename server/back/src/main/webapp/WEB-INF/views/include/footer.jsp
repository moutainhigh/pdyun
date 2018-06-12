<%--
  Created by IntelliJ IDEA.
  User: zhangbowen
  Date: 2015/7/11
  Time: 18:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- jQuery -->
<script src="${ctxStatic}/bootstrapTemplate/bower_components/jquery/dist/jquery-1.11.1.min.js"></script>
<%--json--%>
<script src="${ctxStatic}/jquery-json/jquery.json.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic}/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/easyUI/easyui-lang-zh_CN.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="${ctxStatic}/bootstrapTemplate/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="${ctxStatic}/bootstrapTemplate/bower_components/metisMenu/dist/metisMenu.min.js"></script>
<!-- DataTables JavaScript -->
<%--<script src="${ctxStatic}/bootstrapTemplate/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>--%>
<%--<script src="${ctxStatic}/bootstrapTemplate/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>--%>
<!-- Custom Theme JavaScript -->
<script src="${ctxStatic}/bootstrapTemplate/dist/js/all-custom.js"></script>
<%--上传图片控件--%>
<script src="${ctxStatic}/bootstrapTemplate/bower_components/bootstrap-fileinput/js/fileinput.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrapTemplate/bower_components/bootstrap-fileinput/js/fileinput_locale_zh.js" type="text/javascript"></script>

<script src="${ctxStatic}/modules/commonTools.js"></script>
<%--消息提醒控件--%>
<%--<script src="${ctxStatic}/sweetalert/sweetalert.min.js"></script>--%>
<%--表单提交--%>
<script src="${ctxStatic}/bootstrapTemplate/bower_components/jquery/jquery.form.js"></script>
<%--富文本编辑器--%>
<script src="${ctxStatic}/bootstrapTemplate/bower_components/ckeditor/ckeditor.js" ></script>

<script src="${ctxStatic}/bootstrapTemplate/bower_components/layer/layer.js" ></script>
<script src="${ctxStatic}/bootstrapTemplate/bower_components/layer/extend/layer.ext.js" ></script>
<%--layerDate--%>
<script src="${ctxStatic}/bootstrapTemplate/bower_components/laydate/laydate.js" ></script>
<script src="${ctxStatic}/modules/fomartDate.js"></script>
<script>
  if(top!=window){
    var div = $("div[region='center']").attr("style","padding:5px;overflow-y:hidden!important");
  }
</script>