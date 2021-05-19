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
<link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}bssets/css/bootstrap-patch.css">
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
		<h4>此页面不会自动跳转</h4>
		<h4>请查看提示信息后，点击&nbsp;&nbsp;<a  class="btn btn-success" href="${jumpto}">跳转</a>继续访问</h4>
		<h2>${message}</h2>
	</div>
</body>
<script	src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<script	src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
</html>
