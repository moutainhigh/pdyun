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
                    <label>合约代码</label>
                    <input type="text" class="form-control span1" name="code" placeholder="50字以内"  maxlength="50" required value="${model.code}">
                </div>
                <div class="col-md-2">
                    <label>合约名称</label>
                    <input type="text" class="form-control span1" name="name" placeholder="50字以内" maxlength="50" required value="${model.name}">
                </div>
                <div class="col-md-2">
                    <label>显示次序</label>
                    <input type="number" class="form-control span1" name="orderNo" placeholder="正整数" required value="${model.orderNo}">
                </div>
                <div class="col-md-2">
                    <label>开始时间</label>
                    <input type="text" id="time" class="form-control span1" name="beginTime" placeholder=""  maxlength="50" required value="${model.beginTime}">
                </div>
                <div class="col-md-2">
                    <label>结束时间</label>
                    <input type="text" id="time1" class="form-control span1" name="endTime" placeholder=""  maxlength="50" required value="${model.endTime}">
                </div>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>盈利百分比(市场模式)</label>
                    <input type="text" class="form-control span1" name="profitPercentages" placeholder="50字以内"  maxlength="50" required value="${model.profitPercentages}">
                </div>
                <div class="col-md-2">
                    <label>损失百分比(市场模式)</label>
                    <input type="text" class="form-control span1" name="lossPercentages" placeholder="50字以内"  maxlength="50" required value="${model.lossPercentages}">
                </div>
                    <div class="col-md-2">
                        <label>金额设置(市场模式)</label>
                        <input type="text" class="form-control span1" name="stepMoneys" placeholder="50字以内"  maxlength="50" required value="${model.stepMoneys}">
                    </div>
                <div class="col-md-2">
                    <label>手续费设置(市场模式)</label>
                    <input type="text" class="form-control span1" name="fees" placeholder="50字以内"  maxlength="50" required value="${model.fees}">
                </div>
                <div class="col-md-2">
                    <label>点值设置(市场模式)</label>
                    <input type="text" class="form-control span1" name="pointMoneys" placeholder="50字以内"  maxlength="50" required value="${model.pointMoneys}">
                </div>
                    <%--<div class="col-md-2">--%>
                        <%--<label>时间间隔设置</label>--%>
                        <%--<input type="text" class="form-control span1" name="offTimes" placeholder="50字以内"  maxlength="50" required value="${model.offTimes}">--%>
                    <%--</div>--%>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <label>点位设置</label>
                    <input type="text" class="form-control span1" name="offPoints" placeholder="50字以内"  maxlength="50" required value="${model.offPoints}">
                </div>
                <div class="col-md-2">
                    <label>收益率设置</label>
                    <input type="text" class="form-control span1" name="percentProfits" placeholder="50字以内"  maxlength="50" required value="${model.percentProfits}">
                </div>
                <div class="col-md-2">
                    <label>是否开启</label>
                    <select class="form-control span1" name="status" required>
                        <option ${model.status == 0 ? 'selected="selected"' : ''} value="0">开启</option>
                        <option ${model.status == 1 ? 'selected="selected"' : ''} value="1">关闭</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <label>交易模式</label>
                    <select class="form-control span1" name="model" required>
                        <option ${model.status == 0 ? 'selected="selected"' : ''} value="0">市场模式</option>
                        <%--<option ${model.status == 1 ? 'selected="selected"' : ''} value="1">时间模式</option>--%>
                        <option ${model.status == 2 ? 'selected="selected"' : ''} value="2">点位模式</option>
                        <option ${model.status == 3 ? 'selected="selected"' : ''} value="3">全部模式</option>
                    </select>
                </div>
            </div>
            <div class="form-group row">


            </div>
            <%--<button type="button" id="but">测试的button</button>--%>
            <button type="submit" id="sub" class="btn btn-primary" >提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    $(function(){
        $("#sub").click(function () {
            var beginTime = $("input[name=beginTime]").val();
            var endTime = $("input[name=endTime]").val();

            var arr = new Array();
            arr = beginTime.split(":");
            var back = arr[1];
            var before = arr[0];
            if(!isNaN(back)&&!isNaN(before)){

                if(before.length>2||back.length>2){
                    alert("时间格式错误！请重新输入！");
                    return;
                }
                if (before < 10) {
                    if (before.length==2) {
                        document.getElementById("time").value = before + ":" + back;
                    } else if(before.length == 1){
                        before = "0"+before;
                        document.getElementById("time").value = before + ":" + back;
                    }
                }
                if (before > 24 || beginTime.indexOf(":")==-1) {
                    alert("时间格式错误！请重新输入！");
                    $("input[name=beginTime]").focus();
                    return;
                }
                if(back>60){
                    alert("时间格式错误！请重新输入！");
                    $("input[name=beginTime]").focus();
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
                if(back<10){
                    if(back.length==2){
                        document.getElementById("time").value=before+":"+back;
                    }else{
                        document.getElementById("time").value=before+":0"+back;
                    }
                }
                if(before==24&&back!=00){
                    document.getElementById("time").value="00:"+back;
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
            infoAjaxSubmit(this,'${ctx}/contract/update');
        })
    })
</script>
</body>
</html>