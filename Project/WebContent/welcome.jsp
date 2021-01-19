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

<script>
function ShowHideTab() {
	  var x = document.getElementById('tab');
	  if (x.style.visibility === 'hidden') {
	    x.style.visibility = 'visible';
	  } else {
	    x.style.visibility = 'hidden';
	  }
	}


function ShowHideTab2() {
	  var x = document.getElementById('tab2');
	  if (x.style.visibility === 'hidden') {
	    x.style.visibility = 'visible';
	  } else {
	    x.style.visibility = 'hidden';
	  }
	}
</script>


	<sql:setDataSource var="db1" driver="org.h2.Driver"
		url="jdbc:h2:tcp://localhost/~/test10" user="sa" password="" />
		
		<sql:query var="query1" dataSource="${db1}">
		SELECT * FROM LOGINTIME t JOIN USER u ON u.id=t.user_id where u.username = ?
		<sql:param value="${username}"/>
	</sql:query>
		
    <div style="border:1px">
        <h1 style="color:blue; text-align: center;"><b>Welcome</b> </h1>
        <h1 style="color:blue; text-align: center;">dear ${username}</h1>
        <br>
        <font color="black" size="3">
        
        <input type="button" value="Show/Hide table with your login data" onclick="ShowHideTab();">
        <br>
        <table id="tab" class="center">
        <caption style="text-align:center">Table with your login data</caption>
			<tr>
				<th>Login time</th>
				<th>Login timeout</th>
			</tr>
			<tr>
			<c:forEach var="row" items="${query1.rows}">
					<td><c:out value="${row.logintime}" /></td>
					<td><c:out value="${row.logintimeout}" /></td>
				</tr>
				</c:forEach>
		</table>
		<br><br>
		<sql:query var="query2" dataSource="${db1}">
		SELECT * FROM PASSWORD p JOIN USER u ON u.id=p.user_id where status = 'ACTUAL' and u.username = ?
		<sql:param value="${username}"/>
	</sql:query>
		
		<input type="button" value="Show/hide table with your password data" onclick="ShowHideTab2();">
		<br>
        <table id="tab2" class="center">
        <caption style="text-align:center">Table with your password data</caption>
			<tr>
				<th>Password</th>
				<th>Date of last password change</th>
				<th>Date of the possibility of changing the password</th>
				<th>Password Expiration Date</th>
			</tr>
			<tr>
			<c:forEach var="row" items="${query2.rows}">
					<td>Actual password</td>
					<td><c:out value="${row.date}" /></td>
					<td><c:out value="${row.expire_min}" /></td>
					<td><c:out value="${row.expire_max}" /></td>
				</tr>
				</c:forEach>
		</table>
		<br><br>
        <td><a href="password.jsp?username=${username}">Change password</a></td>
        <div style="text-align: right"><a href="LoginTimedOut?username=${username}">Logout</a></div>
    </div>
</body>
</html>