<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>微信登录-焱飞科技</title>
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
					<h2 class="weui_msg_title">提示信息</h2>
					<div class="weui_msg_desc" id="info">${message}</div>
				</div>
				<div class="weui_opr_area">
					<a href="../" class="weui_btn weui_btn_primary">访问焱飞科技网站</a>
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
					+ "<c:url value='/wwsm/${jsessionid}/${sid}'></c:url>";
				//displayinfo("window.location.host=" + window.location.host);
				//displayinfo("url=" + url);
				server = new WebSocket(url);
			} catch (error) {
				return;
			}
			server.onopen = function(event) {
				displayinfo("点击确认，使用微信账号登录焱飞科技");
				displayinfo("<fmt:formatNumber value='${periodOfValidity/1000}' type='number' pattern='0'/>秒内点击，超时请重新扫描");
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
				//displayinfo(event.data);
				if(event.data=='链接已经关闭'){ 
					server.close();
					setTimeout("dinfo('二维码已经失效，请刷新页面后重新扫描');",${periodOfValidity});
				}
				//$("#id").html(event.id);
				//$("#name").html(event.name);
				//$("#author").html(event.author);
			} 
			$("#sendmsg").click(function() {
				server.send("ok,${openid}");
				//当前页面转到首页
				//TODO:  登录后移动端的页面
				$(window).attr('location','loginatmobile');
			});
			$("#cancelmsg").click(function() {
				$("#info").html("请直接关闭页面");
				server.close();
			});							
		});
	</script>
</body>
</html>