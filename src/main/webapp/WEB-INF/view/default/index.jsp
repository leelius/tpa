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
		<h1>你好，世界！</h1>


		<jsp:include page="footer.jsp" />
	</div>
	<!-- /content -->
	<jsp:include page="foot.jsp" />
</body>
</html>