<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="../inc/bsa_head.jsp"></jsp:include>
<!-- Custom Styles-->
<link href="bsat1/assets/css/custom-styles.css" rel="stylesheet" />
<title>${pagetitle}-${appName}</title>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="../inc/bsa_navtop.jsp"></jsp:include>
		<!--/. NAV TOP  -->
		<jsp:include page="../inc/bsa_navside.jsp"></jsp:include>
		<!-- /. NAV SIDE  -->
		<div id="page-wrapper">
			<div class="header">
				<h1 class="page-header">
					${pagetitle} <small>${subtitle}</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="admin/">首页</a></li>
					<li class="active">${pagetitle}</li>
				</ol>
			</div>
			<div id="page-inner">
				<!-- content start -->
				<div class="row">
					<div class="col-sm-12 col-md-12 col-lg-12">
						<c:if test="${sessionScope.username == null}">
							<form class="form-inline" method="post">
								<div class="form-group">
									<label for="username">Player:</label> <input type="text"
										class="form-control" id="username" name="username"
										placeholder="username">
								</div>
								<button type="submit" class="btn btn-success">提交</button>
							</form>
						</c:if>
						<c:if test="${sessionScope.username != null}">
							<div class="form-inline">
								<div class="form-group">
									<label for="username">Player:</label> <span
										class="form-control">${username}</span>
								</div>
							</div>
							<br />
							<button id="clearmsg">清除信息</button>
							<br />
							<input type="text" id="msg">
							<button id="sendmsg">发送信息</button>
							<br />
						</c:if>
						<div id="info"></div>
					</div>
				</div>
				<!-- content end -->
				<jsp:include page="../inc/bsa_foot.jsp"></jsp:include>
			</div>
			<!-- /. PAGE INNER  -->
		</div>
	</div>
	<script type="text/javascript" language="javascript">
		$(document)
				.ready(
						function() {
							
							
							function displayinfo(info) {
								$("#info").html(
										$("#info").html() + info + "<br>");
							}
							var server;
							try {
								var url="wss://"
									+ window.location.host
									+ "<c:url value='/demowebsocket/${jsessionid}'><c:param name='action' value='talk'/><c:param name='token' value='1234567890'/></c:url>";
								displayinfo("window.location.host=" + window.location.host);
								displayinfo("url=" + url);
								server = new WebSocket(url);
							} catch (error) {
								return;
							}
							server.onopen = function(event) {
								displayinfo("长连接已经建立");
							};

							window.onbeforeunload = function() {
								displayinfo("onbeforeunload");
								server.close();
							};

							server.onclose = function(event) {
								displayinfo("onclose");
							};

							server.onerror = function(event) {
								displayinfo("onerror");

							};
							server.onmessage = function(event) {
								displayinfo(event.data);
								//$("#id").html(event.id);
								//$("#name").html(event.name);
								//$("#author").html(event.author);
							};
							$("#sendmsg").click(function() {
								var msg = $('#msg').val();
								server.send(JSON.stringify({
									name : "${username}",
									msg : msg
								}));
							});
							$("#clearmsg").click(function() {
								$("#info").html("");
							});
							$(".game-cell").click(function() {
								var today = new Date();
								var h = today.getHours();
								var m = today.getMinutes();
								var s = today.getSeconds();
								server.send(this.id);
								//displayinfo(h + ":" + m + ":" + s + ",click=" + this.id);
							});
						});
	</script>

</body>
</html>