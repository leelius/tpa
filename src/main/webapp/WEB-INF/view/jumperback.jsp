<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<base href="${basePath}">
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
<meta name="content-type" content="text/html; charset=UTF-8" />
<style>
html {
	margin: 0;
}

body {
	margin: 0;
}

#layout {
	border: 0px solid black;
	text-align: center;
	margin: 60px auto;
}
</style>
</head>
<body>
	<div id="layout">
		此页面不会自动跳转，查看后退到上一页面，重新输入！
		<h3>${message}</h3>
		<div>
		<%--	window.history.back(-1)，后退到上一页		--%>
		<button style="width:100px;height:40px;" onclick="window.history.back(-1);">跳转</button>
		</div>
	</div>
</body>
</html>
