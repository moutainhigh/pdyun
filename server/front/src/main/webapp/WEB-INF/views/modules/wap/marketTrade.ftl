<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
<#include "/wap/header.ftl" >
<#assign footer = "market"/>
    <link href="${ctxStatic}/all/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/jaioyi.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>
<#--<link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>-->
    <link href="${ctxStatic}/all/css/xaidan.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/jiaoyiyueno.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/jiaoyixiadan.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/buzaijiaoyi.css" rel="stylesheet" type="text/css"/>
    <link href="${ctxStatic}/all/css/market.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"  src="${ctxStatic}/js/rem.js"></script>
    <title>${title}</title>
    <style type="text/css">
        .success{
            width: 7rem;
            overflow: hidden;
            height: 4.6rem;
            border-radius: .2rem;
        }
        ::-webkit-input-placeholder {
            font-size: 1px;
            color: #b5abab;
        }
        .my-bar{
            font-size:.4rem;
        }
        li.my-bar-item p{
            font-size:.5rem;
        }
    </style>
</head>
<body id="fixation">

<section class="wrap-page">
    <ul class="user_b clearfix">
        <li class="user_money left">
            <div class="in_user left text-center">
                <a href="#">
                    <div class="img"><img src="${user.userHeader!'${ctxStatic}/all/image/logo_header.png'}" alt=""></div>
                </a>
            </div>
            <div class="vm_text">
                <p class="vm_name" onclick="window.location.href = ctxWap + '/user?r='+Math.random()">个人账户(元)</p>
                <p class="vm_person">¥ ${user.money}</p>
            </div>
        </li>
        <li class="user_button right">
            <div class="ac_btn">
                <a onclick="window.location.href = '${ctxWap}/pay/recharge'" class="btn_charge ">充值</a>
                <a onclick="window.location.href = '${ctxWap}/money/out'" class="btn_carry">提现</a>
            </div>
        </li>
    </ul>
    <div class="user_top">
    <#if !code??><#assign code=contracts[0].code/></#if>
    <#list contracts as it>
        <#if it.code == code><#assign _cc=it_index/></#if>
    </#list>
        <div class="bd">
            <ul class="ita_t baojia_t my-bar" id="contracts_ul" style="margin-top: -0.1rem;">
            <#list contracts as it>
                <li class="my-bar-item show1 my-bar-item_bj  <#if it.code == code>on<#assign _cc=it_index/></#if>" id="show1_${it_index}" onclick="show1(${it_index});">
                    <strong>${it.name}</strong>
                    <#if !isMarketOpen || !it.marketOpen || !(markets.get(it_index)??)>
                        <p>休市</p>
                    <#else>
                        <#assign ab=markets[it_index].price?number - markets[it_index].open?number/>
                    <#--<#assign a=(markets[it_index].price?number - markets[it_index].open?number)*100/(markets[it_index].open?number)/>-->
                        <#assign it_m=markets[it_index]/>
                        <#assign price=it_m.price?number/>
                        <#if it_m.price?number - it_m.open?number gt 0>
                            <p id="show1_${it.code}" class="baojia_red red">
                                <#if it.precision==0>${price?string('###')}</#if>
                                <#if it.precision==1>${price?string('###.0')}</#if>
                                <#if it.precision==2>${price?string('###.00')}</#if>
                                <#if it.precision==3>${price?string('###.000')}</#if>
                                <i></i></p>
                        <#--<span id="show2_${it.code}" class="red"><#if ab gt 0>+</#if>#{ab;M2}  <#if a gt 0>+</#if>#{a;M2}%</span>-->
                        <#else>
                            <p id="show1_${it.code}" class="green">
                                <#if it.precision==0>${price?string('###')}</#if>
                                <#if it.precision==1>${price?string('###.0')}</#if>
                                <#if it.precision==2>${price?string('###.00')}</#if>
                                <#if it.precision==3>${price?string('###.000')}</#if>
                                <i></i></p>
                        <#--<span id="show2_${it.code}" class="green"><#if ab gt 0>+</#if>#{ab;M2}  <#if a gt 0>+</#if>#{a;M2}%</span>-->
                        </#if>
                    </#if>
                </li>
            </#list>
            </ul>
            <ul class="tempWrap jine_time" style="display: none">
                <li class="white jine  white_money">金额</li>
                <li class="whitea jine cash fuhao white_jian" onclick="addMoney(-1);">-</li>
                <li class="white small qian  white_money"  onclick="showInput(this);">¥ <span id="selectMoney">100</span><#--<b id="selectMoney1"></b>--></li>
                <li class="whitea jine cash fuhao white_jia" onclick="addMoney(1);">+</li>
            <#--<li class="white small quan">券<span id="selectCoupon">50</span><b id="selectCoupon1"></b></li>-->
                <li class="white dqtime dp_time">点位</li>
                <li class="white time_miao white_money" onclick="showSelectOffTime1()"><span id="selectOffTime">1点</span><i></i><#--<b id="selectOffTime1"></b>--></li>

            <#list contracts as it>
                <div id="selectOffTimeDiv_${it.code}" class="time_miao" style="color: #737373;display: none; position: absolute; width: 2.35rem; height: auto; z-index: 1000; left: 100%; margin-left: -2.55rem;margin-top: 0.77rem;">
                    <#list it.offPoints?split(",") as points>
                        <div style="width: 95%; margin-left: 0.2rem; height: 1rem; line-height: 1rem; margin-top: 0.08rem; background-color: #dedede; text-align: center; font-size: 0.4rem;" onclick="selectOffPoint(${points_index});">${points}点</div>
                    <#--                        <div style="width: 95%; margin-left: 0.2rem; height: 1rem; line-height: 1rem; margin-top: 0.08rem; background-color: #dedede; text-align: center; font-size: 0.4rem;" onclick="selectOffPoint(1);">5点</div>
                                            <div style="width: 95%; margin-left: 0.2rem; height: 1rem; line-height: 1rem; margin-top: 0.08rem; background-color: #dedede; text-align: center; font-size: 0.4rem;" onclick="selectOffPoint(2);">15点</div>
                                            <div style="width: 95%; margin-left: 0.2rem; height: 1rem; line-height: 1rem; margin-top: 0.08rem; background-color: #dedede; text-align: center; font-size: 0.4rem;" onclick="selectOffPoint(3);">60点</div>-->
                    </#list>
                </div>
            </#list>
            </ul>
        <#list contracts as it>
            <#if markets[it_index]??>
                <#assign it_m=markets[it_index]/>
                <#assign ab=markets[it_index].price?number - markets[it_index].open?number/>
            <#--<#assign a=(markets[it_index].price?number - markets[it_index].open?number)*100/(markets[it_index].open?number)/>-->
            <#--                        <div class="gd show2" id="show2_${it_index}" style="display: none;">
                                        <div class="gd_number left">
                                            <div class="die left">
                                                <p class="die_p <#if ab gt 0>die_red</#if>" id="show3_${it.code}">${it_m.price}</p>
                                                <p class="die_d <#if ab gt 0>die_red</#if>" id="show4_${it.code}"><#if ab gt 0>+</#if>#{ab;M2}(<#if a gt 0>+</else>#{a;M2}</#if>%)</p>
                                            </div>
                                            <div id="show5_${it.code}" class="<#if it_m.price?number - it_m.open?number gt 0>lianj_red<#else>lianj_green</#if> left"></div>
                                        </div>
                                        <ul class="content_details right">
                                            <li>最高 <p class="red zhangfu" id="high_${it.code}">${it_m.high}</p></li>
                                            <li>最低 <p class="green zhangfu" id="low_${it.code}">${it_m.low}</p><i></i></li>
                                            <li>昨收 <p class="zhangfu" style="color: #BEBDBC;" id="close_${it.code}">${it_m.close}</p><i></i></li>
                                            <li>今开 <p class="zhangfu" style="color: #BEBDBC;" id="open_${it.code}">${it_m.open}</p><i></i></li>
                                        </ul>
                                    </div>-->
            </#if>
        </#list>
            <div id="container" class="xiantu" style="margin-top: 0.5rem; border: 0;" ></div>
            <ul class="content_fenshixian">
                <li class="kdata_c on" onclick="showKClick(0);">分时线</li>
                <li id="1" class="kdata_c" onclick="showKClick(1);">1M</li>
                <li id="5" class="kdata_c" onclick="showKClick(2);">5M</li>
                <li style="display: none;" id="15" class="kdata_c" onclick="showKClick(3);">15M</li>
                <li id="30" class="kdata_c" onclick="showKClick(4);">30M</li>
                <li id="1h" class="kdata_c" onclick="showKClick(5);">1H</li>
                <li id="1d" class="kdata_c" onclick="showKClick(6);">1D</li>
            </ul>
        </div>
        <div class="b_btn clearfix">
            <a class="left buy_up" style="width: 3.5rem;" onclick="sureBox(0);">
                <i class="icon i_z"></i>买涨
            </a>
            <a class="buy_shouyi" style="width: 2.6rem;">
                    <h2 id="ownerlow" style="line-height: 1.3rem; font-size: .6rem;"><#if markets[_cc]??>#{markets[_cc].price?number;M0}<#else>休市</#if></h2>
                </a>
            <a class="right buy_down" style="width: 3.5rem;" onclick="sureBox(1);">
                买跌<i class="icon i_die"></i>
            </a>
        </div>
    </div>
</section>

<!--下单确认弹出框-->
<#--<div class="popBox" id="sureBox" style="display: none;">
    <div class="popCont">
        <div class="pop_bd pop_bd_text pop_bd_text_kuang  f36">
            <div class="tishi shiydjqa" style="background: #000">
            &lt;#&ndash;<div class="tishi_text shiydjq">使用代金券：<span id="showCoupon">50</span></div>&ndash;&gt;
                <div class="close"><img onclick="hideBox('sureBox');" src="all/image/q_03.png"></div>
            </div>
            <div class="pop_bd_center">
                <div class="xiadan_top">
                    <div class="xiadan_top_lf left clearfix">
                        <div class="center pinz left clearfix">
                            <p class="pinz_yellow center clearfix">交易品种</p>
                            <p class="pinz_white center clearfix" id="showContractName">铜</p>
                        </div>
                        <img src="all/image/qwqw44_26.png" class="xiadan_top_lf_line left">
                    </div>
                    <div class="right pinza right">
                        <p class="pinz_yellow">投资金额</p>
                        <p class="pinz_white" id="showMoney">&yen;200.00</p>
                    </div>
                </div>
                <div class="xiadan_top_line"></div>
            </div>
            <div class="pop_bd_center">
                <div class="xiadan_top">
                    <div class="xiadan_top_lf left clearfix">
                        <div class="center pinz left clearfix">
                            <p class="pinz_yellow center clearfix">收益率</p>
                            <p class="pinz_white center clearfix" id="showPercentProfit">78%</p>
                        </div>
                        <img src="all/image/qwqw44_26.png" class="xiadan_top_lf_line left">
                    </div>
                    <div class="right pinza right">
                        <p class="pinz_yellow">到期点位</p>
                        <p class="pinz_white" id="showOffTime"></p>
                    </div>
                </div>
                <div class="xiadan_top_line"></div>
            </div>
            <div class="pop_bd_center">
                <div class="xiadan_top">
                    <div class="xiadan_top_lf left clearfix">
                        <div class="center pinz left clearfix">
                            <p class="pinz_yellow center clearfix">最新价格</p>
                            <p class="pinz_white center clearfix red" id="showPrice">3865</p>
                        </div>
                        <img src="all/image/qwqw44_26.png" class="xiadan_top_lf_line left">
                    </div>
                    <div class="right pinza">
                        <div class="pinz_yellow">委托方向</div>
                        <div class="pinz_white red">
                            <div id="showUpAndDown" class="shangzhang left">买涨</div>
                            <div id="showUpAndDown1" class="zhang_red left"></div>
                        </div>
                    </div>
                </div>
                <div class="xiadan_top_line"></div>
            </div>
            <button class="queding clearfix" onclick="_make();">确定</button>
        </div>
    </div>
</div>-->
<div class="popBox" id="sureBox" style="display: none;">
    <div class="popCont">
        <div class="pop_bd f36" style="height:10rem;">
            <a class="closed" onclick="hideDiv('#sureBox')"></a>
            <img src="images/icon/red.png" class="m_tag" alt="">
            <p class="m_text">建仓买涨</p>
            <div class="pho_bd_item" style="display: block;">
                <div class="po_a">
                    <h3>购买金额（元）</h3>
                    <div class="pa_yc">
                        <ul class="pa_y clearfix" id="setMoneys">
                            <li class="on">¥10</li>
                            <li>¥50</li>
                            <li>¥100</li>
                            <li>¥200</li>

                        </ul>
                    </div>
                    <div class="p_tips">
                        手续费 <em class="cred" id="fee">0元</em>（单向收取)
                    </div>
                    <div class="lot">
                        购买手数 :
                        <div class="gw_num">
                            <em class="jian">-</em>
                            <input type="text" value="1" class="num" id="holdCount" readonly="readonly"/>
                            <em class="add">+</em>
                        </div>
                    </div>
                </div>
            </div>
            <div class="pho_bd_item">
            </div>
            <div class="po_b">
                <h3>止盈止损（%）</h3>
                <ul>
                    <li class="clearfix ykItem" id="stopProfit">
                        <strong class="left">止盈</strong>
                        <span class="on">40</span>
                        <span>60</span>
                        <span>100</span>
                    </li>
                    <li class="clearfix ykItem" id="stopLoss">
                        <strong class="left">止损</strong>
                        <span class="on">40</span>
                        <span>60</span>
                        <span>100</span>
                    </li>
                </ul>
            </div>
            <div class="p_tips reason text-center">
                合理的止盈止损，有利于锁定利益，降低风险！
            </div>
        </div>
        <div class="pop_btn text-center">
            <button class="btn1" style="display: none">使用代金券</button>
            <button class="btn2" onclick="_make()">下单</button>
        </div>
    </div>
</div>
<#--<div class="popBox" id="downBox" style="display: none;">
    <div class="popCont">
        <div class="pop_bd f36">
            <a class="closed"></a>
            <img src="images/icon/green.png" class="m_tag" alt="">
            <p class="m_text">建仓买跌</p>
            <div class="pho_bd_item" style="display: block;">
                <div class="po_a">
                    <h3>购买金额（元）</h3>
                    <div class="pa_yc">
                        <ul class="pa_y lv clearfix">
                            <li class="on">¥10</li>
                            <li>¥50</li>
                            <li>¥100</li>
                            <li>¥200</li>
                            <li>¥1000</li>
                            <li>¥2000</li>
                        </ul>
                    </div>
                    <div class="p_tips">
                        <span class="poundage">手续费 <em class="cred">0.75元</em>（单向收取)</span>
                    </div>
                </div>
            </div>
            <div class="pho_bd_item">
            </div>
            <div class="po_b">
                <h3>止盈止损（%）</h3>
                <ul>
                    <li class="clearfix ykItem">
                        <strong class="left">止盈</strong>
                        <span class="on">40</span>
                        <span>60</span>
                        <span>100</span>
                    </li>
                    <li class="clearfix ykItem">
                        <strong class="left">止损</strong>
                        <span class="on">40</span>
                        <span>60</span>
                        <span>100</span>
                    </li>
                </ul>
            </div>
            <div class="p_tips reason text-center" style="padding-top: 1.2rem">
                合理的止盈止损，有利于锁定利益，降低风险！
            </div>
        </div>
        <div class="pop_btn text-center">
            <button class="btn1">下单</button>
            <button class="btn2">重置</button>
        </div>
    </div>
</div>-->
<!--金额不足弹出框-->
<div class="popBox" id="moneyNotEnoughBox" style="display: none;">
    <div class="popCont" style="display: block">
        <div class="pop_bd yuenk f36">
            <div class="tishi">
                <div class="tishi_text left"><i class="img"></i>提示</div>
                <div class="close"><img onclick="hideBox('moneyNotEnoughBox')" src="all/image/q_03.png"></div>
            </div>
            <p class="xiadan_num xiadan_no">414，余额不足，交易失败！</p>
            <div class="nowc">
                <div class="left_no left">
                    <span>现在充值</span>
                    <img src="all/image/right-no.png">
                </div>
                <div class="now_ch right">
                    <button class="sure yue_sure" onclick="hideBox('moneyNotEnoughBox')">确认</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!--下单成功弹出框-->
<div class="popBox" id="successBox" style="display: none;">
    <div class="popCont">
        <div class="pop_bd f36 success" style="overflow: hidden">
            <div class="tishi">
                <div class="tishi_text left"><i class="img"></i>提示</div>
                <div class="close"><img onclick="hideBox('successBox')" src="all/image/q_03.png"></div>
            </div>
            <div class="duihao">
                <img src="all/image/sew_07.png" class="left img">
                <p class="xiadan_num_success left">下单成功！</p>
            </div>
            <button class="sure make_sure" onclick="hideBox('successBox')">确认</button>
        </div>
    </div>
</div>

<!-- 不在交易时段-->
<div class="popBox" id="stopBox" style="display: none;">
    <div class="popCont">
        <div class="pop_bd buzaijiaoyi f36">
            <div class="tishi">
                <div class="tishi_text left"><i class="img"></i>提示</div>
                <div class="close"><img onclick="hideBox('stopBox')" src="all/image/q_03.png"></div>
            </div>
            <p class="xiadan_num xiushi">休市</p>
            <p class="no">或不在交易时段</p>
            <button class="sure make_surea" onclick="hideBox('stopBox')">确认</button>
        </div>
    </div>
</div>

<div id="dragger" style="position: absolute; left: 100%; top: 9rem; margin-left: -1.8rem; width: 1.8rem; height: .7rem; z-index: 2000;margin-top: 4rem" onclick="window.location.href = '${ctxWap}/user/userMessageList'">
    <div style="position: absolute; top: -0.28rem; z-index: 2; background: #dedede; width: 0.4rem; height: 0.4rem; border-radius: 0.2rem;
                opacity: 1; filter: alpha(opacity=100); -moz-Opacity：1; text-align: center; line-height: 0.4rem; color: #e37000; font-size: 0.25rem;">${countNewMessage}</div>
    <div style="height: .7rem; background: #dedede; opacity: 0.9; filter: alpha(opacity=90); -moz-Opacity：0.9;
            border-radius: 10px 0px 0px 10px;">
        <img src="${ctxStatic}/all/image/wqqwwqqwwqqwqw_46.png" style="height: .4rem; margin-top: 0rem; margin-left: 0.25rem;"/>
        <span style="line-height: .7rem; font-size: .32rem; color: #e37000; font-weight: normal;">消息</span>
    </div>
</div>
<#include "/wap/footer.ftl"/>
<script type="application/javascript">
    $('#container').height(document.body.clientHeight - $('.ita_t').outerHeight() - $('.bd').outerHeight()
            - $('.b_btn').outerHeight() - $('.user_b ').outerHeight() + $('#container').outerHeight()+20);
</script>
<script type="text/javascript" src="${ctxStatic}/js/picker.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/drager.js"></script>
<script type='text/javascript' src='${ctxStatic}/asserts/highcharts/highstock.js' charset='utf-8'></script>

<script type="text/javascript">
    $(document).ready(function(){
        //加的效果
        $(".add").click(function(){
            var n=$(this).prev().val();
            var num=parseInt(n)+1;
            if(num > 100){
                layer.msg("购买上限了!");
                return;
            }
            $(this).prev().val(num);
        });
        //减的效果
        $(".jian").click(function(){
            var n=$(this).next().val();
            var num=parseInt(n)-1;
            if(num == 0){
                return
            }
            $(this).next().val(num);
        })});
</script>

<script type="application/javascript" charset="utf-8">
    // 购买金额选择
    var _selectMoney = 100;

    drag('dragger', {
        drayX: false
    });
    function hideBox() {
        $('.popBox:visible').hide();
    }
    function showBox(id) {
        hideBox();
        $(id).show();
    }

    function hideDiv(id){
        $(id).hide();
    }

    function sureBox(d) {
        if(!_contracts[current_code_p].marketOpen){
            showBox('#stopBox');
            return;
        }
        _direction = d;

        if(0 == d){
            $('.m_tag').attr("src", "images/icon/red.png");
            $('.m_text').text("建仓买涨");
        }else if(1 == d){
            $('.m_tag').attr("src", "images/icon/green.png");
            $('.m_text').text("建仓买跌");
        }
        showBox('#sureBox');
        /* _selectMoney = 100;*/
    }

    var current_code_p = ${_cc};
    var _contracts = ${_contracts};
    var _broadcastsJson = ${broadcastsJson};
    var _codes = ${_codes};
    var _isMarketOpen = ${_isMarketOpen};
    var _user_money = ${user.money?c};
    var _user_coupon_money = ${user.couponMoney?c};
    var SELECTMONEY_MAX = 10000;

    function load(){
        up:for(var i =0; i < _contracts.length; i++){
            var offPoints = _contracts[current_code_p].offPoints.split(",");
            var percentProfits = _contracts[current_code_p].percentProfits.split(",");
            for(var i = 0; i < offPoints.length; i++){
                $("#selectOffTime").text(offPoints[i].concat('点'));
                $("#showPercentProfit").text(percentProfits[i].concat('%'));
                break up;
            }
        }
    }

    // 根据是否能获取最新数据来二次判断 开盘情况
    <#list markets as it>
    if(_contracts[${it_index}].marketOpen){
        _contracts[${it_index}].marketOpen = <#if it??>true<#else>false</#if>;
    }
    </#list>

    //        var _money_arr = [];
    //        initMoneyArr(0);
    //        var _selectMoney = _money_arr[0];
    //        $('#selectMoney').html(_selectMoney);
    //        var p1 = pick('selectMoney1', _money_arr, _selectMoney, function (val) {
    //            _selectMoney = val;
    //            $('#selectMoney').html(val);
    //        });

    function addMoney(i) {
        var val = $('#selectMoney').text();
        if(val == ''){
            return;
        }
        if(Number(val) < 100 && Number(i) == 1){
            _selectMoney = 100;
        }else if(Number(val) <= 100 && Number(i) == -1){
            _selectMoney = Number(val) + i * 80;
        }else{
            _selectMoney = Number(val) + i * 100;
        }

        _selectMoney = _selectMoney < 20 ? SELECTMONEY_MAX : _selectMoney;
        _selectMoney = _selectMoney > SELECTMONEY_MAX ? 20 : _selectMoney;
        $('#selectMoney').html(_selectMoney);
    }

    // 代金券选择
    var _coupons = ${_user_coupons};
    _coupons.splice(0, 0, {
        id: null,
        money: _coupons.length == 0 ? '无可用代金券' : '不使用'
    });
    var _coupons_arr = [];
    for(var i=0; i < _coupons.length; i++){
        _coupons_arr.push(_coupons[i].money+(i != 0 ? '(满'+_coupons[i].minMoney+'元)' : ''));
    }
    $('#selectCoupon').html((_coupons.length == 1 ? '无可用代金券' : '不使用'));
    var _selectCoupon = 0;
    //        pick('selectCoupon1', _coupons_arr, _coupons_arr[0], function (val, index) {
    //            _selectCoupon = index;
    //            $('#selectCoupon').html(val);
    //        });

    // 时间选择
    //var _offTime_arr = ['1点', '5点', '15点','60点'], _offTime_arr_temp = ['1M', '5M', '15M','60M'];
    //var _offPoint_arr = ['1点', '5点', '15点','60点'], _offPoint_arr_temp = ['1', '5', '15','60'];
    var _offPoint_arr = _contracts[current_code_p].offPoints.split(','), _offPoint_arr_temp = _contracts[current_code_p].offPoints.split(',');
    var _selectOffPoint = 0;
    function selectOffPoint(p) {
        _selectOffPoint = p;
        $('#selectOffTimeDiv_'+_contracts[current_code_p].code).hide();
        $('#selectOffTime').html(_contracts[current_code_p].offPoints.split(',')[p]+'点');
//            $('#ownerlow').html(_contracts[current_code_p].percentProfits.split(',')[p]+'%');
        $('#showPercentProfit').text(_contracts[current_code_p].percentProfits.split(',')[p]+'%');
    }

    //        var _offTime_arr = [], _offTime_arr_temp = [];
    //        initOffTimeArr(0);
    //        var _selectOffTime = 0;
    //        $('#selectOffTime').html(_offTime_arr[_selectOffTime]);
    ////        $('#ownerlow').html(_contracts[0].percentProfits.split(',')[0]+'%');
    //        var p3 = pick('selectOffTime1', _offTime_arr, _offTime_arr[_selectOffTime], function (val, index) {
    //            _selectOffTime = index;
    //            $('#selectOffTime').html(val);
    ////            $('#ownerlow').html(_contracts[current_code_p].percentProfits.split(',')[index]+'%');
    //            $('#showPercentProfit').text(_contracts[current_code_p].percentProfits.split(',')[index]+'%');
    //        });
    //
    //        function initOffTimeArr(index){
    //            var _a = _contracts[index].offTimes.split(',');
    //            _offTime_arr.splice(0, _offTime_arr.length);
    //            _offTime_arr_temp.splice(0, _offTime_arr_temp.length);
    //            for(var i=0; i<_a.length; i++){
    //                _offTime_arr.push(Number(_a[i].replace(/M/g, ''))*60+'秒');
    //                _offTime_arr_temp.push(_a[i]);
    //            }
    //        }

    function initMoneyArr(index){
//            var _a = _contracts[index].stepMoneys.split(',');
//            _money_arr.splice(0, _money_arr.length);
//            for(var i=0; i<_a.length; i++){
//                _money_arr.push(_a[i]);
//            }
    }
    var _direction = 0;
    function show1(index) {
        var selectContract = index;
        $('.show1').removeClass('on');
        $('#show1_'+index).addClass('on');

        current_code_p = index;
        if(_contracts[index].marketOpen){
            $('#ownerlow').html($('#show1_'+_contracts[current_code_p].code).text());
            $("#selectOffTime").text(_contracts[current_code_p].offPoints.split(",")[0].concat("点"));
            $("#showOffTime").text(_contracts[current_code_p].offPoints.split(",")[0]);
        }else {
            $('#ownerlow').html('休市');
        }

        initMoneyArr(index);
//            p1.setVal(_selectMoney);
//            initOffTimeArr(index);
//            p3.setVal(_offTime_arr[_selectOffTime]);
        showKClick(0);
        _new_line_price_pre = null;
        _new_line_time_pre = null;
        initContractInfo(index);
    }

    var percentIndex = [null, 0, 0];

    /* 初始化商品信息：金额,止盈止损,手续费 */
    function initContractInfo(index){
        $('#setMoneys').empty();
        $('#stopProfit').empty();
        $('#stopLoss').empty();

        var info = _contracts[current_code_p];
        var setMoneys = info.stepMoneys.split(','), winPercent = info.profitPercentages.split(','), lossPercent = info.lossPercentages.split(',');
        for(var i = 0; i < setMoneys.length; i++){
            var setpMoney = setMoneys[i];
            $('#setMoneys').append('<li onclick="selectMoney('+ i +',this)">¥'+ setpMoney +'</li>');
        }

        var winArry = [], lossArry = [];
        winArry.push('<strong class="left">止盈</strong>');
        winArry.push('<span onclick="selectWinAndLoss('+ null +',0,this)">不设</span>');
        for(var i = 0; i < winPercent.length; i++){
            /*if(0 != i){*/
                winArry.push('<span onclick="selectWinAndLoss('+ i +',0,this)">'+ winPercent[i] +'</span>');
            /*}*/
            /*winArry.push('<span onclick="selectWinAndLoss('+ i +',0,this)">'+ (i == 0 ? '不设' : winPercent[i]) +'</span>');*/
        }
        /*winArry.push('<input class="inputk" id="winPercent" type="text" onkeydown="removeSelectPercent(0)" placeholder="手动设置" value=""/>');*/

        lossArry.push('<strong class="left">止损</strong>');
        for(var i = 0; i < lossPercent.length; i++){
            /*if(0 != i){
                lossArry.push('<span onclick="selectWinAndLoss('+ i +',1,this)">'+ lossPercent[i] +'</span>');
            }*/
            lossArry.push('<span onclick="selectWinAndLoss('+ i +',1,this)">'+ (i == 0 ? '不设' : lossPercent[i]) +'</span>');
        }
        /*lossArry.push('<input class="inputk" id="lossPercent" type="text" onkeydown="removeSelectPercent(1)" placeholder="手动设置" value=""/>');*/

        $('#stopProfit').append(winArry.join(''));
        $('#stopLoss').append(lossArry.join(''));

        /*默认初始化手续费*/
        $('#fee').text('0元');
    }

    /*选择下单金额*/
    function selectMoney(index, obj){
        obj = $(obj);
        obj.siblings().removeClass('on');
        obj.addClass('on');
        /* 获取手续费 */
        var fees = _contracts[current_code_p].fees.split(",");
        $('#fee').text(fees[index].concat('元'));
        percentIndex[2] = index;
    }

    function selectWinAndLoss(index, status, obj){
        if(0 == status){
            $('#winPercent').val('');
            $('#stopProfit').children().removeClass('on');
            $(obj).addClass('on');
        }else{
            $('#lossPercent').val('');
            $('#stopLoss').children().removeClass('on');
            $(obj).addClass('on');
        }
        percentIndex[status] = index;
    }

    // 下单
    function _make() {
        var _con = _contracts[current_code_p];
        /*验证是否手动设置止盈止损*/
        var profitMax = $('#winPercent').val();
        var lossMax = $('#lossPercent').val();
        if(profitMax != '' && profitMax != undefined){
            console.log('止盈:'.concat(profitMax));
        }else{
            profitMax = _con.profitPercentages.split(',')[percentIndex[0]];
        }

        if(lossMax != '' && lossMax != undefined){
            console.log('止损:'.concat(lossMax));
        }else{
            lossMax = _con.lossPercentages.split(',')[percentIndex[1]];
        }
        var reg = new RegExp("^\\+?[1-9][0-9]*$");
        if(!reg.test(lossMax)){
            layer.msg("请输入正确的止损比例！");
            return;
        }

        var obj = {
            code: _con.code,
            type: 0,
            money: _con.stepMoneys.split(',')[percentIndex[2]],
            buyUpDown: _direction,
            profitMax: profitMax,
            lossMax: lossMax,
            holdCount: $('#holdCount').val()
        };
        if(_selectCoupon != 0){
            if(Number(_coupons[_selectCoupon].minMoney) > Number(obj.money)){
                layer.msg('此优惠券不满足使用规则，请重新选择！');
                return;
            }
        }

        $.ajax({
            url: ctx + '/tradeMarket/make',
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify(obj),
            success: function(data){
                //去掉使用的代金券
//                    if(_selectCoupon != 0){
//                        _coupons.splice(_selectCoupon, 1);
//                        _coupons_arr.splice(_selectCoupon, 1);
//                    }
                showBox('#successBox');
            },
            useDefaultError: false,
            error: function(p1, p2, p3, result){
                if(result.code == '2'){
                    showBox('#moneyNotEnoughBox');
                }else if(result.code == '99'){
                    showBox('#stopBox');
                }else{
                    layer.msg(result.error);
                }
            }
        });
    }

    $(function(){
        /* 获取产品个数，适应宽度 */
        var contractCount = $('#contracts_ul li').length;
        var width = 100 / Number(contractCount);
        $('#contracts_ul li').css('width', width + "%");

        load();
        // 定时刷新
        showKClick(0);

        setTimeout(function () {
            marketNew();
        }, 1000);

        initContractInfo(0);
    });

    var _time = null;
    // 固定频率动态刷新行情
    function marketNew(){
        if(!!window.console){

        }
        if(_isMarketOpen && _contracts[current_code_p].marketOpen){
            var __codes = [];
            for(var i = 0; i < _contracts.length; i++){
                if(!_contracts[i].marketOpen){
                    continue;
                }
                __codes.push(_contracts[i].code);
            }
            $.ajax({
                url: ctx + '/market/new',
                type: 'POST',
                contentType: 'application/json;charset=UTF-8',
                data: JSON.stringify({
                    "codes": __codes.join(',')
                }),
                success: function(data){
                    if(!data || data.length == 0){
                        return ;
                    }
                    if(!!window.console){
                        if(!data[0].timestamp){
                            console.log(JSON.stringify(data));
                        }
                    }
                    var isZhang = false;
                    var it = null, code = null, price = null, open = null, off1 = null, off2 = null;
                    var _inde = null;
                    for(var i = 0; i < data.length; i++){
                        it = data[i];
                        if(it == ''){
                            continue;
                        }
                        code = it.code;

                        price = Number(it.price);
                        if(price <= 0){
                            continue;
                        }
                        open = Number(it.open);
                        isZhang = price > open;
                        off1 = (isZhang ? '+' : '') + (price-open);
                        off2 = (isZhang ? '+' : '') + decimalAfter2((price-open)*100/open) +'%';
                        isPing = price == open;
                        for(var j=0; j<_contracts.length; j++){
                            if(_contracts[j].code == code){
                                _inde = j;
                            }
                        }
                        // 改变页面显示
                        $('#show1_'+it.code).html(formatNumber(price, _contracts[_inde].precision)+'<i></i>');
                        $('#show2_'+it.code).html(off1+' '+off2);
                        if(isZhang){
                            $('#show1_'+it.code).removeClass('baojia_green').removeClass('green');
                            $('#show1_'+it.code).removeClass('baojia_grey').removeClass('grey').addClass('baojia_red').addClass('red');
                            $('#show2_'+it.code).removeClass('green').addClass('red');
                        }else {
                            if(isPing){
                                $('#show1_'+it.code).removeClass('baojia_grey').removeClass('grey')
                                $('#show1_'+it.code).removeClass('baojia_red').removeClass('red').addClass('baojia_grey').addClass('grey');
                                $('#show2_'+it.code).removeClass('red').addClass('grey');
                            }else{
                                $('#show1_'+it.code).removeClass('baojia_red').removeClass('red');
                                $('#show1_'+it.code).removeClass('baojia_grey').removeClass('grey').addClass('baojia_green').addClass('green');
                                $('#show2_'+it.code).removeClass('grey').addClass('green');
                            }
                        }

//                        $('#show3_'+it.code).html(price);
//                        $('#show4_'+it.code).html(off1+'('+off2+')');
//                        $('#show3_'+it.code).removeClass('die_red').addClass(isZhang ? 'die_red' : '');
//                        $('#show4_'+it.code).removeClass('die_red').addClass(isZhang ? 'die_red' : '');
//                        $('#show5_'+it.code).removeClass('lianj_red').removeClass('lianj_green').addClass(isZhang ? 'lianj_red' : 'lianj_green');
//
//                        $('#high_'+it.code).html(it.high);
//                        $('#low_'+it.code).html(it.low);
//                        $('#open_'+it.code).html(it.open);
//                        $('#close_'+it.code).html(it.close);

                        if(it.code == _contracts[current_code_p].code){
                            $('#showPrice').text(formatNumber(price, _contracts[current_code_p].precision));
                            $('#ownerlow').html(formatNumber(price, _contracts[current_code_p].precision));
                            if(!_is_loading){
                                // 矫正时间
                                _time = it.timestamp;
                                showNewLine(price, _time);
                            }
                        }
                    }
                }
            });
        }

        setTimeout(function () {
            marketNew();
        }, 350);
    }
    // K线图类型
    var _marketKData_interval_position = 0;
    var _marketKData_interval_val = ["1","1","5","15","30","60","1440"];
    var _marketKData_interval_setTimeout_fun = null;
    function marketKData(){
        var now = new Date();
        var hour = now.getHours();
        var minute = now.getMinutes();
        var second = now.getSeconds();

        var isR = true;
        // 每10秒刷新一次
        if(_marketKData_interval_position == 0 && second == 8){

        }else if(_marketKData_interval_position == 1 && second == 8){

        }else if(_marketKData_interval_position == 2 && minute%5 == 0 && second == 10){

        }else if(_marketKData_interval_position == 3 && minute%15 == 0 && second == 12){

        }else if(_marketKData_interval_position == 4 && minute%30 == 0 && second == 14){

        }else if(_marketKData_interval_position == 5 && minute == 0 && second == 16){

        }else{
            isR = false;
        }
        if(isR){
            showK(_marketKData_interval_position);
        }
        _marketKData_interval_setTimeout_fun = setTimeout(function () {
            marketKData();
        }, 1000);
    }
    // 是否重新绘制的标记
    var _p_pre = null;
    // 是否重新绘制标记线
    var _plotLine_refresh = false;
    function showKClick(p) {
        if(_p_pre != (p == 0 ? p : 1)){
            // 重新绘制K线图
            if(_chart1 != null){
                _chart1.destroy();
                _chart1 = null;
                _plotLine_refresh = true;
            }
            _p_pre = p == 0 ? p : 1;
        }

        $('.kdata_c').removeClass('on');
        $('.kdata_c').eq(p).addClass('on');

        clearTimeout(_marketKData_interval_setTimeout_fun);
        showK(p);
        if(_isMarketOpen && _contracts[current_code_p].marketOpen){
            _marketKData_interval_setTimeout_fun = setTimeout(function () {
                marketKData();
            }, 1000);
        }
    }
    var _chart1 = null;
    var _data = null;
    var _is_loading = false;
    function showK(p) {
        _marketKData_interval_position = p;
        var interval = _marketKData_interval_val[p];

        _is_loading = true;
        _data = [];
        $.ajax({
            url: ctx + '/market/kdata',
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify({
                "code": _contracts[current_code_p].code,
                "interval":interval
            }),
            success: function(data){
                console.log(JSON.stringify(data));
                var d = null;
                for(var i = 0; i < data.length ; i++){
                    d = data[i];
                    _data.push([d.timestamp, Number(d.open), Number(d.high), Number(d.low), Number(d.close)]);
                }
                if(!!window.console && _data.length > 1){
                    console.log(new Date(_data[_data.length-1][0]).toLocaleString() + '  ' + new Date(_data[_data.length-2][0]).toLocaleString());
                }
                _is_loading = false;
                //_chart1 = null;
                if(p == 0){
                    if(_chart1 == null){
                        _chart1 = new Highcharts.StockChart({
                            chart: {
                                //borderWidth: 1,
                                //marginTop: 10,
                                panning: true,
                                pinchType: 'x',
                                zoomType: 'x',
                                resetZoomButton: {
                                    position: {
                                        x: 0,
                                        y: -60
                                    }
                                },
                                backgroundColor: '#101419',
                                renderTo: $('#container')[0]
                            },
                            credits:{
                                enabled: false
                            },
                            rangeSelector:{
                                enabled: false
                            },
                            exporting:{
                                enabled: false
                            },
                            navigator: {
                                enabled: false
                            },
                            scrollbar: {
                                enabled: false
                            },
                            rangeSelector : {
                                enabled: false
                            },
                            xAxis: {
                                lineColor: '#444444'
                            },
                            yAxis: {
                                opposite: false,
                                gridLineWidth: 1,
                                gridLineColor: "#444444",
                                labels: {
                                    align: "right",
                                    x: 15
                                }
                            },
                            tooltip: {
                                borderColor: '#666666',
                                backgroundColor: 'rgba(247,247,247,0.6)'
                            },
                            series : [{
                                id: '1',
                                name : '行情',
                                data : _data,
                                type : 'area',
                                threshold : null,
                                tooltip : {
                                    valueDecimals: _contracts[current_code_p].precision
                                },
                                lineWidth: 1,
                                threshold: null,
                                animation: false,
                                fillColor : {
                                    linearGradient : {
                                        x1: 0,
                                        y1: 0,
                                        x2: 0,
                                        y2: 1
                                    },
                                    stops : [
                                        [0, Highcharts.getOptions().colors[0]],
                                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                                    ]
                                }
                            }]
                        });
                    }else{
                        _chart1.get("1").setData(_data);
                    }
                }else {
                    // 分时图转1分钟蜡烛图，数据点太多，截取40个点
                    if(p == 1){
                        _data.splice(0, _data.length - 20);
                    }
                    if(_chart1 == null){
                        _chart1 = new Highcharts.StockChart({
                            chart: {
                                panning: true,
                                pinchType: 'x',
                                zoomType: 'x',
                                resetZoomButton: {
                                    position: {
                                        x: 0,
                                        y: -60
                                    }
                                },
                                backgroundColor: '#101419',
                                renderTo: $('#container')[0]
                            },
                            credits:{
                                enabled: false
                            },
                            rangeSelector:{
                                enabled: false
                            },
                            exporting:{
                                enabled: false
                            },
                            navigator: {
                                enabled: false
                            },
                            scrollbar: {
                                enabled: false
                            },
                            rangeSelector : {
                                enabled: false
                            },
                            xAxis: {
                                lineColor: '#444444'
                            },
                            yAxis: {
                                opposite: false,
                                gridLineWidth: 1,
                                gridLineColor: "#444444",
                                labels: {
                                    align: "right",
                                    x: 15
                                }
                            },
                            tooltip: {
                                borderColor: '#666666',
                                backgroundColor: 'rgba(247,247,247,0.6)'
                            },
                            series: [{
                                id: "1",
                                name : '行情',
                                type: 'candlestick',
                                upColor: '#d52e32',
                                upLineColor: '#d52e32',
                                color: '#1aa93a',
                                lineColor: '#1aa93a',
                                data: _data,
                                animation: false,
                                tooltip: {
                                    valueDecimals: _contracts[current_code_p].precision
                                }
                            }]
                        });
                    }else{
                        _chart1.get("1").setData(_data);
                    }
                }
                if(_time == null){
                    _time = _data[_data.length-1][0];
                }
                var price = $('#show1_'+_contracts[current_code_p].code).text();
                if(/^\d+(\.\d+)?$/.test(price)){
                    showNewLine(Number(price), _time);
                }
            }
        });
    }

    // 添加标记线
    function addPlotLine(price){
        if(price == null){
            return;
        }
        _chart1.get('1').yAxis.removePlotLine('2');
        _chart1.get('1').yAxis.addPlotLine({
            color: '#d52e32',
            dashStyle: 'LongDash',
            id: '2',
            width: 1,
            value: price,
            label: {
                text: '<span style="color: red;font-family:\'微软雅黑\';">'+ formatNumber(price, _contracts[current_code_p].precision) + '('+_new_line_ms/1000.0+'s)'+'</span>',
                align: 'left',
                useHTML: true,
                y: -5,
                x: 0
            },
            zIndex: 1000
        });
    }

    // 计算价格变动
    var _new_line_time_pre = null;
    var _new_line_price_pre = null;
    var _new_line_ms = null;
    function showNewLine(price, time){
        if(_chart1 != null){
            if(_new_line_price_pre != price){

                _new_line_ms = _new_line_time_pre == null ? 0 : new Date().getTime() - _new_line_time_pre.getTime();
                _new_line_price_pre = price;
                _new_line_time_pre = new Date();

                addPlotLine(price);
            }
            if(_plotLine_refresh){
                addPlotLine(price);
                _plotLine_refresh = false;
            }
            refreshId1(price, time);
        }
    }

    function refreshId1(price, time){
        time = formatTime(time);
        if(_chart1 != null) {
            var _it = _chart1.get("1");
            if (_it != null) {
                var _p = _data.length == 0 ? null : _data[_data.length-1];
                var isRemove = false;
                var _last_p = _p;
                if(_p == null || _p[0] < time){
                    if(_last_p != null){
                        _p = [time, _last_p[4], _last_p[4], _last_p[4], _last_p[4]];
                    }else{
                        _p = [time, price, price, price, price];
                    }
                }else{
                    isRemove = true;
                }

                if(_marketKData_interval_position == 0){
                    if(isRemove && _p[1] == price){
                        return;
                    }
                    _p[1] = price;
                }else{
                    if(isRemove && _p[4] == price){
                        return;
                    }
                    _p[2] = Math.max(price, _p[2]);
                    _p[3] = Math.min(price, _p[3]);
                    _p[4] = price;
                }

                if(isRemove){
                    _it.removePoint(_data.length-1, false);
                }
                _it.addPoint(_p);
            }
        }
    }

    /**
     * 格式化时间戳，以适应K线图数据展示（主要是蜡烛图）
     * @param time
     * @returns {number}
     */
    function formatTime(time){
        var t = new Date(time);
//            if(_marketKData_interval_position == 0){
//                return t.getTime();
//            }
        var hour = t.getHours();
        var minute = t.getMinutes();
        var second = t.getSeconds();
        var date = t.getDate();
        var step = Number(_marketKData_interval_val[_marketKData_interval_position]);
        t.setMilliseconds(0);
        t.setSeconds(0);

        var off = 0;

        if(step < 60){
            if(step != 1){
                off = (minute - minute%step) + (minute%step != 0 || second != 0 ? step : 0);
                t.setMinutes(off);
            }
        }else if(step >= 60 && step < 1440){
            step = step/60;
            t.setMinutes(0);
            off = (hour - hour%step) + (hour%step != 0 || second != 0 ? step : 0);
            if(step != 1){
                t.setHours(off);
            }
        }else if(step == 1440){
            step = step/1440;
            t.setMinutes(0);
            t.setHours(0);
            off = (date - date/step) + (date%step != 0 || second != 0 ? step : 0);
            if(step != 1){
                t.setDate(off);
            }
        }
        if(!!window.console){
//                console.log(new Date(time).toLocaleString() + ' *********** ' + step + ' ************ ' + off + ' *********** ' + t.toLocaleString());
        }
        return t.getTime();
    }

    function showInput(the) {
        var it = $(the);
        it.html('<input id="__selectMoney" onblur="formaterSelectMoney(this);" autofocus '
                +' style="background:transparent;border:0px;width:100%;height:100%;text-align:center;" />');
        $('#__selectMoney').focus();
    }

    // ¥ <span id="selectMoney">100</span>
    function formaterSelectMoney(the) {
        var it = $(the).parent();
        var val = $('#__selectMoney').val();
        if(!/^[0-9]+$/.test(val)){
            val = 100;
        }
        _selectMoney = val;
        val = Number(val);
        val = Math.min(SELECTMONEY_MAX, Math.max(val-val%10, 20));
        _selectMoney = val;
        it.html('¥ <span id="selectMoney">'+val+'</span>');
    }

    function showSelect(the) {
        var it = $(the);
        var _arr = [];
        _arr.push('<select id="__selectOffTime" onblur="formaterSelectOffTime(this);" autofocus '
                +' style="text-align: center; background:transparent;border:0px;width:100%;height:100%;" >');
        for (var i=0; i<_offTime_arr.length; i++){
            _arr.push('<option value="'+i+'">'+_offTime_arr[i]+'</option>');
        }
        _arr.push('</select>');
        it.html(_arr.join(''));
        alert(_arr.join(''));
        $('#__selectOffTime').focus();
    }

    // <span id="selectOffTime">60秒</span>
    function formaterSelectOffTime(the) {
        var it = $(the).parent();
        it.html('<span id="selectOffTime">'+_offPoint_arr[$('#__selectOffTime').val()]+'</span>');
    }

    function hideShowSelectOffTime1(){
    <#list contracts as it>
        $('#selectOffTimeDiv_${it.code}').hide();
    </#list>
    }

    function showSelectOffTime1(){
        $(document).unbind('click', hideShowSelectOffTime1);
        $('#selectOffTimeDiv_'+_contracts[current_code_p].code).toggle(function () {
            $(document).unbind('click', hideShowSelectOffTime1);
        }, function () {
            setTimeout(function () {
                $(document).one('click', hideShowSelectOffTime1);
            }, 50);
        });
    }

    function removeSelectPercent(index){
        if(0 == Number(index)){
            $('#stopProfit').children().removeClass("on");
        }else{
            $('#stopLoss').children().removeClass("on");
        }
    }

    // Highcharts全局配置
    Highcharts.setOptions({
        global: {
            useUTC: true,
            timezoneOffset: -8*60
        },
        lang: {
            contextButtonTitle: "Chart context menu",
            decimalPoint: ".",
            downloadJPEG: "",
            downloadPDF: "",
            downloadPNG: "",
            downloadSVG: "",
            invalidDate: "",
            loading: "Loading...",
            months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            numericSymbols: [ "k" , "M" , "G" , "T" , "P" , "E"],
            printChart: "Print chart",
            rangeSelectorFrom: "From",
            rangeSelectorTo: "To",
            rangeSelectorZoom: "Zoom",
            resetZoom: "恢复",
            resetZoomTitle: "Reset zoom level 1:1",
            shortMonths:['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            shortWeekdays: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
            thousandsSep: "",
            weekdays: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"]
        }
    });
</script>
</body>
</html>