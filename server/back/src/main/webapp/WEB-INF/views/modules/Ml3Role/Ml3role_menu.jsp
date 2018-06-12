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
<body style="background: #ffffff">
<div class="panel panel-default">
    <div class="panel-body" style="padding:10px 10px 10px 20px;border: none">
        <form>
            <div class="form-group">
                <label>选择菜单权限</label>
                <ul id="menuTree"></ul>
            </div>
            <button type="button" class="btn btn-default" id="submitTree">提交</button>
        </form>
    </div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
    var selectMenuIds;
    $('#menuTree').tree({
        url: ctx + '/M3Role/menu_trees?roleId=${roleId}',
        method:'post',
        animate:true,
        checkbox:true,
        onLoadSuccess:function(data){
            $.each(selectMenuIds,function(i,obj){
                var nodeTarget =$('#menuTree').tree('find', obj).target;
                var isLeaf = $('#menuTree').tree('isLeaf', nodeTarget);
                if(isLeaf){
                    $('#menuTree').tree("check",nodeTarget);
                }
            })
        },
        loadFilter: function (data) {
            selectMenuIds = data.selectMenuIds;
            return data.menuTrees;
        }
    });
    $(function () {
        $("#submitTree").on("click", function () {
            //获取当前选择的菜单
            var checkedNodes = $('#menuTree').tree('getChecked');
            var reqJsonList = [];
            $.each(checkedNodes,function(i,obj){
                reqJsonList.push({
                    roleId:'${roleId}',
                    permissionId:obj.id
                });
            })
            var indeterminateNodes = $('#menuTree').tree('getChecked','indeterminate');
            $.each(indeterminateNodes,function(i,obj){
                reqJsonList.push({
                    roleId:'${roleId}',
                    permissionId:obj.id
                });
            })
            $.ajax({
                url:ctx+"/M3Role/saveRoleMenu?roleId=${roleId}",
                cache:false,
                method:"POST",
                dataType: "json",
                data: $.toJSON(reqJsonList) ,
                contentType : 'application/json',
                success: function(result){
                    if (result.code == 0) {
                        parent.window.$("iframe").parent().window("close")
                        parent.$("#listDataGrid").datagrid('reload');
                        parent.window.layer.msg("操作成功",{
                            offset:getLayerCenter()
                        });
                    } else {
                        layer.msg(result.msg,{
                            offset:getLayerCenter()
                        });
                    }
                }
            });
        });
    })
</script>
</body>
</html>
