<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Form</title>
</head>
<body>
<h3>${param.username}, change your password</h3>
<div style="border:1px solid #D0D0D0;width:400px;padding:10px;">
<form name="loginForm" method = "POST" action="ChangePassword">	
	New Password:  <input type="password" name="new_password" value="" placeholder="new password" required>
	<br>
	Repeat Password:  <input type="password" name="repeat_password" value="" placeholder="repeat password"" required>
	<br>
	<input type="submit" value="Save">
	<br>
	<input type="hidden" name="username" value="${param.username}">
	</form>
</div>

</body>
</html>