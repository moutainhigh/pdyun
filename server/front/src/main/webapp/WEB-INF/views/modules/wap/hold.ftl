<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
<#include "/wap/header.ftl" >
<#assign footer = "hold"/>
    <link href="${ctxStatic}/all/css/base.css" rel="stylesheet" type="text/css"/>

    <link href="${ctxStatic}/all/css/daydingd.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/chicang.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>
    <!--<link href="all/css/footer.css" rel="stylesheet" type="text/css"/>-->
    <script type="text/javascript" src="${ctxStatic}/js/rem.js"></script>
    <title>${title}</title>
    <style type="text/css">
        .layui-m-layer0 .layui-m-layerchild{
            background: #1d2128;
        }
        #_tradePwd{
            background: #0f1317;
            color: #fff;
            height: 1.3rem;
            width: 7rem;
            border-radius: .3rem;
            padding-left: .3rem;
        }
        ::-webkit-input-placeholder{
            color:#fff;
        }
        .layui-m-layerbtn span[yes] {
            color: #fff!important;
        }
        .layui-m-layerbtn {
            display: box;
            display: -moz-box;
            display: -webkit-box;
            width: 100%;
            height: 50px;
            line-height: 50px;
            font-size: .35rem;
             border-top: none!important;
            background-color: #ce372c!important;
            color: #fff;
        }
        .layui-m-layerbtn span{
            font-size: .5rem!important;
        }
        .layui-m-layer0 .layui-m-layerchild{
            width: 80%!important;
        }
    </style>
</head>
<body id="fixation">
<section class="wrap-page">
    <div class="user_top">
        <a class="left buy_chic <#if !flag??>buy_dd</#if>" id="chicang" onclick="change1()">
            <i class="icon i_z buy_chic_fangd"></i>持仓中
        </a>
        <a class="right buy_chic <#if flag??>buy_dd</#if>" id="todayOrder" onclick="chang2()">
            <i class="icon i_z  buy_chic_dingd"></i>当日订单
        </a>
    </div>
    <!--持仓页面-->
<#if list??>
    <#list list as it>
        <#if it.model?string == '2'>
            <div class="new_tong div1" id="holdDiv_${it.id}" <#if flag??>style="display: none;"</#if> >
                <div class="new_tong_lf left">
                    <p>订单号:<span>${it.serialNo}</span></p>
                    <p class="tong_text">${it.contractName}</p>
                    <p>最新价格</p>
                    <p <#--id="${it.id}_1"--> class="price_${it.code}">
                    </p>
                </div>
                <div class="new_tong_rt right" style="margin-top: .4rem">
                    <div class="buyru_top">
                        <div class="buyru_top_one buyru_t left"><img src="<#if it.buyUpDown?number == 0>${ctxStatic}/all/image/zhang.png<#else>${ctxStatic}/all/image/xiajiang.png</#if>"></div>
                        <div class="bianhao_bot_tong_line left"></div>
                        <div class="buyru_top_two buyru_t left">
                            <p>买入价</p>
                            <p>${it.buyPoint?c}</p>
                        </div>
                        <div class="bianhao_bot_tong_line left"></div>
                        <div class="buyru_top_three buyru_t  left">
                            <p>投资金额</p>
                            <p>&yen;${it.money}</p>
                        </div>
                        <div class="bianhao_bot_tong_line left"></div>
                        <div class="buyru_top_three buyru_t  left">
                            <p>点位</p>
                            <#list map?keys as key>
                                <#if it.code == key && map[key] == 0>
                                    <p><#if it.offPoint??>${it.offPoint?number * 1}</#if></p>
                                <#elseif it.code == key && map[key] == 1>
                                    <p><#if it.offPoint??>${it.offPoint?number * 10}</#if></p>
                                <#elseif it.code == key && map[key] == 2>
                                    <p><#if it.offPoint??>${it.offPoint?number * 100}</#if></p>
                                <#elseif it.code == key && map[key] == 3>
                                    <p><#if it.offPoint??>${it.offPoint?number * 1000}</#if></p>
                                </#if>
                            </#list>
                        </div>
                    <#--<div class="bianhao_bot_tong_line left"></div>-->
                        <div class="buyru_top_for buyru_t right">
                        <#--                    <p id="${it.id}_2">12121</p>
                        <p id="${it.id}_3"></p>-->
                        </div>
                    </div>
                    <div class="buyru_bot">
                        <p class="left">收益率:<span id="${it.id}"><#--${it.winMoney?number / it.money?number * 100}%--></span></p>
                        <p class="right">${it.buyTime?time}</p>

                    </div>
                <#--            <div class="buyru_bot">
                                <div class="buyru_bot_lf left">
                                    <p>开始时间:<span>${it.buyTime?datetime}</span></p>
                                    <input class="lefttime" orderid="${it.id}" lefttime = "" value="${it.sellTime?datetime}" hidden/>
                                    <p>剩余时间:<span class="green"><span id="fen_${it.id}"></span>分<span id="miao_${it.id}"></span>秒</span></p>
                                </div>
                                <div class="buyru_bot_rt right">
                                    <div class="buyru_bot_rt_w">
                                        <div class="buyru_bot_rt_red" id="color_${it.id}">
                                            <div class="num" id="speed_${it.id}"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>-->
                </div>
            </div>
        </#if>
    </#list>
</#if>
    <!-- 微盘持仓样式 -->
    <if list??>
        <#list list as it>
            <#if it.model == '0'>
                <div class="new_tong div1" id="holdDiv_${it.id}" <#if flag??>style="display: none;"</#if> >
                   <div class="Mochikura">
                       <div class="indent" style="display: block">
                           <p class="left goods">订单号:<span>${it.serialNo}</span></p>
                           <p class="right predict">预计收入 : <span id="pre_${it.id}">加载中..</span></p>
                       </div>
                      <div style="overflow: hidden;display: block">
                          <div class="new_tong_lf left">

                              <p class="tong_text">${it.contractName}</p>
                              <p>最新价格</p>
                              <p class="price_${it.code}"></p>
                          </div>
                          <div class="new_tong_rt right">
                              <div class="buyru_top">
                                  <div class="buyru_top_one buyru_t left"><img src="<#if it.buyUpDown?number == 0>${ctxStatic}/all/image/zhang.png<#else>${ctxStatic}/all/image/xiajiang.png</#if>"></div>
                                  <div class="bianhao_bot_tong_line left"></div>
                                  <div class="buyru_top_two buyru_t left">
                                      <p>买入价</p>
                                      <p>${it.buyPoint?c}</p>
                                  </div>
                                  <div class="bianhao_bot_tong_line left"></div>
                                  <div class="buyru_top_three buyru_t  left">
                                      <p>投资金额</p>
                                      <p>&yen;${it.money}</p>
                                  </div>
                                  <div class="bianhao_bot_tong_line left"></div>
                                  <div class="buyru_top_three buyru_t  left">
                                      <button class="position right" onclick="sell('${it.id}')">平仓</button>
                                      <#--<span>盈亏</span>-->
                                  </div>
                              <#--<div class="bianhao_bot_tong_line left"></div>-->
                                  <div class="buyru_top_for buyru_t right">
                                  <#--                    <p id="${it.id}_2">12121</p>
                        <p id="${it.id}_3"></p>-->
                                  </div>
                              </div>
                             <#-- <div class="buyru_bot">

                              </div>-->
                              <div class="buyru_bot">
                                  <p class="left">手续费:<span>${it.fee}元</span></p>
                                  <p class="left ptime">${it.buyTime?time}</p>
                                  <button class="amend right" style="cursor: pointer;" onclick="updateHold('${it.id}')">修改</button>
                              </div>

                          </div>
                      </div>
                   </div>
                </div>
            </#if>
        </#list>
    </if>

    <!--当日订单页面-->
    <div class="account div2" <#if !flag??>style="display: none;"</#if> >
        <div class="left account_dnum">
            当日单数:<span>${toDayTradeList?size}</span>
        </div>
        <div class="right account_ye">
            账户余额:<span>${user.money}</span>
        </div>
    </div>
<#if toDayTradeList??><#list toDayTradeList as it>
    <div class="bianhao div2" <#if !flag??>style="display: none;"</#if> >
        <div class="bianhao_top">
            <div class="left bianhao_num">交易编号：<span>${it.serialNo}</span></div>
            <div class="right bianhao_time">下单时间:<span>${it.buyTime?time}</span></div>
        </div>
        <div class="bianhao_line"></div>
        <div class="bianhao_bot">
            <div class="left bianhao_bot_tong left">
                <div class="bianhao_bot_tong_lf">
                    <img src="<#if it.contractName == 'DK银'>${ctxStatic}/all/image/yin_03.png<#elseif it.contractName == 'DK铜'>${ctxStatic}/all/image/tong.png<#else>${ctxStatic}/all/image/shiyou.png</#if>" class="clearfix text-center img">
                    <p class="text-center">${it.contractName}</p>
                </div>
            </div>
            <div class="bianhao_bot_tong_line left"></div>
            <div class="left bianhao_bot_price left">
                <div class="left biao_price_lf">
                    <div class="left bianhao_bot_price_t left">
                        <p>执行价格</p>
                        <p>${it.buyPoint}</p>
                    </div>
                    <div class="left bianhao_bot_price_c left"></div>
                    <div class="left bianhao_bot_price_b left">
                        <p>到期价格</p>
                        <p>${it.sellPoint}</p>
                    </div>
                </div>
                <img src="<#if it.buyUpDown?number == 0>${ctxStatic}/all/image/zhang.png<#else>${ctxStatic}/all/image/xiajiang.png</#if>" class="left text-center">
                <div class="left biao_price_lf left">
                    <div class="left bianhao_bot_price_t">
                        <p>投资金额</p>
                        <p>${it.money}</p>
                    </div>
                    <div class="left bianhao_bot_price_c"></div>
                    <div class="left bianhao_bot_price_b ">
                        <p>到期时间</p>
                        <p>${it.sellTime?time}</p>
                    </div>
                </div>
            </div>
            <div class="bianhao_bot_tong_line left"></div>
            <#if it.difMoney gt 0>
                <div class="right bianhao_bot_d  bianhao_bot_yk">
                    <div style="white-space: nowrap;">盈<h3>¥${it.difMoney}</h3></div>
                    <div class="bianhao_bot_price_c"></div>
                    <#if it.model?number == 2>
                        <#list map?keys as key>
                            <#if it.code == key && map[key] == 0>
                                <p class="dian" style="font-size:0.3rem;color:#fff;white-space: nowrap;display: block;/* margin: 0 auto; */float: left;margin-left: 0.3rem;"> 点位<span><#if it.offPoint??>${it.offPoint?number * 1}</#if></span></p>
                            <#elseif it.code == key && map[key] == 1>
                                <p class="dian" style="font-size:0.3rem;color:#fff;white-space: nowrap;display: block;/* margin: 0 auto; */float: left;margin-left: 0.3rem;"> 点位<span><#if it.offPoint??>${it.offPoint?number * 10}</#if></span></p>
                            <#elseif it.code == key && map[key] == 2>
                                <p class="dian" style="font-size:0.3rem;color:#fff;white-space: nowrap;display: block;/* margin: 0 auto; */float: left;margin-left: 0.3rem;"> 点位<span><#if it.offPoint??>${it.offPoint?number * 100}</#if></span></p>
                            <#elseif it.code == key && map[key] == 3>
                                <p class="dian" style="font-size:0.3rem;color:#fff;white-space: nowrap;display: block;/* margin: 0 auto; */float: left;margin-left: 0.3rem;"> 点位<span><#if it.offPoint??>${it.offPoint?number * 1000}</#if></span></p>
                            </#if>
                        </#list>
                    <#else>
                            <p class="dian" style="font-size:0.3rem;color:#fff;white-space: nowrap;display: block;/* margin: 0 auto; */float: left;margin-left: 0.3rem;"> 市场模式</p>
                    </#if>
                    <div class="serviceChange">手续费:<span>${it.fee}</span></div>
                </div>
            <#elseif it.difMoney lt 0>
                <div class="right bianhao_botg_yk">
                    <#if it.model == '2' >
                        <div style="color:#fff;white-space: nowrap;">亏<h3>¥-${it.money - it.fee}</h3></div>
                    <#else>
                        <div style="color:#fff;white-space: nowrap;">亏<h3>¥${it.difMoney}</h3></div>
                    </#if>
                    <div class="bianhao_bot_price_c"></div>
                    <#if it.model?number == 2>
                        <#list map?keys as key>
                            <#if it.code == key && map[key] == 0>
                                <p class="dian" style="font-size:0.3rem;color:#fff;white-space: nowrap;display: block;/* margin: 0 auto; */float: left;margin-left: 0.3rem;"> 点位<span><#if it.offPoint??>${it.offPoint?number * 1}</#if></span></p>
                            <#elseif it.code == key && map[key] == 1>
                                <p class="dian" style="font-size:0.3rem;color:#fff;white-space: nowrap;display: block;/* margin: 0 auto; */float: left;margin-left: 0.3rem;"> 点位<span><#if it.offPoint??>${it.offPoint?number * 10}</#if></span></p>
                            <#elseif it.code == key && map[key] == 2>
                                <p class="dian" style="font-size:0.3rem;color:#fff;white-space: nowrap;display: block;/* margin: 0 auto; */float: left;margin-left: 0.3rem;;color:#fff"> 点位<span><#if it.offPoint??>${it.offPoint?number * 100}</#if></span></p>
                            <#elseif it.code == key && map[key] == 3>
                                <p class="dian" style="font-size:0.3rem;color:#fff"> 点位<span><#if it.offPoint??>${it.offPoint?number * 1000}</#if></span></p>
                            </#if>
                        </#list>
                    <#else >
                        <p class="dian" style="font-size:0.3rem;color:#fff"> 市场模式</p>
                    </#if>
                    <div class="serviceChange">手续费:<span>${it.fee}</span></div>
                </div>
            <#else>
                <div class="right bianhao_both_yk">
                    <div style="color:#fff;white-space: nowrap;">平<h3>¥${it.difMoney}</h3></div>
                    <div class="bianhao_bot_price_c"></div>
                    <#if it.model?number == 2>
                        <#list map?keys as key>
                            <#if it.code == key && map[key] == 0>
                                <p class="dian" style="font-size:0.3rem;color:#fff;white-space: nowrap;display: block;/* margin: 0 auto; */float: left;margin-left: 0.3rem;"> 点位<span><#if it.offPoint??>${it.offPoint?number * 1}</#if></span></p>
                            <#elseif it.code == key && map[key] == 1>
                                <p class="dian" style="font-size:0.3rem;color:#fff;white-space: nowrap;display: block;/* margin: 0 auto; */float: left;margin-left: 0.3rem;"> 点位<span><#if it.offPoint??>${it.offPoint?number * 10}</#if></span></p>
                            <#elseif it.code == key && map[key] == 2>
                                <p class="dian" style="font-size:0.3rem;color:#fff;white-space: nowrap;display: block;/* margin: 0 auto; */float: left;margin-left: 0.3rem;"> 点位<span><#if it.offPoint??>${it.offPoint?number * 100}</#if></span></p>
                            <#elseif it.code == key && map[key] == 3>
                                <p class="dian" style="font-size:0.3rem;color:#fff;white-space: nowrap;display: block;/* margin: 0 auto; */float: left;margin-left: 0.3rem;"> 点位<span><#if it.offPoint??>${it.offPoint?number * 1000}</#if></span></p>
                            </#if>
                        </#list>
                    <#else >
                        <p class="dian" style="font-size:0.3rem;color:#fff;white-space: nowrap;display: block;/* margin: 0 auto; */float: left;margin-left: 0.3rem;"> 市场模式</p>
                    </#if>
                    <div class="serviceChange">手续费:<span>${it.fee}</span></div>
                </div>
            </#if>
        </div>
    </div>
</#list>
</#if>
</section>
<#include "/wap/footer.ftl"/>
<script type="application/javascript" src="${ctxStatic}/js/modules/trade/market.js?r=2"></script>
<script>
    function change1() {
        $('#chicang').addClass('buy_dd');
        $('#todayOrder').removeClass('buy_dd');
        $(".div2").hide();
        $(".div1").show();
    }
    function chang2() {
        $('#todayOrder').addClass('buy_dd');
        $('#chicang').removeClass('buy_dd');
        $(".div1").hide();
        $(".div2").show();
        window.location.replace(ctxWap + '/trade/hold?flag=2&r='+Math.random());
    }


    var _codes = ${_codes};
    var _list = ${_list};
    // 刷新盈亏情况
    var oldPrice = {};
    function refreshWinOrLoss() {
        $.ajax({
            url: ctx + '/market/new',
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify({
                "codes": _codes.join(',')
            }),
            success: function (data) {
                if (!data || data.length == 0) {
                    return;
                }
                // 整理最新价格
                var newPrice = {};
                for(var i = 0; i < data.length; i++){
                    if(data[i] == null){
                        continue;
                    }

                    /*更换价格颜色*/
                    var price = data[i];
                    for (var key in oldPrice){
                        if(key == price.code){
                            if(price.price > oldPrice[key]){
                                $('.price_'.concat(price.code)).removeClass("tong_green").addClass("tong_red");
                            }else if(price.price < oldPrice[key]){
                                $('.price_'.concat(price.code)).removeClass("tong_red").addClass("tong_green");
                            }else{

                            }
                        }
                    }

                    newPrice[data[i].code] = Number(data[i].price);
                    oldPrice[data[i].code] = Number(data[i].price);
                    $('.price_'.concat(data[i].code)).text(data[i].price);

                }

                // 改变页面内容
                var t = null, buyPrice = null, offPoint = null;
                for(var i = 0; i < _list.length; i++){
                    t = _list[i];
                    buyPrice = Number(t.buyPoint);
                    if(newPrice[t.code] && t.model == 2){
                        $('#'+t.id+'_1').text(formatNumber(newPrice[t.code], 0));
                        offPoint = Number(t.offPoint);
                        // 校验是否平仓
                        if(newPrice[t.code] <= buyPrice - offPoint || newPrice[t.code] >= buyPrice + offPoint){
                            $("#holdDiv_"+t.id).remove();
                            _list.splice(i, 1);
                            i--;
                        }
                    }
                    if(t.model == 0){
                        marketSell(newPrice, t);
                    }

                    $('#'.concat(t.id)).text((Number(t.winMoney) / Number(t.money) * 100) + "%");
                }
            }
        });
        _timeout_fun = setTimeout(function () {
            refreshWinOrLoss();
        } , 1500);
    }
    refreshWinOrLoss();


    function marketSell(newPrice, data){

        console.log("buyUpDown=1---" + (newPrice[data.code] - data.profitMaxPoint));
        console.log("buyUpDown=0---" + (data.lossMaxPoint - newPrice[data.code]));
        /*显示盈亏*/
        var result;
        if(data.buyUpDown == 0){
            result = (newPrice[data.code] - Number(data.buyPoint)) * Number(data.pointValue);
        }else if(data.buyUpDown == 1){
            result = (Number(data.buyPoint) - newPrice[data.code]) * Number(data.pointValue);
        }else{

        }
        $("#pre_".concat(data.id)).text(result.toFixed(2));
        /*if(result > 0){
            console.log("盈:" + result);

        }else if(result < 0){
            console.log("亏:" + result);
        }else{
            console.log("平");
        }*/

        /* 平仓移除市场订单 */
        if((data.buyUpDown == 1 && data.profitMaxPoint - newPrice[data.code] >= 0) || (data.buyUpDown == 1 && newPrice[data.code] - data.lossMaxPoint >= 0) ||
                (data.buyUpDown == 0 && data.lossMaxPoint - newPrice[data.code] >= 0) || (data.buyUpDown == 0 && newPrice[data.code] - data.profitMaxPoint >= 0)){
            $('#holdDiv_'+ data.id).remove();
        }else{

        }
    }

</script>
</body>
</html>