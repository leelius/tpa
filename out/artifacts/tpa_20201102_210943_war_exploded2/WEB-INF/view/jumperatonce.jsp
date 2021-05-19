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
<script type="text/javascript">
	window.location.href = "${jumpto}";
</script>
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
		<div id="info">
			1秒后自动跳转，你也可以点击<a href="${jumpto}">跳转</a>
		</div>
		<h2>${message}</h2>
	</div>
</body>
</html>
