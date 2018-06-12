
//
function toRecharge() {
    var _m = $("#zhenmoeny").val();
    if(!/^\d+(\.\d{0,2})?$/.test(_m)){
        layer.msg("充值金额不正确！", function () {

        });
        return;
    }
    layer.open({
        type: 2,
        content: '加载中',
        shadeClose: false
    });
    $.ajax({
        url: ctx + '/money/prepay',
        type: 'post',
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify({
            money: _m,
            tradeType: 'JSAPI'
        }),
        success: function (data) {
            // function onBridgeReady(){
            //     layer.closeAll();
            //     var _prepay = data.pk.replace('prepay_id=', '');
            //     WeixinJSBridge.invoke(
            //         'getBrandWCPayRequest',
            //         {
            //             "appId": data.appId,     //公众号名称，由商户传入
            //             "timeStamp": data.timeStamp,         //时间戳，自1970年以来的秒数
            //             "nonceStr": data.nonceStr, //随机串
            //             "package": data.pk,
            //             "signType": data.signType,         //微信签名方式：MD5
            //             "paySign": data.paySign //微信签名
            //         },
            //         function(res){
            //             // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
            //             if(res.err_msg == "get_brand_wcpay_request:ok" ) {
            //                 layer.msg("充值成功", function () {
            //                     window.history.back();
            //                 })
            //             }else {
            //                 $.ajax({
            //                     url: ctx + '/money/prepay/del/'+_prepay,
            //                     type: 'delete',
            //                     contentType: 'application/json;charset=UTF-8'
            //                 });
            //                 if(res.err_msg == 'get_brand_wcpay_request:cancel'){
            //                     // 用户取消
            //                 }else{
            //                     layer.msg("充值失败", function () {
            //
            //                     });
            //                 }
            //             }
            //         }
            //     );
            // }
            // if (typeof WeixinJSBridge == "undefined"){
            //     if(document.addEventListener ){
            //         document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            //     }else if(document.attachEvent){
            //         document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            //         document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            //     }
            // }else{
            //     onBridgeReady();
            // }
            window.location.replace('https://pay.swiftpass.cn/pay/jspay?token_id='+data+'&showwxtitle=1');
        },
        error: function(){
            layer.closeAll();
            layer.msg("充值失败");
        }
    });
}

//首信易支付
function toShouXinYiRecharge(payType) {
    var _m = $("#zhenmoeny").val();
    if(Number(_m) < 20){
        layer.msg("单笔入金最低20元");
        return;
    }
    if(payType == 0){
        /*zhongNanQuickPay(_m);*/
        /*rongyaUnionPay(_m);*/
        mingfuUnionPay(_m);
    }else if(payType == 1){
        //scanCodePay();
        cloudWechat(_m);
        /*rongyaUnionPay(_m);*/
    }else if(payType == 2){
        cloudAli(_m);
        /*rongyaUnionPay(_m);*/
    }else if(payType == 3){
        /*msgSub();*/
        mingfuQuickPay_new(_m);
    }else if(payType == 4){
        rongheFastPay(_m);
    }else{

    }

}


function mingfuQuickPay_new(_m){
    var obj = {
        card_no: $("#zhongNanQuickPayDiv input[name='card_no']").val().trim(),
        phone_no: $("#zhongNanQuickPayDiv input[name='phone_no']").val().trim(),
        card_name: $("#zhongNanQuickPayDiv input[name='card_name']").val().trim(),
        id_no: $("#zhongNanQuickPayDiv input[name='id_no']").val().trim()
    };
    /*if(obj.card_no == null || obj.card_no == '' || !/^\d{16,19}$/.test(obj.card_no)){
        layer.msg('请填写正确的银行卡号！');
        return;
    }
    if(obj.phone_no == null || obj.phone_no == ''){
        layer.msg('请填写正确的手机号！');
        return;
    }
    if(obj.card_name == null || obj.card_name == ''){
        layer.msg('请填写正确的银行卡户名！');
        return;
    }
    if(obj.id_no == null || obj.id_no == '' || !/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(obj.id_no)){
        layer.msg('请填写正确的身份证号！');
        return;
    }*/

    layer.open({
        type: 2,
        content: '加载中',
        shadeClose: false
    });
    obj.money = _m;
    $.ajax({
        url: ctx + '/money/mingfuQuickPay',
        type: 'POST',
        contentType: 'application/json;UTF-8',
        data: JSON.stringify(obj),
        success: function (data) {
            layer.closeAll();
            window.location.href = data;
        },
        error: function (data) {
            layer.closeAll();
            var error = JSON.parse(data.responseText);
            layer.msg(error.error);
        }
    })
}


function mingfuQuickPay(_m){
    var obj = {
        card_no: $("#zhongNanQuickPayDiv input[name='card_no']").val().trim(),
        phone_no: $("#zhongNanQuickPayDiv input[name='phone_no']").val().trim(),
        card_name: $("#zhongNanQuickPayDiv input[name='card_name']").val().trim(),
        id_no: $("#zhongNanQuickPayDiv input[name='id_no']").val().trim()
    };
    if(obj.card_no == null || obj.card_no == '' || !/^\d{16,19}$/.test(obj.card_no)){
        layer.msg('请填写正确的银行卡号！');
        return;
    }
    if(obj.phone_no == null || obj.phone_no == ''){
        layer.msg('请填写正确的手机号！');
        return;
    }
    if(obj.card_name == null || obj.card_name == ''){
        layer.msg('请填写正确的银行卡户名！');
        return;
    }
    if(obj.id_no == null || obj.id_no == '' || !/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(obj.id_no)){
        layer.msg('请填写正确的身份证号！');
        return;
    }

    layer.open({
        type: 2,
        content: '加载中',
        shadeClose: false
    });
    obj.money = _m;
    $.ajax({
        url: ctx + '/money/mingfuQuickPay',
        type: 'POST',
        contentType: 'application/json;UTF-8',
        data: JSON.stringify(obj),
        success: function (data) {
            layer.closeAll();
            layer.msg(data);
        },
        error: function (data) {
            layer.closeAll();
            var error = JSON.parse(data.responseText);
            layer.msg(error.error);
        }
    })
}

/**
 * 明付网关
 * @param _m
 * @returns {boolean}
 */
function mingfuUnionPay(_m) {
    if(Number(_m) < 300){
        layer.msg("银联入金最低300元！");
        return false;
    }
    var obj = {
        money: _m
    }
    // $.cookie('zhongnan_bank_info', encodeURIComponent(JSON.stringify(obj)), { expires: 7, path: window.location.pathname });
    layer.open({
        type: 2,
        content: '加载中',
        shadeClose: false
    });

    $.ajax({
        url: ctx + '/money/mingfuUnionPay',
        type: 'post',
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify(obj),
        success: function (data) {
            layer.closeAll();
           /* console.log(JSON.stringify(data));
            for(var key in data){
                console.log(data[key]);
                $("input[name="+ key +"]").val(data[key]);

            }
            $("#mingfuUnionPay").submit();*/
            /*window.location.href = ';*/
            /*$("input[name=jsonParam]").val(data);
            $("#mingfuUnionPay").submit();*/

            $('.xiadan_num_success').html('http://39.108.235.176/trans/gateway/webPage/collect?jsonParam='.concat(data))
            $('#successBox').show();

        },
        error: function(){
            layer.closeAll();
            layer.msg("充值失败");
        }
    });
}

/*融合*/
function rongheFastPay(_m) {
    if(Number(_m) < 300){
        layer.msg("银联入金最低300元！");
        return false;
    }
    var obj = {
        money: _m
    }
    // $.cookie('zhongnan_bank_info', encodeURIComponent(JSON.stringify(obj)), { expires: 7, path: window.location.pathname });
    layer.open({
        type: 2,
        content: '加载中',
        shadeClose: false
    });

    $.ajax({
        url: ctx + '/money/rongheFastPay',
        type: 'post',
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify(obj),
        success: function (data) {
            layer.closeAll();
            window.location.href = data;
        },
        error: function(){
            layer.closeAll();
            layer.msg("充值失败");
        }
    });
}

function rongyaUnionPay(_m) {
    var obj = {
        money: _m
    }
    // $.cookie('zhongnan_bank_info', encodeURIComponent(JSON.stringify(obj)), { expires: 7, path: window.location.pathname });
    layer.open({
        type: 2,
        content: '加载中',
        shadeClose: false
    });

    var url = "";
    if(payType == 0){
        url = ctx + '/money/rongyaUnionPay';
    }else if(payType == 1){
        url = ctx + '/money/rongyaWechatPay';
    }else if(payType == 2){
        url = ctx + '/money/rongyaAliPay';
    }else{}

    $.ajax({
        url: url,
        type: 'post',
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify(obj),
        success: function (data) {
            layer.closeAll();
            for(var key in data){
                $("input[name="+ key +"]").val(data[key]);
            }
            $('#rongyaPay').submit();
        },
        error: function(){
            layer.closeAll();
            layer.msg("充值失败");
        }
    });
}


function cloudWechat(_m){
    layer.open({
        type: 2,
        content: '加载中',
        shadeClose: false
    });

    $.ajax({
        url: ctx + '/money/zhinengCloudWechat',
        type: 'POST',
        contentType: 'application/json;UTF-8',
        data: JSON.stringify({
            money: _m
        }),
        success: function (data) {
            console.log(data);
            for(var key in data){
                $('#'.concat(key)).val(data[key]);
            }
            $('#zhiNengCloudPay').submit();
        },
        error: function (data) {
            layer.closeAll();
            var error = JSON.parse(data.responseText);
            layer.msg(error.error);
        }
    })
}

function cloudAli(_m){
    layer.open({
        type: 2,
        content: '加载中',
        shadeClose: false
    });

    $.ajax({
        url: ctx + '/money/zhinengCloudAli',
        type: 'POST',
        contentType: 'application/json;UTF-8',
        data: JSON.stringify({
            money: _m
        }),
        success: function (data) {
            layer.closeAll();
            console.log(data);
            for(var key in data){
                $('#'.concat(key)).val(data[key]);
            }
            $('#zhiNengCloudPay').submit();
        },
        error: function (data) {
            layer.closeAll();
            var error = JSON.parse(data.responseText);
            layer.msg(error.error);
        }
    })
}

function scanCodePay() {
    var _m = $("#zhenmoeny").val();
    if(!/^\d+(\.\d{0,2})?$/.test(_m)){
        layer.msg("充值金额不正确！", function () {

        });
        return;
    }
    layer.open({
        type: 2,
        content: '加载中',
        shadeClose: false
    });
    $.ajax({
        url: ctx + '/money/createPayOrder',
        type: 'post',
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify({
            money: _m,
            type:"254"
        }),
        success: function (data) {
            // var url = data.bankurl;
            // window.location.replace(url);
            window.location.href = ctxWap + '/money/qrcode?url='+encodeURIComponent(data.codeurl);
        },
        error: function(){
            layer.closeAll();
            layer.msg("充值失败");
        }
    });
}

function unionPay() {
    var _m = $("#zhenmoeny").val();
    if(/^\d+(\.\d{0,2})?$/.test(_m)){
    }else{
        layer.msg("请输入正确的充值金额！")
        return;
    }
    layer.open({
        type: 2,
        content: '加载中',
        shadeClose: false
    });

    var obj = {
        money: getInputValue('zhenmoeny'),
    };
    $.ajax({
        //加密数字签名 且添加订单
        url: ctx + '/money/yizhifu/unionPay/createPayOrder',
        type: 'post',
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify(obj),
        success: function (data) {
            if(data != ''){

                setInputValue('v_mid',data.v_mid);
                setInputValue('v_oid',data.v_oid);
                setInputValue('v_rcvname',data.v_rcvname);
                setInputValue('v_rcvaddr',data.v_rcvaddr);
                setInputValue('v_rcvtel',data.v_rcvtel);
                setInputValue('v_rcvpost',data.v_rcvpost);
                setInputValue('v_ymd',data.v_ymd);
                setInputValue('v_orderstatus',data.v_orderstatus);
                setInputValue('v_ordername',data.v_ordername);
                setInputValue('v_moneytype',data.v_moneytype);
                setInputValue('v_url',data.v_url);
                setInputValue('v_md5info',data.v_md5info);
                document.getElementById('form').submit();
            }
        },
        error: function(){
            layer.closeAll();
            layer.msg("充值失败");
        }
    });
}

function zhongNanQuickPay(_m) {
    var obj = {
        card_no: $("#zhongNanQuickPayDiv input[name='card_no']").val().trim(),
        phone_no: $("#zhongNanQuickPayDiv input[name='phone_no']").val().trim(),
        card_name: $("#zhongNanQuickPayDiv input[name='card_name']").val().trim(),
        id_no: $("#zhongNanQuickPayDiv input[name='id_no']").val().trim()
    };
    if(obj.card_no == null || obj.card_no == '' || !/^\d{16,19}$/.test(obj.card_no)){
        layer.msg('请填写正确的银行卡号！');
        return;
    }
    if(obj.phone_no == null || obj.phone_no == ''){
        layer.msg('请填写正确的手机号！');
        return;
    }
    if(obj.card_name == null || obj.card_name == ''){
        layer.msg('请填写正确的银行卡户名！');
        return;
    }
    if(obj.id_no == null || obj.id_no == '' || !/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(obj.id_no)){
        layer.msg('请填写正确的身份证号！');
        return;
    }
    // $.cookie('zhongnan_bank_info', encodeURIComponent(JSON.stringify(obj)), { expires: 7, path: window.location.pathname });
    layer.open({
        type: 2,
        content: '加载中',
        shadeClose: false
    });
    obj.money = _m;
    $.ajax({
        url: ctx + '/money/zhongNanQuickPay',
        type: 'post',
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify(obj),
        success: function (data) {
            layer.closeAll();
            for(var attr in data){
                $('#zhongNanQuickPay input[name="'+attr+'"]').val(data[attr]);
            }
            console.log(JSON.stringify(data));
            document.getElementById('zhongNanQuickPay').submit();
        },
        error: function(){
            layer.closeAll();
            layer.msg("充值失败");
        }
    });
}

function getInputValue(id) {
    return $('#'+id).val();
}
function setInputValue(id,value) {
    return $("#"+id).val(value);
}

var wait=60;
function time(o) {
    var phone = $("#zhongNanQuickPayDiv input[name='phone_no']").val().trim();
    if(phone == null || phone == ''){
        layer.msg('请填写正确的手机号！');
        return;
    }
    if (wait == 0) {
        o.removeAttribute("disabled");
        o.value="重新获取";
        wait = 60;
        payFalg = true;
    } else {

        o.setAttribute("disabled", true);
        o.value="重新发送(" + wait + ")";
        wait--;
        setTimeout(function() {
                time(o)
            },
            1000)
    }
}

var payFalg = false;
document.getElementById("btn").onclick = function(){
    if(payFalg){
        layer.msg("请重新创建支付订单！");
    }else{
        time(this);
        var _m = $("#zhenmoeny").val();
        mingfuQuickPay(Number(_m));
    }
}

function msgSub(){
    var obj = {
        msg_no : $("#zhongNanQuickPayDiv input[name='msg_no']").val().trim()
    }
    if(obj.msg_no == null || obj.msg_no == ''){
        layer.msg("请填写正确的短信验证码！");
        return;
    }
    $.ajax({
        url: ctx + '/money/mingfuMsgSub',
        type: 'POST',
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify(obj),
        success: function (data) {
            layer.msg(data);
        },
        error: function (data) {
            layer.closeAll();
            var error = JSON.parse(data.responseText);
            layer.msg(error.error);
        }
    })
}