<%--
Created by CodeGenerator.
User: Administrator
Date: 2016-10-17
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
                    <label>设置类型</label>
                    <input type="text" class="form-control span1" id="shezhi" placeholder="设置类型"  maxlength="100" required value="" readonly>
                </div>
                    <div class="col-md-2">
                        <label>开始时间</label>
                        <input type="text" id="time" class="form-control span1" name="openTime" placeholder=""  maxlength="20" required value="${model.openTime}">
                    </div>
                    <div class="col-md-2">
                        <label>结束时间</label>
                        <input type="text" id="time1" class="form-control span1" name="closeTime" placeholder=""  maxlength="20" required value="${model.closeTime}">
                    </div>
            </div>

            <button type="submit" id="sub" class="btn btn-primary">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function(){
        var id = $("input[type=hidden]").val();
        if(id == "balance"){
            document.getElementById("shezhi").value = "结算时间";
        }else{
            document.getElementById("shezhi").value = "交易时间";
        }
        $("#sub").click(function(){

        var beginTime = $("input[name=openTime]").val();
        var endTime = $("input[name=closeTime]").val();
        var arr = new Array();
        arr = beginTime.split(":");
        var back = arr[1];
        var before = arr[0];
        if(!isNaN(back)&&!isNaN(before)) {
            if(before.length>2||back.length>2){
                alert("时间格式错误！请重新输入！");
                return;
            }
            if (before < 10) {
                if (before.length == 2) {
                    document.getElementById("time").value = before + ":" + back;
                } else if (before.length == 1) {
                    before = "0" + before;
                    document.getElementById("time").value = before + ":" + back;
                }
            }
            if (before > 24 || beginTime.indexOf(":") == -1) {
                alert("时间格式错误！请重新输入！");
                $("input[name=openTime]").focus();
                return;
            }
            if (back > 60) {
                alert("时间格式错误！请重新输入！");
                $("input[name=openTime]").focus();
                return;
            }
            if (back == 60) {
                before = parseInt(before) + 1;
                if (before < 25) {
                    if(before.length==2){
                        document.getElementById("time").value= before+":00";
                    }else {
                        if(before == 9){
                            document.getElementById("time").value= "0"+before+":00";
                        }else{
                            document.getElementById("time").value = before + ":00";
                        }

                    }
                } else {
                    alert("时间格式错误！请重新输入！");
                }
            }
            if (back < 10) {
                if (back.length == 2) {
                    document.getElementById("time").value = before + ":" + back;
                } else {
                    document.getElementById("time").value = before + ":0" + back;
                }
            }
            if (before == 24 && back != 00) {
                document.getElementById("time").value = "00:" + back;
            }
        }else{
            alert("输入非法字符！请重新输入！");
            return;
        }
            var arr1 = new Array();
            arr1 = endTime.split(":");
            var back1 = arr1[1];
            var before1 = arr1[0];
            if(!isNaN(back1)&&!isNaN(before1)) {
                if(before1.length>2||back1.length>2){
                    alert("时间格式错误！请重新输入！");
                    return;
                }
                if (before1 < 10) {
                    if (before1.length == 2) {
                        document.getElementById("time1").value = before1 + ":" + back1;
                    } else if (before1.length == 1) {
                        before1 = "0" + before1;
                        document.getElementById("time1").value = before1 + ":" + back1;
                    }
                }
                if (before1 > 24 || endTime.indexOf(":") == -1) {
                    alert("时间格式错误！请重新输入！");
                    $("input[name=endTime]").focus();
                    return;
                }
                if (back1 > 60) {
                    alert("时间格式错误！请重新输入！");
                    $("input[name=endTime]").focus();
                    return;
                }
                if (back1 == 60) {
                    before1 = parseInt(before1) + 1;
                    if (before1 < 25) {
                        if(before1.length==2){
                            document.getElementById("time1").value= before1+":00";
                        }else {
                            if(before1 == 9){
                                document.getElementById("time1").value= "0"+before1+":00";
                            }else{
                                document.getElementById("time1").value = before1 + ":00";
                            }

                        }
                    } else {
                        alert("时间格式错误！请重新输入！");
                    }
                }
                if (back1 < 10) {
                    if (back1.length == 2) {
                        document.getElementById("time1").value = before1 + ":" + back1;
                    } else {
                        document.getElementById("time1").value = before1 + ":0" + back1;
                    }
                }
                if (before1 == 24 && back1 != 00) {
                    document.getElementById("time1").value = "00:" + back1;
                }
            }else{
                alert("输入非法字符！请重新输入！");
                return;
            }
        infoAjaxSubmit(this,'${ctx}/tradebalancetime/update')
        })

    })

</script>
</body>
</html>