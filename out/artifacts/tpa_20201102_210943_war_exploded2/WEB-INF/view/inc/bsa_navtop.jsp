<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<nav class="navbar navbar-default top-navbar" role="navigation">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			id="sideNavBtn" data-target=".sidebar-collapse">
			<span class="sr-only">导航切换</span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</button>
		<a class="navbar-brand padding-0 margin-0" href="./"><img src="bssets/tpa/admin_logo1.png"></a>
		<div id="sideNav" href="#">
			<i class="fa fa-bars icon"></i>
		</div>
	</div>
	<ul class="nav navbar-top-links navbar-right">
		<li class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
				<i class="fa fa-user fa-fw"></i>
				<span class="text-primary">${user.real_name}</span>
				<i class="fa fa-caret-down"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="/superadmin/userinfo"><i class="fa fa-user fa-fw"></i>个人信息</a></li>
				<li><a href="superadmin/userpassword"><i class="fa fa-gear fa-fw"></i> 修改密码</a></li>
<%--				<c:if test="${user.wechatOpenid==''}">--%>
<%--					<li><a id="bindingwechat" href="#" ><i class="fa fa-wechat"></i> 绑定微信</a></li>--%>
<%--				</c:if>--%>
<%--				<c:if test="${user.wechatOpenid!=''}">--%>
<%--					<li><a id="unbindingwechat" href=""><i class="fa fa-wechat"></i> 解除微信绑定</a></li>--%>
<%--				</c:if>--%>
				<li class="divider"></li>
				<li><a href="logout"><i class="fa fa-sign-out fa-fw"></i>退出</a></li>
			</ul> <!-- /.dropdown-user -->
		</li>
		<!-- /.dropdown -->
	</ul>
</nav>


