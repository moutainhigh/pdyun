<%--
  Created by IntelliJ IDEA.
  User: zhangbowen
  Date: 2015/7/11
  Time: 18:33
  To change this template use File | Settings | File Templates.
  顶部导航栏
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%--手机版左侧menu--%>
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">菜单</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="javascript:void(0)">
			<img src="${ctxStatic}/login/img/01.png" style="float:left; margin-left:24px; margin-top:-4px" />
		</a>
    </div>
    <%--手机版显示左侧menu结束--%>
    <%--导航栏菜单开始--%>
    <ul class="nav navbar-top-links navbar-right">
        <li class="dropdown">
            <a href="${ctx}/logout">${fns:getUser().name}<i class="fa fa-sign-out fa-fw"></i></a>
            <%--<a class="dropdown-toggle" data-toggle="dropdown" href="#">--%>
                <%--<i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>--%>
            <%--</a>--%>

            <%--<ul class="dropdown-menu dropdown-user">--%>
                <%--&lt;%&ndash;<li><a href="#"><i class="fa fa-user fa-fw"></i>用户信息</a>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</li>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<li><a href="#"><i class="fa fa-gear fa-fw"></i>设置</a>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</li>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<li class="divider"></li>&ndash;%&gt;--%>
                <%--<li><a href="${ctx}/logout"><i class="fa fa-sign-out fa-fw"></i>注销</a>--%>
                <%--</li>--%>
            <%--</ul>--%>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>
    <%--导航栏菜单结束--%>
