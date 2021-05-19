<%@page language="java" pageEncoding="utf-8"%>
<nav class="navbar-default navbar-side" role="navigation">
	<div class="sidebar-collapse">
		<ul class="nav" id="main-menu">
			<li><a
				<c:if test="${navitem=='Home'}">class="active-menu"</c:if>
				href="./teacher"><i class="fa fa-home"></i>首页</a></li>

			<li><a
				<c:if test="${navitem=='Experiment'}">class="active-menu"</c:if>
				href="#"><i class="fa fa-qrcode"></i> 实验信息<span class="fa arrow"></span></a>
				<ul
					<c:if test="${navitem!='Experiment'}">class="nav nav-second-level"</c:if>
					<c:if test="${navitem=='Experiment'}">class="nav nav-second-level collapse in"</c:if>>
					<li
						<c:if test="${pagename == 'teacher/experimentarrange'}">class="active"</c:if>><a
						href="teacher/experimentarrange">实验安排</a></li>
					<li
						<c:if test="${pagename == 'teacher/statexperimentteacher'}">class="active"</c:if>><a
						href="teacher/statexperimentteacher">实验统计</a></li>
					<li
						<c:if test="${pagename == 'teacher/experimentrecord'}">class="active"</c:if>><a
						href="teacher/experimentrecord">评阅实验报告</a></li>
				</ul></li>
<%--
			<li><a
				<c:if test="${navitem=='Evaluation'}">class="active-menu"</c:if>
				href="#"><i class="fa fa-qrcode"></i> 考核信息<span class="fa arrow"></span></a>
				<ul
					<c:if test="${navitem!='Evaluation'}">class="nav nav-second-level"</c:if>
					<c:if test="${navitem=='Evaluation'}">class="nav nav-second-level collapse in"</c:if>>

					<li
						<c:if test="${pagename == 'teacher/evaluation'}">class="active"</c:if>><a
						href="teacher/evaluation">考核管理</a></li>
					<li
						<c:if test="${pagename == 'teacher/statevaluationpoint'}">class="active"</c:if>><a
						href="teacher/statevaluationpoint">题库统计</a></li>
					<li
						<c:if test="${pagename == 'teacher/evaluationpoint'}">class="active"</c:if>><a
						href="teacher/evaluationpoint">题库管理</a></li>
				</ul></li>
--%>


			<!--个人信息管理开始 -->
			<li><a
				<c:if test="${navitem=='Personal'}">class="active-menu"</c:if>
				href="#"><i class="fa fa-qrcode"></i> 个人信息管理<span
					class="fa arrow"></span></a>
				<ul
					<c:if test="${navitem!='Personal'}">class="nav nav-second-level"</c:if>
					<c:if test="${navitem=='Personal'}">class="nav nav-second-level collapse in"</c:if>>
					<li
						<c:if test="${pagename == 'teacher/userinfo'}">class="active"</c:if>><a
						href="teacher/userinfo">个人信息</a></li>
					<li
						<c:if test="${pagename == 'teacher/userpassword'}">class="active"</c:if>><a
						href="teacher/userpassword">修改密码</a></li>
				</ul></li>
			<!--个人信息管理结束 -->

		</ul>
	</div>
</nav>