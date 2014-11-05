<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Login Page</title>
</head>
<body>
<h2>
	<c:if test="${not empty error }">
		${error }
	</c:if>
	<c:if test="${not empty msg }">
		${msg }
	</c:if>
</h2>
<form name="loginForm" method="post" action="<c:url value='/login'/>">
	<table>
		<tr>
			<td>User Name:</td>
			<td>
				<input type="text" name="username">
			</td>
		</tr>
		<tr>
			<td>Password:</td>
			<td>
				<input type="password" name="password">
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="Sign In">
			</td>
		</tr>
	</table>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
</body>
</html>