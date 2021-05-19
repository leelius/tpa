<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="canusewechat" value="false"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<link href="${basePath}bssets/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}bssets/css/bootstrap-patch.css">
<link rel="icon" sizes="192x192" href="${basePath}bssets/i/favicon.png">
<title>${pagetitle}-${appName}</title>
<style type="text/css">
a:hover {
	text-decoration: none;
}
</style>
</head>
<body>
	<!--headerimg start-->
	<div class="container-fluid">
		<div class="row margin-top-lg">
			<div class="col-md-1 col-md-offset-4">
				<img src="${basePath}bssets/tpa/sicnu_logo.png"
					class="img-responsive center-block" style="width: 100px;" />
			</div>
			<div class="col-md-2 ">
				<img src="${basePath}bssets/tpa/sicnu_name.png"
					class="img-responsive center-block"
					style="width: 200px; margin-top: 10px;" />
			</div>
		</div>
	</div>
	<!--headerimg end-->
	<!--container start-->
	<div class="container">
		<h2 class="text-center">${appName} — ${title}</h2>
		<div>
			<!-- Nav tabs -->
<%--			<ul class="nav nav-tabs" role="tablist">--%>
<%--				<li role="presentation"--%>
<%--					<c:if test="${canusewechat}">class="active"</c:if>><a--%>
<%--					href="#wechat" aria-controls="wechat" role="tab" data-toggle="tab">微信登录</a></li>--%>
<%--				<li role="presentation"--%>
<%--					<c:if test="${canusewechat==false}">class="active"</c:if>><a--%>
<%--					href="#login" aria-controls="login" role="tab" data-toggle="tab">密码登录</a></li>--%>
<%--			</ul>--%>
			<!-- Tab panes -->
			<div class="tab-content">
				<div role="tabpanel"
					<c:choose>
							<c:when test="${canusewechat==false}">
								class="tab-pane" 
							</c:when>
							<c:when test="${canusewechat}">
								class="tab-pane active" 
							</c:when>
						</c:choose>
					id="wechat">
					<div style="min-height: 300px;">
						<c:choose>
							<c:when test="${canusewechat==false}">
								<h3 class="text-center text-danger">微信功能已经关闭！</h3>
							</c:when>
							<c:when test="${canusewechat}">
								<br>
								<img src="${basePath}wcc/loginqrcode?rnd=${rnd}" id="qrcode"
									class="center-block" alt="微信扫描二维码登录" title="微信扫描二维码登录" />
								<h3 class="text-center text-danger" id="info"></h3>
								<h3 class="text-center text-success" id="infosuccess"></h3>
							</c:when>
						</c:choose>
					</div>
				</div>
				<div role="tabpanel"
					<c:choose>
							<c:when test="${canusewechat==false}">
								class="tab-pane active" 
							</c:when>
							<c:when test="${canusewechat}">
								class="tab-pane" 
							</c:when>
						</c:choose>
					id="login">
					<form class="form-horizontal " method="post" action="${basePath}${action}" style="min-height: 300px;">
						<input type="hidden" name="operate" value="login"> <br>
						<div class="form-group">
							<label for="username" class="col-sm-2 control-label">登录号</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="username"
									name="username" placeholder="请输入用户名" value="">
								<%-- 18781110337 --%>
								<%-- 15708429965 --%>
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-sm-2 control-label">密码</label>
							<div class="col-sm-8">
								<input type="password" class="form-control" id="password"
									autocomplete="off" name="password" placeholder="请输入密码" value="">
								<%--  --%>
							</div>
						</div>
						<div class="form-group">
							<label for="vcode" class="col-sm-2 control-label">验证码</label>
							<div class="col-sm-8">
								<div class="input-group">
									<span title="图片看不清时点击更新验证码" class="input-group-addon padding-0"
										style="cursor: pointer;">
										<img id="codeImage" style="height: 32px;" alt="验证码"
										src="${basePath}/getverificationcodeimage">
									</span>
									<input
										type="text" class="form-control" id=vcodeinput
										name="vcodeinput" placeholder="请输入验证码" autocomplete="off"
										maxlength="4">
								</div>
<%--								${message}--%>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-8">
								<button type="submit" class="btn btn-primary col-xs-12">登录</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!--container end-->
	<div class="text-center">
		<p>2017-2021.${appName} 版权所有 All rights reserved.</p>
	</div>
	<%-- 
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 --%>
	<script src="${basePath}bssets/js/jquery.min.1.12.4.js"></script>
	<script src="${basePath}bssets/js/bootstrap.min.js"></script>
	<%--刷新验证码	-julius --%>
	<script type="text/javascript" language="javascript">
		$(document).ready(
				function() {
					$("#codeImage").click(function() {
								$(this).attr(
										'src',
										'${basePath}getverificationcodeimage?arg='
												+ Math.random());
							});
				});
	</script>
	<c:if test="${canusewechat}">
		<script type="text/javascript" language="javascript">
			function displayinfo(info) {
				$("#info").html($("#info").html() + info + "<br>");
			}
			function dinfo(info) {
				$("#info").html(info + "<br>");
			}
			function dinfosuccess(info) {
				$("#infosuccess").html(info + "<br>");
			}
			var server;
			$(document)
					.ready(
							function() {
								try {
									var url = "wss://"
											+ window.location.host
											+ "<c:url value='/wwsm/${jsessionid}/${sid}'></c:url>";
									//displayinfo("window.location.host=" + window.location.host);
									//displayinfo("url=" + url);
									server = new WebSocket(url);
								} catch (error) {
									displayinfo("error=" + error);
									return;
								}
								server.onopen = function(event) {
									displayinfo("使用微信扫描登录（有效期：<fmt:formatNumber value='${periodOfValidity/1000}' type='number' pattern='0'/>秒，超时请刷新）");
								}
								window.onbeforeunload = function() {
									displayinfo("");
									server.close();
								}
								server.onclose = function(event) {
									$("#info").show();
									//dinfo("链接超时，请刷新页面重新获取二维码");
									$("#infosuccess").hide();
									$("#qrcode").hide();
								}
								server.onerror = function(event) {
									displayinfo("链接错误");
								}
								server.onmessage = function(event) {
									//displayinfo(event.data);
									//$("#id").html(event.id);
									//$("#name").html(event.name);
									//$("#author").html(event.author);
									if (event.data == "可以确认") {
										isscan = true;
										$("#info").hide();
										dinfosuccess("请在移动端点击确认按钮");
										$("#qrcode").hide();
									} else if (event.data == "gotoLoginUseWechatInfo") {
										dinfosuccess("页面即将跳转，请稍候！");
										$(window).attr('location',
												'/loginopenid');
									} else {

									}
								}
							});
		</script>
	</c:if>
</body>
</html>