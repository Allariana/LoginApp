<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Form</title>
</head>
<body>
<h3>Login</h3>
<div style="border:1px solid #D0D0D0;width:400px;padding:10px;">
	<form method = "POST" action="Validation">
	User name: <input type="text" name="username" value="">
	<br>
	Password:  <input type="password" name="password" value="">
	<br>
	<input type="submit" value="Submit">
	<br>
	</form>
</div>

</body>
</html>