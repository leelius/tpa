<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="head.jsp" />
<title>${pagetitle}-${appName}</title>
<meta content="" name="description" />
</head>
<body>
	<jsp:include page="header.jsp" />
	<!-- content -->
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				当前登录：${sessionScope.tpauser.real_name}[${sessionScope.tpauser.alias}]
			</div>
			<div class="col-md-6 text-right">
				<a href="${basePath}logout" class="btn btn-warning">修改密码</a>&nbsp;&nbsp;<a
					href="${basePath}logout" class="btn btn-info">退出登录状态</a>
			</div>
			<div>show_experiment=${show_experiment.value},
				show_evaluation=${show_evaluation.value}</div>
		</div>
		<div class="row">
			<div class="col-md-6"></div>
			<div class="col-md-6 text-right"></div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
	<!-- /content -->
	<jsp:include page="foot.jsp" />
</body>
</html>