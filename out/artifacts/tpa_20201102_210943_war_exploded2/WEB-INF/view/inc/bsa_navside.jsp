<%@page language="java" pageEncoding="utf-8"%>
<nav class="navbar-default navbar-side" role="navigation">
	<div class="sidebar-collapse">
		<ul class="nav" id="main-menu">
			<li><a <c:if test="${navitem=='Home'}">class="active-menu"</c:if>
				   href="#"><i class="fa fa-home"></i>首页
				</a>
			</li>

			<!-- 用户管理开始 -->
			<li><a <c:if test="${navitem=='User'}"> class="active-menu"</c:if> href="">
					<i class="fa fa-user"></i> 用户及权限(super) <span class="fa arrow"></span>
				</a>
				<ul
					<c:if test="${navitem!='User'}">class="nav nav-second-level"</c:if>
					<c:if test="${navitem=='User'}">class="nav nav-second-level collapse in"</c:if>>
<%--					<li--%>
<%--						<c:if test="${pagename == 'superadmin/permit'}">class="active"</c:if>><a--%>
<%--						href="superadmin/permit">许可管理</a></li>--%>
<%--					<li--%>
<%--						<c:if test="${pagename == 'superadmin/role'}">class="active"</c:if>><a--%>
<%--						href="superadmin/role">角色管理</a></li>--%>
					<li
						<c:if test="${pagename == 'superadmin/user'}">class="active"</c:if>><a
						href="superadmin/user">用户管理</a></li>
				</ul>
			</li>
			<!-- 用户管理结束 -->

			<li>
				<a <c:if test="${navitem=='BaseInfo'}">class="active-menu"</c:if>
					href="#"><i class="fa fa-qrcode"></i> 基础信息(super)<span class="fa arrow"></span>
				</a>
				<ul
					<c:if test="${navitem!='BaseInfo'}">class="nav nav-second-level"</c:if>
					<c:if test="${navitem=='BaseInfo'}">class="nav nav-second-level collapse in"</c:if>>
					<li
						<c:if test="${pagename == 'superadmin/subject'}">class="active"</c:if>><a
						href="superadmin/subject">科目管理</a></li>
					<li
						<c:if test="${pagename == 'superadmin/studentgroup'}">class="active"</c:if>><a
						href="superadmin/studentgroup">班级管理</a></li>
					<li
						<c:if test="${pagename == 'superadmin/student'}">class="active"</c:if>><a
						href="superadmin/student">学生管理</a></li>
					<li
						<c:if test="${pagename == 'superadmin/curriculum'}">class="active"</c:if>><a
						href="superadmin/curriculum">课程管理</a></li>
<%--					<li--%>
<%--						<c:if test="${pagename == 'superadmin/experimentmanagement'}">class="active"</c:if>><a--%>
<%--						href="superadmin/experimentmanagement">实验管理</a></li>--%>
<%--					<li--%>
<%--						<c:if test="${pagename == 'superadmin/statexperiment'}">class="active"</c:if>><a--%>
<%--						href="superadmin/statexperiment">实验统计</a></li>--%>
				</ul>
			</li>

			<li><a
				<c:if test="${navitem=='Experiment'}">class="active-menu"</c:if>
				href="#"><i class="fa fa-qrcode"></i> 实验信息<span class="fa arrow"></span></a>
				<ul
					<c:if test="${navitem!='Experiment'}">class="nav nav-second-level"</c:if>
					<c:if test="${navitem=='Experiment'}">class="nav nav-second-level collapse in"</c:if>>
					<li
						<c:if test="${pagename == 'superadmin/experimentarrange'}">class="active"</c:if>><a
						href="superadmin/experimentarrange">实验安排</a></li>
					<li
						<c:if test="${pagename == 'superadmin/statexperiment'}">class="active"</c:if>><a
						href="superadmin/statexperiment">实验统计</a></li>
					<li
						<c:if test="${pagename == 'superadmin/experimentrecord'}">class="active"</c:if>><a
						href="superadmin/experimentrecord">评阅实验报告</a></li>
				</ul></li>
<%--
			<li><a
				<c:if test="${navitem=='Evaluation'}">class="active-menu"</c:if>
				href="#"><i class="fa fa-qrcode"></i> 考核信息<span class="fa arrow"></span></a>
				<ul
					<c:if test="${navitem!='Evaluation'}">class="nav nav-second-level"</c:if>
					<c:if test="${navitem=='Evaluation'}">class="nav nav-second-level collapse in"</c:if>>

					<li
						<c:if test="${pagename == 'superadmin/evaluation'}">class="active"</c:if>><a
						href="superadmin/evaluation">考核管理</a></li>
					<li
						<c:if test="${pagename == 'superadmin/statevaluationpoint'}">class="active"</c:if>><a
						href="superadmin/statevaluationpoint">题库统计</a></li>
					<li
						<c:if test="${pagename == 'superadmin/evaluationpoint'}">class="active"</c:if>><a
						href="superadmin/evaluationpoint">题库管理</a></li>
				</ul>
			</li>
--%>
			<!-- 网站设置开始  -->
			<li><a
				<c:if test="${navitem=='Website'}">class="active-menu"</c:if>
				href="#"><i class="fa fa-qrcode"></i> 网站设置(super)<span
					class="fa arrow"></span></a>
				<ul
					<c:if test="${navitem!='Website'}">class="nav nav-second-level"</c:if>
					<c:if test="${navitem=='Website'}">class="nav nav-second-level collapse in"</c:if>>
					<li
						<c:if test="${pagename == 'superadmin/syslog'}">class="active"</c:if>><a
						href="superadmin/syslog">系统日志</a></li>
				</ul></li>
			<!-- 网站设置结束  -->

			<!--个人信息管理开始 -->
			<li><a
				<c:if test="${navitem=='Personal'}">class="active-menu"</c:if>
				href="#"><i class="fa fa-qrcode"></i> 个人信息管理<span
					class="fa arrow"></span></a>
				<ul
					<c:if test="${navitem!='Personal'}">class="nav nav-second-level"</c:if>
					<c:if test="${navitem=='Personal'}">class="nav nav-second-level collapse in"</c:if>>
					<li
						<c:if test="${pagename == 'superadmin/userinfo'}">class="active"</c:if>><a
						href="superadmin/userinfo">个人信息</a></li>
					<li
						<c:if test="${pagename == 'superadmin/userpassword'}">class="active"</c:if>><a
						href="superadmin/userpassword">修改密码</a></li>
				</ul></li>
			<!--个人信息管理结束 -->
		</ul>
	</div>
</nav>