var _tradePwd = null;
/*
function sell(id){
    //if(_tradePwd == null){
        layer.open({
            content: '<input id="_tradePwd" type="password" style="line-height: 0.6rem; font-size: 0.4rem;" placeholder="请输入交易密码" value=""/>',
            btn: '确定',
            yes: function(index){
                _tradePwd = $('#_tradePwd').val();
                if(!/^.{6,}$/.test(_tradePwd)){
                    layer.msg('请输入正确的交易密码');
                    return;
                }
                $.ajax({
                    url: ctx + '/tradeMarket/sell',
                    type: 'PUT',
                    contentType: 'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        "id":id,
                        "tradePassword":md5(_tradePwd)
                    }),
                    success: function(data){
                        console.log(data);
                        // 重置chi持仓记录
                        $('#holdDiv_'+id).remove();
                        /!*for (var i = 0; i < _listJson.length; i++){
                            if(_listJson[i].id == id){
                                _listJson.splice(i, 1);
                            }
                        }
                        if(_listJson.length == 0){
                            $('#ykShow,.ykShow').text('0.00');
                        }*!/
                        var m = Math.round((Number(data.difMoney)-Number(data.fee))*100)/100;
                        layer.open({
                            content: '您'+(m <= 0 ? '亏了' : '赚了')
                            +m+'￥！<br/>'+(data.buyUpDown == '0' ? '买涨' : '买跌')
                            +',合约:'+data.contractName+',建仓：'+data.buyPoint+',平仓:'+data.sellPoint+',手续费：'+data.fee,
                            style: 'border:none; color:#FF4500; background-color: #01182f',
                            btn: '我知道了'
                        });
                    }
                });

            },
            end: function () {
                _tradePwd = null;
            }
        });
    //}

}*/


function sell(id){
    $.ajax({
        url: ctx + '/tradeMarket/sell',
        type: 'PUT',
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify({
            "id":id,
            "tradePassword":md5(_tradePwd)
        }),
        success: function(data){
            console.log(data);
            // 重置chi持仓记录
            $('#holdDiv_'+id).remove();
            /*for (var i = 0; i < _listJson.length; i++){
             if(_listJson[i].id == id){
             _listJson.splice(i, 1);
             }
             }
             if(_listJson.length == 0){
             $('#ykShow,.ykShow').text('0.00');
             }*/
            // var m = Math.round((Number(data.difMoney)-Number(data.fee))*100)/100;
            var m = Math.round(Number(data.difMoney)*100)/100;
            layer.open({
                content: '您'+(m <= 0 ? '亏了' : '赚了')
                +m+'￥！<br/>'+(data.buyUpDown == '0' ? '买涨' : '买跌')
                +',合约:'+data.contractName+',建仓：'+data.buyPoint+',平仓:'+data.sellPoint+',手续费：'+data.fee,
                style: 'border:none; color:#FF4500; background-color: #01182f',
                btn: '我知道了'
            });
        }
    });
}

function updateHold(id) {
    layer.open({
        title: '修改订单',
        content: '止盈:<input type="text" id="_tradePwd" name="profitMax" placeholder="止盈比例(%): 100 - 300"/>' +
                '止损:<input type="text" id="_tradePwd" name="lossMax" placeholder="止损比例(%): 0 - 70"/>',
        btn: ['确认', '取消'],
        yes: function (index) {
            $.ajax({
                url: ctx + '/tradeMarket/updateTrade',
                type: 'POST',
                contentType: 'application/json;charset=UTF-8',
                data: JSON.stringify({
                    id: id,
                    profitMax: $('input[name=profitMax]').val(),
                    lossMax: $('input[name=lossMax]').val()
                }),
                success: function (data) {
                    console.log(JSON.stringify(data));
                    layer.msg(data);

                }
            })
        }
    })
}