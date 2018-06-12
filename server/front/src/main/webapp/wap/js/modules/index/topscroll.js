/**
 * Created by Administrator on 2018/1/5.
 */
$(function(){
    var $this = $(".news_li");
    var scrollTimer;
    $this.hover(function(){
        clearInterval(scrollTimer);
    },function(){
        scrollTimer = setInterval(function(){
            if(recordStatus == true){
                queryWinRecord();
                broadcast();
            }
            scrollNews( $this );
        }, 2000 );
    }).trigger("mouseout");
});
function scrollNews(obj){
    var $self = obj.find("ul:first");
    var lineHeight = $self.find("li:first").height();
    $self.animate({ "margin-top" : -lineHeight +"px" },600 , function(){
        $self.css({"margin-top":"0px"}).find("li:first").appendTo($self);
    })
}

function queryWinRecord() {
    recordStatus = false;
    $.ajax({
        url: ctx + '/index/winProfit',
        type: 'GET',
        success: function (data) {
            winProfit(data);
        }
    })
}

var recordStatus = true;

function winProfit(data){
    var record = data.winProfitList;
    for (var i = 0; i < record.length; i++){
        var it = record[i];
        var chnName = it.chnName;
        var msg = chnName.substring(0, 2) + "**平仓" + it.contractName + "斩获了" + Number(it.winProfit).toFixed(0) + "%的收益";
        $(".win_scroll").append("<li style='line-height: .6rem;'>"+ msg +"</li>");
    }
}

var start = 0, rows = 5;
var imageObj;
function broadcast() {
    $.ajax({
        url: ctx + '/index/broadcast',
        type: 'GET',
        data: {
            start: start,
            rows: rows
        },
        success: function (data) {
            var total = data.broadcastList.total;

            //$(".informationTxt").children().remove();
            var arry = [];
            var list = data.broadcastList.data;
            for (var i = 0; i < list.length; i++){
                var broadcast = list[i];
                var content = broadcast.content;
                /*<li class="affiche_txt mation">
                 <img class="img left" src="${ctxStatic}/all/image/timg (1).jpg">
                 <div class="left message">
                 <h2>标题</h2>
                 <p>标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题</p>
                 </div>
                 </li>*/

                /*截取图片用于显示*/
                var str = content.match(/<img[^>]+>/g);

                arry.push('<li class="affiche_txt mation" onclick="findBroadcast(&quot;'+ broadcast.id +'&quot;)">');
                if(null == str){
                    arry.push('<img class="img left" src="'+ ctxStatic +'/all/image/timg01.jpg">');
                }else{
                    arry.push('<img class="img left" src="'+ $(str[0]).attr("src") +'">');
                }
                arry.push('<div class="left message">');
                arry.push('<h2>'+ broadcast.title +'</h2>');
                arry.push('<p>'+ content.replace(/<\/?(img|a|ul|li)[^>]*>/gi, '').substring(0, 7) +'</p>');
                arry.push('</div>');
                arry.push('</li>');
            }
            $('.informationTxt').append(arry.join(' '));
            start += list.length;
            if(total == start){
                $('#click_more').html('<span>没有更多数据</span>');
            }else{
                $('#click_more').html('<span onclick="broadcast()">点击加载更多</span>');
            }
        }
    });
}

function findBroadcast(id){
    window.location.href = ctxWap + '/index/broadcastDetail/' + id;
}