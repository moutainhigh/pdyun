
$(function(){
    var myScroll = new iScroll(document.body);

    // 定时刷新
    //an();
    showKClick(0);
    if(_isMarketOpen){
        setTimeout(function () {
            marketNew();
        }, 2000);
    }

});
//
var current_code_p = 0;
function _changeShow(index){
    current_code_p = index;
    $('.tong').removeClass('gaoliang');
    $('.tong').eq(index).addClass('gaoliang').show();

    $('.show1').hide();
    $('.show2').hide();
    $('.show1').eq(index).show();
    $('.show2').eq(index).show();
    showKClick(0, $('.kdatali')[0]);
}

// 固定频率动态刷新行情
function marketNew(){
    if(!!window.console){

    }
    $.ajax({
        url: ctx + '/market/new',
        type: 'POST',
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify({
            "codes": _codes.join(',')
        }),
        success: function(data){
            var isZhang = false;
            var it = null, code = null, price = null, open = null, off1 = null, off2 = null;
            for(var i = 0; i < data.length; i++){
                it = data[i];
                code = it.code;
                price = Number(it.price);
                open = Number(it.open);
                // 改变页面显示
                isZhang = price > open;
                off1 = (isZhang ? '+' : '') + (price-open);
                off2 = (isZhang ? '+' : '') + decimalAfter2((price-open)*100/open) +'%';
                if(isZhang){
                    $('#'+code+'_price').removeClass('green').addClass('red');
                }else{
                    $('#'+code+'_price').removeClass('red').addClass('green');
                }

                $('#con_price_'+code+', #cur_point_'+code).html(data[i].price);
                $('#con_price_1_'+code).html(off1);
                $('#con_price_2_'+code).html(off2);

                $('#top_'+it.code).html(it.price + '<p> ' + off1 + '(' + off2 + ')<p>');
                $('#show2_'+it.code+' p').each(function (index) {
                    switch (index){
                        case 0:
                            $(this).html(it.high);
                            break;
                        case 1:
                            $(this).html(it.low);
                            break;
                        case 2:
                            $(this).html(it.close);
                            break;
                        case 3:
                            $(this).html(it.open);
                            break;
                    }
                });
            }
        }
    });

    setTimeout(function () {
        marketNew();
    }, 2000);
}
// K线图类型
var _marketKData_interval_position = 0;
var _marketKData_interval_val = ["1","5","15","30","1h","1d"];
var _marketKData_interval_val_time = ["1","5","15","30","1h","1d"];
var _marketKData_interval_setTimeout_fun = null;
function marketKData(){
    // 先清除定时函数
    var now = new Date();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();

    var isR = true;
    if(_marketKData_interval_position == 0 && second == 5){

    }else if(_marketKData_interval_position == 1 && second == 8){

    }else if(_marketKData_interval_position == 2 && second == 10){

    }else if(_marketKData_interval_position == 3 && second == 12){

    }else if(_marketKData_interval_position == 4 && second == 14){

    }else if(_marketKData_interval_position == 5 && second == 16){

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
function showKClick(p) {
    $('.kdatali').removeClass('gaoliang');
    $('.kdatali').eq(p).addClass('gaoliang');
    clearTimeout(_marketKData_interval_setTimeout_fun);
    showK(p);
    if(_isMarketOpen){
        _marketKData_interval_setTimeout_fun = setTimeout(function () {
            marketKData();
        }, 1000);
    }
}
function showK(p) {
    _marketKData_interval_position = p;
    var interval = _marketKData_interval_val[p];

    $.ajax({
        url: ctx + '/market/kdata',
        type: 'POST',
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify({
            "code": _contracts[current_code_p].code,
            "interval":interval
        }),
        success: function(data){
            var _data = [];
            var d = null;
            for(var i = data.length - 1; i >=0 ; i--){
                d = data[i];
                _data.push([d.timestamp, Number(d.open), Number(d.high), Number(d.low), Number(d.close)]);
            }
            if(!!window.console){
                console.log(new Date(_data[_data.length-1][0]).toLocaleString() + '  ' + new Date(_data[_data.length-2][0]).toLocaleString());
            }
            if('1' == interval){
                $('#container').highcharts('StockChart', {
                    chart: {
                        panning: false,
                        pinchType: ''
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
                    series : [{
                        name : '行情',
                        data : _data,
                        tooltip: {
                            valueDecimals: (_data[0][1]+'').indexOf('.') != -1 ? 2 : 0
                        }
                    }]
                });
            }else {
                $('#container').highcharts('StockChart', {
                    chart: {
                        panning: false,
                        pinchType: ''
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
                    series: [{
                        name : '行情',
                        type: 'candlestick',
                        data: _data,
                        tooltip: {
                            valueDecimals: (_data[0][1]+'').indexOf('.') != -1 ? 2 : 0
                        }
                    }]
                });
            }
        }
    });
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
        resetZoom: "Reset zoom",
        resetZoomTitle: "Reset zoom level 1:1",
        shortMonths:['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        shortWeekdays: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
        thousandsSep: "",
        weekdays: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"]
    }
});
// 跳转到充值页面
function toRecharge() {
    window.location.href = ctxWap + '/pay/recharge?from=home&r='+Math.random();
}
