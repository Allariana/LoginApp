<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%> 
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
Date lastAccessTime = new Date(session.getLastAccessedTime());
%>

<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Home</title>
</head>
<body>
	
	<sql:setDataSource var="db1" driver="org.h2.Driver"
		url="jdbc:h2:tcp://localhost/~/test10" user="sa" password="" />

			
		<sql:query var="query1" dataSource="${db1}">
		SELECT * FROM time where user_id=1
	</sql:query>
		
    <div style="border:1px">
        <h1>Welcome</h1>
        <b>Dear ${username}</b>
        <br><br>
        <table border="1">
		<tr>
			<th>Username</th>
			<th>Actual Password</th>
			<th>Old Password</th>
			<th>Login time</th>
			<th>Login timed out</th>
		</tr>
		<tr>
		<c:forEach var="row" items="${query1.rows}">
				<td>${username}</td>
				<td>${password}</td>
				<td>old password....</td>
				<td><c:out value="${row.logintime}" /></td>
				<td><c:out value="${row.logintimedout}" /></td>
			</tr>
			</c:forEach>
	</table>
		<br><br>
        <td><a href="password.jsp?username=${username}">Change password</a></td>
        <br><br>
        <a href="form.jsp">Logout</a>
    </div>

</body>
</html>