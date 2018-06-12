<%--
  Created by IntelliJ IDEA.
  User: zhangbowen
  Date: 2015/8/7
  Time: 8:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body style="background: #ffffff;border-top:3px solid #3c8dbc;">
<div class="panel panel-default" style="margin:5px;">
    <div class="panel-body" style="padding:10px 10px 10px 20px;border: none; overflow-x:hidden;">
        <form>
			<input type="hidden" name="appId" value="${appId}"/>
			<input type="hidden" name="id" value="${name}"/>
			<input type="hidden" name="type" value="${type}"/>
			<input type="hidden" name="parentName" value="${parentName}"/>
            <div class="form-group row">
            	<div class="col-md-6">
	                <label>菜单名称<span style="color:#dd4b39;" id="sp">(${parentName == null ? 8 : 20}字以内，不可重名)</span></label>
	                <input type="text" class="form-control span1" name="name" placeholder="${parentName == null ? 8 : 20}字以内，不可重名" maxlength="${parentName == null ? 8 : 20}"
						   value="${name}"
	                       required>
	            </div>
				<div class="col-md-6" id="d1" style="${type == 'click' || type == 'media_id' || type == 'view_limited' ? 'display:none;' : ''}">
					<label id="lab1">网页地址</label>
					<input type="text" class="form-control span1" name="url" placeholder="256字节以内" maxlength="256" value="${url}">
				</div>
				<div class="col-md-6" id="d2" style="${type == 'click' ? '' : 'display:none;'}">
					<label id="lab2">下发文本</label>
					<input type="text" class="form-control span1" name="content" placeholder="128字节以内" maxlength="128" value="${content}">
				</div>
				<div class="col-md-6" id="d3" style="${type == 'media_id' || type == 'view_limited' ? '' : 'display:none;'}">
					<label id="lab3">资源类型</label>
					<!-- 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news） -->
					<select id="media_id_query" class="form-control span1" onchange="_media_id_query()">
						<option value="">请选择类型</option>
						<option value="image">图片</option>
						<option value="video">视频</option>
						<option value="voice">语音</option>
						<option value="news">图文</option>
					</select>
				</div>
				<div class="col-md-6" id="d4" style="${type == 'media_id' || type == 'view_limited' ? '' : 'display:none;'}">
					<label id="lab4">选择</label>
					<select id="media_id_select" class="form-control span1" onchange="select_media_id()">
						<option value="">请选择</option>
					</select>
					<input type="hidden" id="media_id_val" class="form-control span1" name="mediaId" placeholder="合法media_id" maxlength="128" value="${mediaId}">
				</div>
            </div>
			<div style="width: 100%;" id="d5" style="${type == 'media_id' || type == 'view_limited' ? '' : 'display:none;'}">

			</div>
            <button type="submit" class="btn btn-primary" onclick="infoAjaxSubmit(this,'${ctx}/weiXinMenu/update')" style="float: right;">提交</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
	function changeShow(the) {
		var val = $(the).val();
		$('#d1,#d2,#d3').hide();
		if(val == 'click'){
			$('#d2').show();
		}else if(val == 'media_id' || val == 'view_limited'){
			$('#d3,#d4,#d5').show();
		}else{
			$('#d1').show();
		}
	}

	function select_media_id() {
		$('#media_id_val').val($('#media_id_select').val());
	}

	function _media_id_query() {
		$('#media_id_select,#d5').empty();
		var type = $('#media_id_query').val();
		if(type == null || type == ''){
			return;
		}

		$.ajax({
			url: ctx + '/weiXinMenu/queryMedia?type='+type+'&appId=${appId}',
			type: 'get',
			success: function(data){
				console.log(data);
				var aa = [];
				aa.push('<option value="">请选择</option>');
				var it = null;
				var name = null;
				for (var i = 0; i < data.length; i++){
					name = null;
					it = data[i];
					if(typeof it.name != 'undefined'){
						aa.push('<option value="'+it.media_id+'">'+it.name+'</option>');
						$('#d5').append('<a style="line-height: 30px;" target="_blank" href="'+it.url+'">'+it.name+'</a><br/>');
					}else{
						for(var j = 0; j < it.content.news_item.length; j++){
							if(name == null){
								name = it.content.news_item[j].title;
							}else{
								name += ','+it.content.news_item[j].title;
							}
							$('#d5').append('<a style="line-height: 30px;" target="_blank" href="'+it.content.news_item[j].url+'">'+it.content.news_item[j].title+'</a><br/>');
						}
						aa.push('<option value="'+it.media_id+'">'+name+'</option>');
					}
				}
				$('#media_id_select').html(aa.join(''));
			}
		});
	}
</script>
</body>
</html>
