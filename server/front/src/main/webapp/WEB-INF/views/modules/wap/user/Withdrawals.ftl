<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
<#include "/wap/header.ftl">
    <title>${title}</title>
    <link rel="stylesheet" href="${ctxStatic}/css/aa/global.css">
    <link rel="stylesheet" href="${ctxStatic}/css/aa/withdraw.css">
    <link rel="stylesheet" href="${ctxStatic}/css/aa/cd.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/aa/cd.css" />

    <script type="text/javascript"  src="${ctxStatic}/js/rem.js"></script>
  <style>
    .top_div a:before{
      content: "";
      display: inline-block;
      width: 16px;
      height: 28px;
      background:url("${ctxStatic}/img/images2/css_sprites.png");
      background-position-y:-111px;
       background-position-x:0;
       line-height:6rem;
       -webkit-transform: translateY(7px);margin-right: 0.5rem;
    }
  </style>
</head>
<body style="max-width: 768px;margin: 0 auto; background: #101419;">
<div class="wrap"  style="max-width: 768px;">
    <div class="index" style="margin-top: -5rem;">
        <form id="withDraw" class="i-form" method="post" action="#" onSubmit="return false;">
            <#--<ul class="form-box">-->
                <#--<li class="f-line clearfix">-->
                    <#--<label class="f-label">可提取余额</label>-->
                    <#--<em>${user.returnMoney}元</em>-->
                <#--</li>-->
                <#--<li class="f-line clearfix">-->
                    <#--<label class="f-label">本次提取金额</label>-->
                    <#--<input id="n-money1" class="f-input" type="text" placeholder="请输入提取金额" name="money">-->
                <#--</li>-->
            <#--</ul>-->
            <ul class="form-box">
                <li class="f-line clearfix">
                    <label class="f-label">省&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份 &nbsp;&nbsp;&nbsp;&nbsp;|</label>
                    <input class="f-input c-1" id="province" type="text" name="province" placeholder="请选择所在省份" value="<#if bankCard??>${bankCard.province}</#if>" data-validtype="require">
                    <div class="nice-select" style="z-index: 9999;">
                        <ul id="selectProvince" style="display: none; overflow: auto;position: absolute;top: 1rem;left: 2.5rem;font-size: 0.35rem;z-index: 9999;background: rgb(0, 0, 0);height: 5rem;padding-top: 0.1rem;margin-bottom: 0.1rem;width: 4rem;">
                            <#if _province??>
                                <#list _province as provinces>
                                    <li class="province_i" name="${provinces_index}" style="height: 30px;line-height: 30px;overflow: hidden;padding: 0px 10px;cursor: pointer;">${provinces}</li>
                                </#list>
                            </#if>
                        </ul>
                    </div>
                </li>
                <li class="f-line clearfix">
                    <label class="f-label">城&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市 &nbsp;&nbsp;&nbsp;&nbsp;|</label>
                    <input class="f-input c-1" id="city" type="text" name="city" placeholder="请选择所在城市" value="<#if bankCard??>${bankCard.city}</#if>" data-validtype="require">
                    <div class="nice-select" style="z-index: 9999;">
                        <ul id="selectCity" style="display: none; overflow: auto; position:absolute;width:4rem; top:1rem; left:2.5rem;padding-top:5px;font-size: 0.35rem;z-index: 9999; height: 5rem; background: #000000;">

                        </ul>
                    </div>
                </li>
                <li class="f-line clearfix">
                    <label class="f-label">选&nbsp;择&nbsp;银&nbsp;行 &nbsp;&nbsp;&nbsp;&nbsp;|</label>
                    <input class="f-input c-1" id="bank" type="text" name="bankName" placeholder="请选择银行名称" value="<#if bankCard??>${bankCard.bankName}</#if>" data-validtype="require">
                    <#--<div class="nice-select" style="z-index: 9999;">-->
                        <#--<ul id="selectBank" style="display: none; overflow: auto; position: absolute; top: 1rem; left: 2.3rem; z-index: 9999; background: rgb(0, 0, 0); display: block; height: 5rem; padding-top: 5px; width: 4rem; font-size: 0.35rem;">-->
                            <#--<#if _bank??>-->
                                <#--<#list _bank as bank>-->
                                    <#--<li class="bank_i" style="height: 30px;line-height: 30px;overflow: hidden;padding: 0px 10px;cursor: pointer;">${bank}</li>-->
                                <#--</#list>-->
                            <#--</#if>-->
                        <#--</ul>-->
                    <#--</div>-->
                </li>
                <li class="f-line clearfix">
                    <label class="f-label">开户行名称 &nbsp;&nbsp;&nbsp;&nbsp;|</label>
                    <input class="f-input c-1" type="text" id="search" name="openBankName" placeholder="请输入银行支行名称" value="<#if bankCard??>${bankCard.openBankName}</#if>" data-validtype="require">
                    <i class="bank_line" style="margin-left: 0.3rem;"></i>
                    <div class="nice-select" style="z-index: 9999;">
                        <ul id="selectOpenBank" style="display:none;background-color: rgb(0, 0, 0);overflow: auto;position: absolute;width: 100%;top: 1.02rem;left: 2rem;z-index: 9999;padding: 0.2rem;height: 4rem;">
                        </ul>
                    </div>
                </li>
                <li class="f-line clearfix">
                    <label class="f-label">卡&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号 &nbsp;&nbsp;&nbsp;&nbsp;|</label>
                    <input class="f-input c-1" type="text" name="bankAccount" placeholder="请输入借记卡卡号" value="<#if bankCard??>${bankCard.bankAccount}</#if>" data-validtype="require">
                </li>
                <li class="f-line clearfix">
                    <label class="f-label">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名 &nbsp;&nbsp;&nbsp;&nbsp;|</label>
                    <input class="f-input c-1" id="userName" type="text" placeholder="请输入持卡人姓名" name="chnName" value="<#if bankCard??>${bankCard.chnName}</#if>" data-validtype="require">
                </li>
                <li class="f-line clearfix">
                    <label class="f-label">身&nbsp;份&nbsp;证&nbsp;号&nbsp;码&nbsp;|</label>
                    <input class="f-input c-1" id="idCard" type="text" placeholder="请输入身份证号码" name="idCard" value="<#if bankCard??><#if bankCard.idCard??>${bankCard.idCard}</#if></#if>" data-validtype="require">
                </li>
                <li class="f-line clearfix">
                    <label class="f-label">预留手机号码&nbsp;&nbsp;|</label>
                    <input class="f-input c-1" id="phone" type="text" placeholder="请输入预留手机号码" name="phone" value="<#if bankCard??><#if bankCard.phone??>${bankCard.phone}</#if></#if>" data-validtype="require">
                </li>
            </ul>
            <#if !bankCard??>
                <div class="f-line line-pas">
                    <label class="f-label">输&nbsp;入&nbsp;密&nbsp;码 &nbsp;&nbsp;&nbsp;&nbsp;|</label>
                    <input name="password" class="f-pwd" type="password" placeholder="请输入密码" data-validtype="require">
                </div>
                <input id="subbut" type="button" value="提 交" class="f-sub" style="background-color: #cf372d;" onclick="addBankCard();">
                <#else >
                    <div class="f-line" id="divPwd" style="display: none;">
                        <label class="f-label">输&nbsp;入&nbsp;密&nbsp;码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|</label><input name="password" class="f-pwd" type="password" placeholder="请输入密码" data-validtype="require">
                    </div>
                    <input id="updateBankInfo" type="button" value="修改信息" class="f-sub" style="background-color: #cf372d;" onclick="addBut(this)">
                    <input id="subInfo" type="button" value="保存" class="f-sub" style="display: none; background-color: #cf372d;" onclick="addBankCard()">
            </#if>
        </form>
    </div>
</div>
<#include "/wap/footer.ftl">
<#if bank ??>
<script type="text/javascript" src="${ctxStatic}/js/modules/city.js"></script>
</#if>
<script type="text/javascript">
    <#if bankCard??>
        $('#withDraw input').attr('disabled', true);
        $('#updateBankInfo').attr('disabled', false);
    </#if>
    <#if bank ??>
        var banks = ${bank};
    </#if>

    function addBankCard() {
        var valid = $('#withDraw').valid();
        if(!valid){
            return;
        }
        var obj = $('#withDraw').obj();
        obj.password = md5(obj.password);
        $.ajax({
            url: ctx + '/users/addBankCard',
            type: 'POST',
            data: JSON.stringify(obj),
            contentType: 'application/json;charset=UTF-8',
            success: function (data) {
                layer.msg('绑定成功！', function () {
                    /*window.location.replace(ctxWap.concat('/user?r=').concat(Math.random()+""));*/
                    window.location.replace('${ctxStatic}/user/bankCard');
                });
            }
        });
    }

   function addBut(obj){
       $('#withDraw input').attr('disabled', false);
       $('#userName').attr('disabled',true);
       $('#userName').attr('disabled',true);
       $('#divPwd').css('display','block');
        $('#subInfo').css('display','block');
        $('#updateBankInfo').css('display','none');
    }

    /*监听支行*/
    var bind_name = 'input';
    var mainWord = null;
    $('#search').bind(bind_name+' paste',function(event){   //输入框的id为search，这里监听输入框的keyup事件
        event = event || window.event;
        event.preventDefault();

        $("#selectOpenBank").show();
        var val = $('#search').val();
        if(val == mainWord || "" == val){
            $('#selectOpenBank').hide();
            return;
        }
        mainWord = val;
        $.ajax({
            type:"POST",     //AJAX提交方式为GET提交
            url: ctx + "/users/getOpenBankName",   //处理页的URL地址
            data:"mainWord="+val,   //要传递的参数
            success:function(data){   //成功后执行的方法
                if(data != ""){
                    $("#selectOpenBank").empty();
                    for(i=0;i<data.length;i++){
                        $("#selectOpenBank").append('<li style="width: 100%;background: ;height: 30px;line-height: 30px; font-size: 0.32rem; color: #FFFFFF;" onclick="clickBank(this)">'+data[i]+'</li>')
                    }
                }
            }
        });

    });

    function clickBank(param) {
        $("#search").val($(param).html());
        $("#selectOpenBank").hide()
    }
</script>
</body>
</html>