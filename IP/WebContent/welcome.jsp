<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%> 
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Home</title>
</head>

<style>
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}
table.center {
  margin-left: auto;
  margin-right: auto;
}
th, td {
  padding: 5px;
  text-align: center;
}
th {
  background-color: #e3edfc;
}
td {
  cellpadding="5";
  background-color: #f0f4fa;
}
</style>
<body>

<%
String id_a = "nie umiem znalezc ID";
Object id=request.getAttribute("id");
if (id != null)
	id_a = id.toString();
 %>

 <%
 String dane = "";
 Object dane1 = request.getAttribute("username");
 if (dane1 != null)
 	dane = dane1.toString();
%>
	<sql:setDataSource var="db1" driver="org.h2.Driver"
		url="jdbc:h2:tcp://localhost/~/test10" user="sa" password="" />
		
		<sql:query var="query1" dataSource="${db1}">
		SELECT * FROM TIME t JOIN USER u ON u.id=t.user_id where u.username = ?
		<sql:param value="${username}"/>
	</sql:query>
		
    <div style="border:1px">
        <h1 style="color:blue; text-align: center;"><b>Welcome</b> </h1>
        <h1 style="color:blue; text-align: center;">dear ${username}</h1>
        <br>
        <font color="black" size="3">
        
        <table style="width:50%" class="center">
        <caption style="text-align:center">Table with your data</caption>
			<tr>
				<th>ID</th>
				<th>Username</th>
				<th>Actual Password</th>
				<th>Login time</th>
				<th>Login timed out</th>
			</tr>
			<tr>
			<c:forEach var="row" items="${query1.rows}">
					<td><c:out value="${row.user_id}" /></td>
					<td>${username}</td>
					<td>${password}</td>
					<td><c:out value="${row.logintime}" /></td>
					<td><c:out value="${row.logintimedout}" /></td>
				</tr>
				</c:forEach>
		</table>
		<br><br>
        <td><a href="password.jsp?username=${username}">Change password</a></td>
        <div style="text-align: right"><a href="LoginTimedOut?username=${username}">Logout</a></div>
    </div>
</body>
</html>