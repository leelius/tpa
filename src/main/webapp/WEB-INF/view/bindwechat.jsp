<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>微信绑定-焱飞科技</title>
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
					<img src="bindqrcode?rnd=${rnd}" id="qrcode" class="center-block"
						alt="微信扫描二维码登录" title="微信扫描二维码登录" /><br>
					<h2 class="weui_msg_title">${nickname}</h2>
					<div class="weui_msg_title" id="info"></div>
				</div>
				<div class="weui_opr_area">
					<p class="weui_btn_area">						
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
			$("#info").html(
					$("#info").html() + info + "<br>");
		}
		function dinfo(info) {
			$("#info").html(info + "<br>");
		}
		var server;
		function closesocket() {
			server.close();			
		}
		$(document).ready(function() {				
			try {
				var url="wss://"
					+ window.location.host
					+ "<c:url value='/wbm/${jsessionid}/${sid}'></c:url>";
				server = new WebSocket(url);
			} catch (error) {
				return;
			}
			server.onopen = function(event) {
				displayinfo("即将绑定微信号到焱飞科技<br>请使用微信扫描二维码");
				displayinfo("<fmt:formatNumber value='${periodOfValidity/1000}' type='number' pattern='0'/>秒内扫描，超时请重新申请");
				setTimeout("closesocket()",${periodOfValidity}); 
			};

			window.onbeforeunload = function() {
				displayinfo("");
				server.close();
			};

			server.onclose = function(event) {
				dinfo("请重新扫描二维码");
				$("#sendmsg").hide();
				$("#cancelmsg").hide();
			}
			server.onerror = function(event) {
				displayinfo("服务器未响应链接错误");
			}
			server.onmessage = function(event) {
				if(event.data=='bindwechatyesdoit'){
					$("#qrcode").hide();
					$("#info").addClass("page_title");
					$(window).attr('location','../wcc/bindsuccesspc');
				}
			}
			$("#sendmsg").click(function() {
				window.close();
			});									
		});
	</script>
</body>
</html>