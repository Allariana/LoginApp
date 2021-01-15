<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%> 
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
<h3>Login</h3>
<div style="border:1px solid #D0D0D0;width:400px;padding:10px;">
	<form name="loginForm" method = "POST" action="Validation">
	User name* : User name: <input type="text" name="username" value="" placeholder="username" required>
	<br>
	Password* : <input type="password" name="password" value="" placeholder="password" required>
	<br><br>
	<p style="color: black;"><i>* Required field</i></p> 
	<input type="submit" value="Submit">
	<br>
	</form>
</div>

</body>
</html>