<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>微信登录</title>
<link rel="stylesheet" href="../bssets/weui/weui.min.css" />
<link rel="stylesheet" href="../bssets/weui/example.css" />
</head>
<body ontouchstart>
	<div class="container js_container">
		<div class="page">
			<div class="weui_msg">
				<div class="weui_icon_area">
					<img src="../bssets/i/logo.png">
				</div>
				<div class="weui_icon_area">
					<h2 class="weui_msg_title">${nickname}</h2>
					<img src="${headimgurl}">
				</div>
				<div class="weui_opr_area">
					<p class="weui_btn_area">
					<div class="weui_msg_desc" id="info">微信没有绑定到，新建账号</div>
					<button id="sendmsg_create" class="weui_btn weui_btn_primary">使用微信登录网站，创建新账号</button>
					<div class="weui_msg_desc" id="info">微信没有绑定到，绑定已有账号</div>
					<button id="sendmsg_binding" class="weui_btn weui_btn_warn">先登录，再绑定已有账号</button>
					</p>
				</div>
				<div class="weui_extra_area">如果有疑问：请拨打电话：${telephone}</div>
			</div>
		</div>
	</div>
	<script src="../bssets/weui/zepto.min.js"></script>
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<script type="text/javascript" language="javascript">
		function displayinfo(info) {
			$("#info").html($("#info").html() + info + "<br>");
		}
		function dinfo(info) {
			$("#info").html(info + "<br>");
		}
		$(document).ready(function() {
			$("#sendmsg_create").click(function() {
				//通知pc端，时间暂停
				$("#sendmsg_create").attr('disabled', false);
				$(window).attr('location', './cufw');
			});
		});
	</script>
</body>
</html>