<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/2
  Time: 9:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <style type="text/css">
        ul li{
            float: left;
            list-style: none;
            padding:0;
            margin:0;
            border: 1px solid #808080;
            width:8rem;
            height: 3rem;
            text-align: center;
            line-height: 2rem;
            border-radius: 5%;
        }
        .on-color{
            background-color: #00A0E9;
        }
    </style>
</head>
<body style="padding: 20px; padding-left: -3rem;">
    <div class="container" style="margin-bottom: 3rem;">
        <ul style="list-style: none;">
            <li  class="on-color">出金记录</li>
            <li>申请出金</li>
        </ul>
    </div>
    <!-- 提现表单 -->
    <form class="form-horizontal" style="display: none;">
        <div class = "form-group">
            <label class="col-sm-2 control-label" for = "money">出金金额</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="money" name="money" placeholder="请输入出金金额"/>
            </div>
        </div>
        <div class = "form-group">
            <label class="col-sm-2 control-label" for = "fee">出金手续费</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="fee" name="fee" placeholder="请输入出金手续费" disabled = "disabled"/>
            </div>
        </div>
        <div class = "form-group">
            <label class="col-sm-2 control-label" for = "bankName">开户行</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="bankName" placeholder="请输入银行开户行" value="${memberUnits.bankName}"/>
            </div>
        </div>
        <div class = "form-group">
            <label class="col-sm-2 control-label" for = "openBankName">开户行支行</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="openBankName" placeholder="请输入开户行支行" value = "${memberUnits.bankChildName}"/>
            </div>
        </div>
        <div class = "form-group">
            <label class="col-sm-2 control-label" for = "">省份</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="" placeholder="请输入省份" disabled = "disabled"/>
            </div>
        </div>
        <div class = "form-group">
            <label class="col-sm-2 control-label" for = "">城市</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="" placeholder="请输入城市" disabled = "disabled"/>
            </div>
        </div>
        <div class = "form-group">
            <label class="col-sm-2 control-label" for = "bankAccount">银行账户</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="bankAccount" placeholder="请输入银行账户" value="${memberUnits.bankAccount}" />
            </div>
        </div>
        <div class = "form-group">
            <label class="col-sm-2 control-label" for = "unitsName">开户名称</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="unitsName" placeholder="请输入开户名称" value="${memberUnits.name}"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 col-sm-offset-2">
                <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmitForWithdraw(this, '${ctx}/Ml3MemberUnits/unitsWithdraw')">提交</button>
            </div>
        </div>
    </form>
    <div id="moneyRecord">
        <table id="listDataGrid" class="easyui-datagrid"  style="width: 100%;" singleSelect="true" pagination="true"
               data-options="
	                url: '${ctx}/Ml3MemberUnitsMoneyRecord/findMemberUnitsMoneyRecordList?id=${id}',
	                lines: true,
	                idField: 'id',
	                striped: true,
	                fitColumns:true,
	                onLoadSuccess:datagridLoadSuccess,
	                resizeHandle:'right'
	            ">
            <thead>
            <tr>
                <th field="unitsName" align="center" data-options="width:100">VIP合伙人名称</th>
                <th field="money" align="center" data-options="width:100">申请金额</th>
                <th field="fee" align="center" data-options="width:100">手续费(1%)</th>
                <th field="reviewStatus" align="center" data-options="width:100,
                formatter:function(value, row, index){
                    return value == 0 ? '处理中': (value == 1 ? '通过' : '不通过');
                }">审核状态</th>
                <th field="bankName" align="center" data-options="width:100">银行名称</th>
                <th field="openBankName" align="center" data-options="width:100">银行开户行名称</th>
                <th field="bankAccount" align="center" data-options="width:100">银行账户</th>
                <th field="realName" align="center" data-options="width:100">开户人姓名</th>
                <th field="createTime" align="center" data-options="width:100,
                formatter: formatDateBoxFull">申请时间</th>
                <%--<th field="type" align="center" data-options="width:100,--%>
                <%--formatter:function(value,row,index){--%>
                <%--return value == '0' ? '充值' : '提现';--%>
                <%--}--%>
                <%--">类型</th>--%>
                <th field="completeTime" align="center" data-options="width:100">完成时间</th>
            </tr>
            </thead>
        </table>
    <%--</div>--%>


    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script type="text/javascript">
    $("#money").keyup(function (e) {
        var money = Number($("#money").val());
        if (!/^\d+$|^\d+\.\d+$/g.test(money)) {
            layer.msg("请输入正确的金额",function () {});
            return false;
        }
        $("#fee").val(money * (1 / 100));
    });

    /*操作表头*/
    $("li").on("click", function () {
        $(this).addClass("on-color").siblings().removeClass("on-color");
        if($(this).index() === 0){
            /*$("#moneyRecord").show();
            $(".form-horizontal").hide();*/
            window.location.href = ctx + '/Ml3MemberUnits/getUnitsWithdraw';
        }else if($(this).index() === 1){
            $("#moneyRecord").hide();
            $(".form-horizontal").show();
        }
    })



    /**
     * 表单页面的提交操作
     */
    function infoAjaxSubmitForWithdraw(source, url, callback) {
        console.log(source);
        var options = {
            url: url,
            dataType: 'json',
            type: "POST",
            beforeSend: function () {
                ajaxLayerLoadingIndex = layer.load(1, {
                    offset: getLayerCenter(),
                    shade: [0.2, '#000000']
                });
            },
            complete: function () {
                layer.close(ajaxLayerLoadingIndex);
            },
            success: function (result) {
                $('#listDataGrid').datagrid('reload');
                layer.msg(result.msg);
            }
        };
        $(source).parent().parent().parent().ajaxForm(options);
    }

</script>
</body>
</html>
