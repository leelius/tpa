<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="proPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<br>
	<br>
	<!-- 有这个角色则会显示User Page链接-->
	<shiro:hasAnyRoles name="user">
		<a href="${proPath}/user.jsp"> User Page</a>
	</shiro:hasAnyRoles>
	<br>
	<br>
	<!-- 有这个角色则会显示Admin Page链接-->
	<shiro:hasAnyRoles name="admin">
		<a href="${proPath }/admin.jsp"> Admin Page</a>
	</shiro:hasAnyRoles>
	<!-- 有这个delete权限则会显示删除按钮-->
	<shiro:hasPermission name="delete">
		<input type="button" value="删除">
	</shiro:hasPermission>
	<br>
	<br>
	<a href="${proPath }/test/logout">Logout</a>
</body>
</html>