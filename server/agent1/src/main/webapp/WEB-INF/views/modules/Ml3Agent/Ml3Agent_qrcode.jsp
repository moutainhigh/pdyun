<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/28
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <style type="text/css">
        .btn_addPic
        {
            display: block;
            position: relative;
            width: 120px;
            height: 28px;
            overflow: hidden;
            border: 1px solid #EBEBEB;
            background: none repeat scroll 0 0 #F3F3F3;
            color: #999999;
            cursor: pointer;
            text-align: center;
            float: left;
            margin-right:5px;
        }
        .btn_addPic span
        {
            cursor: pointer;
            display: block;
            line-height: 28px;
        }
        .filePrew
        {
            cursor: pointer;
            position: absolute;
            top: 0;
            left:0;
            width: 120px;
            height: 30px;
            font-size: 100px; /* 增大不同浏览器的可点击区域 */
            opacity: 0; /* 实现的关键点 */
            filter: alpha(opacity=0); /* 兼容IE */
        }
    </style>
</head>
<body>
<div style="height:100%;overflow-y: scroll;">
    <form id="fomSub"  action="${ctx}/Ml3Agent/saveqrcodes" enctype="multipart/form-data" method="post">
        <div style="margin:60px;">
            <div class="btn_addPic">
                <span style="color:#000;font-family: '微软雅黑'">上传图片…</span>
                <input tabindex="3" title="支持jpg格式，文件小于5M" size="3"
                                         name="file" class="filePrew" type="file" onchange="getImgURL(this)" id="file">
            </div>
            <input type="text" id="textName" style="height: 28px;border:1px solid #f1f1f1" />
            <input type="submit" name="submit" id="sub" value="提交">
        </div>
        <div id="show" style="width: 100%;margin-left:160px;">
            <label style="margin-left: 20px;display: none" id="newqrcode">新公众号二维码:</label><br/>
            <div class="mark1"></div>
        </div><br/><br/><br/>
        <div>
            <label style="margin-left: 20px;" id="oldqrcode">公众号二维码:</label><br/>
            <div  style="margin-left: 100px;">
                <img src="${ctx}/Ml3Agent/qrcode1" />
                <%--<img src="${ctxStatic}/login/img/01.png" style="float:left; margin-left:24px; margin-top:-4px" />--%>
            </div>
        </div>
    </form>
</div>
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script type="text/javascript">
    var imgurl = "";
    function getFileName(o){
        var pos=o.lastIndexOf("\\");
        return o.substring(pos+1);
    }
    function getImgURL(node) {
        var file = $("#file").val();
        var fileName = getFileName(file);
        document.getElementById("textName").value = fileName;

        var imgURL = "";
        try{
            var file = null;
            if(node.files && node.files[0] ){
                file = node.files[0];
            }else if(node.files && node.files.item(0)) {
                file = node.files.item(0);
            }
            //Firefox 因安全性问题已无法直接通过input[file].value 获取完整的文件路径
            try{
                //Firefox7.0
                imgURL =  file.getAsDataURL();
                //alert("//Firefox7.0"+imgRUL);
            }catch(e){
                //Firefox8.0以上
                imgRUL = window.URL.createObjectURL(file);
                //alert("//Firefox8.0以上"+imgRUL);
            }
        }catch(e){      //这里不知道怎么处理了，如果是遨游的话会报这个异常
            //支持html5的浏览器,比如高版本的firefox、chrome、ie10
            if (node.files && node.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    imgURL = e.target.result;
                };
                reader.readAsDataURL(node.files[0]);
            }
        }
        //imgurl = imgURL;
//        document.getElementById("newqrcode").attr("display","block");
        var do1 = document.getElementById("newqrcode");
        do1.style.display = "block";
        document.getElementById("oldqrcode").innerHTML="原公众号二维码";
        creatImg(imgRUL);
        return imgURL;
    }

    function creatImg(imgRUL){   //根据指定URL创建一个Img对象
        var textHtml = "<img src='"+imgRUL+"' style='margin: 0 auto'/>";
        $(".mark1").after(textHtml);
    }





    $(document).ready(function(){
        $("#sub").on("click",function () {
            $("#fomSub").submit();
        });

        $("#idFile").change(function () {
            var fil = this.files;
            for(var i = 0 ; i<fil.length;i++){
                reads(fil[i]);
            }
        })
    });
    function reads(fil){
        var reader = new FileReader();
        reader.readAsDataURL(fil);
        reader.onload = function()
        {
            document.getElementById("show").innerHTML += "<img src='"+reader.result+"'>";
        };
    }
</script>
</body>

</html>
