/**
 * Created by Administrator on 2017/1/12.
 */
/**
 * @type {Array}
 * "重庆市、福建省、甘肃省、广西自治区、贵州省、海南省、河北省6、河南省7、黑龙江省8、" +
 "湖北省9、湖南省10、江苏省11、江西省12、吉林省13、辽宁省14、内蒙古自治区15、宁夏自治区16、" +
 "青海省17、山东省18、山西省19、陕西省20、四川省21、天津市22、新疆自治区23、西藏自治区24、" +
 "云南省25、浙江省26";
 */
var city = new Array();
city[0] = new Array('渝中区','江北区','沙坪坝区','南岸区','九龙坡区','大渡口区');
city[1] = new Array('福州市','厦门市','泉州市','漳州市','龙岩市','南平市','宁德市','莆田市','三明市');
city[2] = new Array('兰州市','金昌市','白银市','天水市','嘉峪关市','定西市','平凉市','庆阳市','陇南市','武威市','张掖市','酒泉市','甘南市','临夏市');
city[3] = new Array('南宁市','柳州市','桂林市','梧州市','北海市','防城港市','钦州市','贵港市','玉林市','贺州市','百色市','河池市');
city[4] = new Array('贵阳市','六盘水市','遵义市','铜仁市','毕节市','安顺市','黔西南市','黔东南市','黔南市');
city[5] = new Array('海口市','三亚市','通什市','琼海市','琼山市','文昌市','万宁市','东方市','儋州市');
city[6] = new Array('石家庄市','唐山市','秦皇岛市','邯郸市','邢台市','保定市','张家口市','承德市','沧州市','廊坊市','衡水市');
city[7] = new Array('郑州市','开封市','洛阳市','平顶山市','安阳市','鹤壁市','新乡市','焦作市','濮阳市','许昌市','漯河市','三门峡市','南阳市','商丘市','周口市','驻马店市','信阳市','济源市');
city[8] = new Array('哈尔滨市','齐齐哈尔市','牡丹江市','佳木斯市','大庆市','伊春市','黑河市','鸡西市','鹤岗市','双鸭山市','七台河市','绥化市','大兴安岭市');
city[9] = new Array('武汉市','黄石市','十堰市','荆州市','宜昌市','襄樊市','鄂州市','荆门市','孝感市','黄冈市','咸宁市','恩施市','随州市','仙桃市','天门市','潜江市','神农架市');
city[10] = new Array('长沙市','株州市','湘潭市','衡阳市','邵阳市','岳阳市','常德市','郴州市','益阳市','永州市','怀化市','娄底市','湘西市');
city[11] = new Array('南京市','苏州市','无锡市','常州市','镇江市','连云港市','扬州市','徐州市','南通市','盐城市','淮阴市','泰州市','宿迁市');
city[12] = new Array('南昌市','景德镇市','九江市','萍乡市','新余市','鹰潭市','赣州市','宜春市','吉安市','上饶市','抚州市');
city[13] = new Array('长春市','吉林市','四平市','辽源市','通化市','白山市','松原市','白城市','延边市');
city[14] = new Array('沈阳市','大连市','鞍山市','锦州市','丹东市','盘锦市','铁岭市','抚顺市','营口市','辽阳市','阜新市','本溪市','朝阳市','葫芦岛市');
city[15] = new Array('呼和浩特市','包头市','乌海市','临河市','东胜市','集宁市','锡林浩特市','通辽市','赤峰市','海拉尔市','乌兰浩特市');
city[16] = new Array('银川市','石嘴山市','银南市','固原市');
city[17] = new Array('西宁市','海东市',' 海北市','黄南市','海南市','果洛市','玉树市','海西市');
city[18] = new Array('济南市','青岛市','淄博市','德州市','烟台市','潍坊市','济宁市','泰安市','临沂市','菏泽市','威海市','枣庄市','日照市','莱芜市','聊城市','滨州市','东营市');
city[19] = new Array('太原市','大同市','阳泉市','朔州市','长治市','临汾市','晋城市');
city[20] = new Array('西安市','铜川市','宝鸡市','咸阳市','渭南市','延安市','汉中市','榆林市','商洛市','安康市');
city[21] = new Array('成都市','自贡市','攀枝花市','泸州市','德阳市','绵阳市','广元市','遂宁市','内江市','乐山市','南充市','宜宾市','广安市','达川市','巴中市','雅安市','眉山市','阿坝市','甘孜市','凉山市');
city[22] = new Array('和平市','河北区','河西','河东','南开','红桥','塘沽','汉沽','大港','东丽','西青','津南','北辰','武清','滨海');
city[23] = new Array('乌鲁木齐市','克拉玛依市','石河子市','吐鲁番市','哈密市','和田市','阿克苏市','喀什市','克孜勒苏市','巴音郭楞市','昌吉市','博尔塔拉市','伊犁市');
city[24] = new Array('拉萨市','那曲市','昌都市','山南市','日喀则市','阿里市','林芝市');
city[25] = new Array('昆明市','东川市','曲靖市','玉溪市','昭通市','思茅市','临沧市','保山市','丽江市','文山市','红河市','西双版纳市','楚雄市','大理市','德宏市','怒江市','迪庆市');
city[26] = new Array('杭州市','湖州市','丽水市','温州市','绍兴市','舟山市','嘉兴市','金华市','台州市','衢州市','宁波市');

$(function(){
    /*银行*/
    $("#bank").after('<div class="nice-select" style="z-index: 9999;"><ul id="selectBank" style="display: none;overflow: auto;position: absolute;top: 1rem;left: 2.55rem;z-index: 9999999;background: rgb(0, 0, 0);height: 11rem;padding-top: 5px; font-size: 0.35rem;"></ul></div>');
    var bank = null;
    for(var item in banks){
        $("#selectBank").append('<li class="bank_i" style="height: 30px;line-height: 30px;overflow: hidden;padding: 0px 10px;cursor: pointer;">'+ item +'</li>');
    }

    /*显示银行*/
    $("#bank").on("click",function () {
        console.log(JSON.stringify(banks));
        $(document).unbind('click', hideShowSelectBank);
        $('#selectBank').toggle(function () {
            $(document).unbind('click', hideShowSelectBank);
        }, function () {
            setTimeout(function () {
                $(document).one('click', hideShowSelectBank);
            }, 50);
        });
    });
    /*选择银行*/
    $(".bank_i").on("click",function () {
        $("#bank").val($(this).text());
    });

    /*显示省份*/
    $("#province").on("click",function(){
        $(document).unbind('click', hideShowSelectProvince);
        $('#selectProvince').toggle(function () {
            $(document).unbind('click', hideShowSelectProvince);
        }, function () {
            setTimeout(function () {
                $(document).one('click', hideShowSelectProvince);
            }, 50);
        });
    });
    /*选择省份*/
    $(".province_i").on("click",function () {
        $("#province").val($(this).text());
        var name = $(this).attr('name');
        $("#city").val('');
        /*级联城市*/
        $(".cityss").remove();
        var citys = city[name];
        for (var  i = 0; i < citys.length; i++){
            $("#selectCity").append('<li class="cityss" style="height: 30px;line-height: 30px;overflow: hidden;padding: 0px 10px;cursor: pointer;" onclick="cityClick(this)">'+ citys[i] +'</li>');
        }
    });
    /*显示城市*/
    $("#city").on('click',function(){
        $(document).unbind('click', hideShowSelectCity);
        $('#selectCity').toggle(function () {
            $(document).unbind('click', hideShowSelectCity);
        }, function () {
            setTimeout(function () {
                $(document).one('click', hideShowSelectCity);
            }, 50);
        });
    });
});
/*隐藏省份*/
function hideShowSelectProvince(){
    $("#selectProvince").hide();
}
/*隐藏银行*/
function hideShowSelectBank(){
    $("#selectBank").hide();
}
/*隐藏城市*/
function hideShowSelectCity(){
    $("#selectCity").hide();
}

function  cityClick(obj) {
    var val = obj.innerHTML;
    $("#city").val(val);
}




