<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <#include "/wap/header.ftl" >
    <link href="${ctxStatic}/all/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/weixinchongzhi.css?r=1.2" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/buzaijiaoyi.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"  src="${ctxStatic}/all/js/rem.js"></script>
    <title>${title}</title>
    <style>
        .success {
            width: 80%;
            overflow: hidden;
            height: 7rem;
        }
        .sure {
            width: 1.6rem;
            height: 1rem;
            line-height: 1rem;
            background: #cf372d;
            border-radius: 0.1rem;
            float: right;
            margin-right: 3.5rem;
            border: 0;
            color: #fff;
            font-size: 0.35rem;
            position: absolute;
            left: 50%;
            bottom: 0.5rem;
            top: auto;
            transform: translateX(-50%);
        }
        .xiadan_num_success {
            line-height: 20px;
            /* padding-top: 0.5rem; */
            padding-left: 0.2rem;
            color: #bababa;
            width: 100%;
            height: 4rem;
            background: transparent;
            border: none;
        }
    </style>
</head>
<body>
<section class="wrap-page">
    <div class="user_top">
        <p>充值规则：</p>
        <div class="chognzhi_content">
            <#--<p> &nbsp;&nbsp; &nbsp;&nbsp;单笔入金最低20，</p>-->
            <#--<p> &nbsp;&nbsp; &nbsp;&nbsp;单笔最高5000，单日最高5万；</p>-->
            <p> &nbsp;&nbsp; &nbsp;&nbsp;入金时间必须为交易时间，</p>
            <p> &nbsp;&nbsp; &nbsp;&nbsp;如有变动，以交易中心公告为准；</p>
        </div>
        <form id="form" method="post" action="https://pay.yizhifubj.com/customer/gb/pay_bank.jsp">
            <div class="w-item clearfix">
                <input type="text" id="zhenmoeny" placeholder="请输入金额" name="v_amount" class="pwd" required><span style="color: red">&yen;</span>
                <input type="hidden" id="v_mid" name="v_mid" value="" required>
                <input type="hidden" id="v_pmode" name="v_pmode" value="126" required>
                <input type="hidden" id="v_oid" name="v_oid" value="" required>
                <input type="hidden" id="v_rcvname" name="v_rcvname" value="" required>
                <input type="hidden" id="v_rcvaddr" name="v_rcvaddr" value="" required>
                <input type="hidden" id="v_rcvtel" name="v_rcvtel" value="" required>
                <input type="hidden" id="v_rcvpost" name="v_rcvpost" value="" required>
                <input type="hidden" id="v_ymd" name="v_ymd" value="" required>
                <input type="hidden" id="v_orderstatus" name="v_orderstatus" value="" required>
                <input type="hidden" id="v_ordername" name="v_ordername" value="" required>
                <input type="hidden" id="v_moneytype" name="v_moneytype" value="" required>
                <input type="hidden" id="v_url" name="v_url" value="" required>
                <input type="hidden" id="v_md5info" name="v_md5info" value="" required>
            </div>
        </form>

        <div id="zhongNanQuickPayDiv" style="/*padding-left: 0.4rem;*/ display: none;">
            <div class="f-line clearfix">
                <label class="f-label">银&nbsp;行&nbsp;卡&nbsp;号&nbsp;&nbsp;| </label>
                <input class="f-input c-1" type="text" name="card_no" placeholder="请输入银行卡号" maxlength="20">
            </div>
            <div class="f-line clearfix">
                <label class="f-label">持卡人姓名&nbsp;&nbsp;| </label>
                <input class="f-input c-1" type="text" name="card_name" placeholder="请输入持卡人姓名">
            </div>
            <div class="f-line clearfix">
                <label class="f-label">身&nbsp;份&nbsp;证&nbsp;号&nbsp;&nbsp;| </label>
                <input class="f-input c-1" type="text" name="id_no" placeholder="请输入身份证号" maxlength="18">
            </div>
            <div class="f-line clearfix">
                <label class="f-label">预留手机号&nbsp;&nbsp;| </label>
                <input class="f-input c-1" type="text" name="phone_no" placeholder="请输入银行预留手机号" maxlength="11">
            </div>
            <div class="f-line clearfix f_cation" style="background: transparent">
                <div class="verification" style="">
                    <label class="f-label">验&nbsp;&nbsp;&nbsp;&nbsp;证&nbsp;&nbsp;&nbsp;码&nbsp;&nbsp;| </label>
                    <input class="f-input c-1 getyzm" type="text" name="msg_no" placeholder="请输入验证码" maxlength="11">
                </div>
                <input type="button" id="btn" class="btnGet" value="获取验证码" />
            </div>
        </div>

        <!--中南快捷支付-->
        <form id="zhongNanQuickPay" action="http://api.zhongnanpay.com:3022/hmpay/online/createWxOrder.do" method="post" style="display: none;">
            <input name="merchant_no" id="merchant_no" value=""  type="hidden"/>
            <input name="total_fee" id="total_fee" value="" type="hidden"/>
            <input name="pay_num" id="pay_num" value=""  type="hidden"/>
            <input name="notifyurl" id="notifyurl" value="" type="hidden"/>
            <input name="pay_type" id="pay_type" value="" type="hidden"/>
            <input name="sign" id="sign" value="" type="hidden"/>
            <input name="card_no" id="card_no" value="" type="hidden"/>
            <input name="phone_no" id="phone_no" value="" type="hidden"/>
            <input name="card_name" id="card_name" value="" type="hidden"/>
            <input name="id_no" id="id_no" value="" type="hidden"/>
        </form>

        <!--智能云支付-->
        <form id="zhiNengCloudPay" action="https://pay.adhdu.cn/" method="post" style="display: none;">
            <input name="uid" id="uid" value=""  type="hidden"/>
            <input name="price" id="price" value="" type="hidden"/>
            <input name="istype" id="istype" value=""  type="hidden"/>
            <input name="notify_url" id="notify_url" value="" type="hidden"/>
            <input name="return_url" id="return_url" value="" type="hidden"/>
            <input name="orderid" id="orderid" value="" type="hidden"/>
            <input name="orderuid" id="orderuid" value="" type="hidden"/>
            <input name="goodsname" id="goodsname" value="" type="hidden"/>
            <input name="key" id="key" value="" type="hidden"/>
        </form>

        <form id="rongyaPay" action="http://api.ry-pay.net/api.aspx" method="post" style="display: none;">
            <input name="pay_version" type="hidden">
            <input name="pay_memberid" type="hidden">
            <input name="pay_orderid" type="hidden">
            <input name="pay_applydate" type="hidden">
            <input name="pay_bankcode" type="hidden">
            <input name="pay_notifyurl" type="hidden">
            <input name="pay_amount" type="hidden">
            <input name="pay_md5sign" type="hidden">
            <input name="pay_productname" type="hidden">
        </form>

        <form id="mingfuUnionPay" action="http://39.108.235.176/trans/gateway/webPage/collect?jsonParam=" method="post" style="display: none;">
            <#--<input name="merCode" type="hidden">-->
            <#--<input name="tranTime" type="hidden">-->
            <#--<input name="tranNo" type="hidden">-->
            <#--<input name="settleType" type="hidden">-->
            <#--<input name="tranAmt" type="hidden">-->
            <#--<input name="noticeUrl" type="hidden">-->
            <#--<input name="sign" type="hidden">-->
            <#--<input name="subject" type="hidden">-->
            <#--<input name="orderDesc" type="hidden">-->
            <#--<input name="frontUrl" type="hidden">-->
            <input name="jsonParam" type="hidden">
        </form>

        <#--<div id="zhongNanQuickPayDiv" style="/*padding-left: 0.4rem;*/ display: block;">-->
            <#--<div class="f-line clearfix">-->
                <#--<label class="f-label">银&nbsp;行&nbsp;卡&nbsp;号&nbsp;&nbsp;| </label>-->
                <#--<input class="f-input c-1" type="text" name="card_no" placeholder="请输入银行卡号" maxlength="20">-->
            <#--</div>-->
            <#--<div class="f-line clearfix">-->
                <#--<label class="f-label">预留手机号&nbsp;&nbsp;| </label>-->
                <#--<input class="f-input c-1" type="text" name="phone_no" placeholder="请输入银行预留手机号" maxlength="11">-->
            <#--</div>-->
            <#--<div class="f-line clearfix">-->
                <#--<label class="f-label">持卡人姓名&nbsp;&nbsp;| </label>-->
                <#--<input class="f-input c-1" type="text" name="card_name" placeholder="请输入持卡人姓名">-->
            <#--</div>-->
            <#--<div class="f-line clearfix">-->
                <#--<label class="f-label">身&nbsp;份&nbsp;证&nbsp;号&nbsp;&nbsp;| </label>-->
                <#--<input class="f-input c-1" type="text" name="id_no" placeholder="请输入身份证号" maxlength="18">-->
            <#--</div>-->
        <#--</div>-->

        <div class="pa_yc clearfix" id="ali_money" style="">
            <ul class="pa_y clearfix">
                <li id="" >300</li>
                <li id="" >1000</li>
                <li id="" >5000</li>
                <li id="" >10000</li>
                <li id="" >15000</li>
                <li id="" >20000</li>
            </ul>
        </div>

        <div class="pa_yc clearfix" id="wechat_money" style="display:none;">
            <ul class="pa_y clearfix">
                <li id="" >100</li>
                <li id="" >1000</li>
                <li id="" >5000</li>
                <li id="" >10000</li>
            </ul>
        </div>
        <div class="w-item tianxie clearfix">
            <div class="fill left"  onclick="changeStyle(3)">
                <a><span id="quickPay" class="tianxie_tu quickPay-active"></span>快捷支付(推荐)</a>
            </div>
            <div class="fill right" onclick="changeStyle(0)">
                <a href="#"><span id="yinLianPay" class="tianxie_tu yinlian"></span>银联支付</a>
            </div>
            <div class="fill left"  onclick="changeStyle(4)">
                <a><span id="quick" class="tianxie_tu quick"></span>快捷支付(推荐)</a>
            </div>
            <div class="fill right" onclick="changeStyle(2)">
                <a href="#"><span id="ali" class="tianxie_tu ali"></span>支付宝支付</a>
            </div>
            <div class="fill left"  onclick="changeStyle(1)">
                <a><span id="saoMaPay" class="tianxie_tu weixin"></span>微信支付</a>
            </div>
        </div>
        <button class="login" type="submit" onclick="toShouXinYiRecharge(payType)">确认充值</button>
    </div>
    <div class="popBox" id="successBox" style="display:none">
        <div class="popCont">
            <div class="pop_bd f36 success" style="overflow: hidden">
                <div class="tishi">
                    <div class="tishi_text left"><i class="img"></i>提示</div>
                    <div class="close"></div>
                </div>
                <div class="duihao">

                    <textarea readonly id="copy-num" class="xiadan_num_success left"></textarea>
                </div>
                <button class="sure make_sure">确认</button>
            </div>
        </div>
    </div>
</section>


<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript"  src="${ctxStatic}/js/md5.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/asserts/layer_mobile/layer.src.js"></script>
<script type="application/javascript" charset="UTF-8" src="${ctxStatic}/asserts/iscroll-4/iscroll.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/tools.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/valid.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/modules/pay/recharge.js?r=1.2"></script>

<script type="text/javascript">


   $(document).ready(function(obj){
       $('.make_sure').click(function(){
           var e=document.getElementById("copy-num").select();//对象是copy-num1
           document.execCommand("Copy"); //执行浏览器复制命令
           alert("复制成功,请在pc端打开连接");
           $('#successBox').hide();
           //window.clipboardData.setData("Text",'123456');
       })
        $("li").on("click",function(){
            $("#zhenmoeny").val($(this).text());
            $(".on").removeClass("on");
            $(this).addClass("on");
        });
    });
   var payType = 3;
   function changeStyle(n) {
       /*if(n != 0){
           $('#zhongNanQuickPayDiv').hide();
       }else{
           $('#form').show();
           $('#zhongNanQuickPayDiv').show();
       }*/

       /*if(n == 1){
           $('#form').hide();
           $('#ali_money').hide();
           $('#wechat_money').show();
       }else if(n == 2){
           $('#form').hide();
           $('#ali_money').show();
           $('#wechat_money').hide();
       }else{
           $('#ali_money').show();
           $('#wechat_money').hide();
       }*/

       $('#saoMaPay').removeClass('weixin-active').addClass('weixin');
       $('#yinLianPay').removeClass('yinlian-active').addClass('yinlian');
       $('#ali').removeClass('ali-active').addClass('ali');
       $('#quickPay').removeClass('quickPay-active').addClass('quickPay');
       $('#quick').removeClass('quick-active').addClass('quick');
       payType = n;
       if(n==0){
           //银联
           $('#yinLianPay').removeClass('yinlian').addClass('yinlian-active');
       }else if(n == 1){
           //微信
           $('#saoMaPay').removeClass('weixin').addClass('weixin-active');
       }else if(n == 3){
           $('#quickPay').removeClass('quickPay').addClass('quickPay-active');
       }else if(n == 4){
           $('#quick').removeClass('quick').addClass('quick-active');
       }else{
           //支付宝
           $('#ali').removeClass('ali').addClass('ali-active');
       }
       /*if(n != 3){
           $('#zhongNanQuickPayDiv').hide();
       }else{
           $('#zhongNanQuickPayDiv').show();
       }*/
   }
</script>
</body>
</html>