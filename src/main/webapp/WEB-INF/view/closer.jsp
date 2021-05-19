<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<meta charset="utf-8" />
<title></title>
<meta name="content-type" content="text/html; charset=UTF-8" />
<script language="javascript" type="text/javascript">
	setTimeout(closewindow, 1000);
	function closewindow(){
		top.location.reload();
	}
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
		<h2>${message}</h2>
	</div>
</body>
</html>
