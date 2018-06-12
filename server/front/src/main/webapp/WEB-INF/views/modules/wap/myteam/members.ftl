<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="format-detection" content="email=no">
	<#include "/wap/header.ftl">
    <title>${title}</title>
	<link rel="shortcut icon" href="${ctxStatic}/favicon.ico" type="image/x-icon" />
    <link href="${ctxStatic}/all/css/footer.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/cd.css" />
	<link rel="stylesheet" href="${ctxStatic}/css/global.css">
	<link rel="stylesheet" href="${ctxStatic}/css/base.css">
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
	.menus ul li:nth-child(4) a{
		color: #FF8400;
	}
	.legion:before{
		background: url("${ctxStatic}/img/images2/legion/dph.png") no-repeat;
	}
  </style>
  <link rel="stylesheet" href="${ctxStatic}/css/global.css">
</head>
<body style="max-width: 768px;margin: 0 auto;overflow:scroll;overflow-y:hidden;">
<!--顶部开始-->
<#--<div class="top_div" style="max-width: 768px;" id="myteam_top">-->
        <#--<a style="display: inline-block;float: left;height: 5rem;line-height: 5rem;color: #fff;margin-left: 1rem;font-size: 18px;" href="javascript:window.history.back();">返回</a>-->
        <#--<h1 style="margin-right:5.6rem">我的军团</h1>-->
<#--</div>-->
<!--顶部结束-->
<!-- mian开始 -->
<div class="min" style="width: 100%;">
	    <div class="team_login" style="padding-bottom:0.5rem">
	       <div class="garding_top"></div>
			<#list users.data as it>
                <div class="grading">
                    <p class="grading_img"><img src="${it.userHeader!''}" alt=""></p>
                    <div class="grading_name">
                        <h3>${it.chnName!''}</h3>
                        <span><#if it.corpsType == '2'>骑</#if><#if it.corpsType == '3'>步</#if>兵团</span>
                    </div>
                    <div class="grading_time">
                        <h3>加入时间</h3>
                        <span><#if it.registerTime??>${it.registerTime?date}</#if></span>
                    </div>
                </div>
			</#list>
	       <h6 class="grading_data" id="click_more">
	       		<#if users.start + users.data.size() == users.total>没有更多数据<#else><span onclick="loadMore();">点击加载更多</span></#if>
	       </h6>
    	</div>
</div><!--597 x 880-->
<#include "/wap/footer.ftl">
<script type="application/javascript">
	var _start = 4, _total = ${users.total}, _rows = 4, _loading = false;

	function loadMore() {
		if(_loading){
			return ;
		}
        _loading = true;
		var _html = $('#click_more').html();
        $('#click_more').html('努力加载中');
		$.ajax({
			url: ctx + '/corps/members',
			data: {
				start: _start,
				rows: _rows
			},
			success: function(pb){
                _loading = false;

				var data = pb.data;
				var _arr = [];
				for (var i = 0; i < data.length; i++){
					it = data[i];
					_arr.push('<div class="grading">');
					_arr.push('<p class="grading_img"><img src="'+it.userHeader+'" alt=""></p>');
					_arr.push('<div class="grading_name">');
					_arr.push('<h3>'+it.chnName+'</h3>');
					_arr.push('<span>'+(it.corpsType == '1' ? '炮' : (it.corpsType == '2' ? '骑' : '步'))+'兵团</span>');
					_arr.push('</div>');
					_arr.push('<div class="grading_time">');
					_arr.push('<h3>加入时间</h3>');
					_arr.push('<span>'+it.registerTime.substring(0, 10)+'</span>');
					_arr.push('</div>');
					_arr.push('</div>');
				}
				$('#click_more').before(_arr.join(''));
                _start += pb.data.length;
				_total = pb.total;
				if(_start == _total){
                    $('#click_more').html('没有更多数据');
				}else {
                    $('#click_more').html('<span onclick="loadMore();">点击加载更多</span>');
				}
			},
			error: function () {
				_loading = false;
                $('#click_more').html(_html);
            }
		});
    }
</script>
</body>
</html>