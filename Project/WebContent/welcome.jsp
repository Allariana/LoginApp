<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
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

td {cellpadding ="5";
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
		function ShowHideTab3() {
			var x = document.getElementById('tab3');
			if (x.style.visibility === 'hidden') {
				x.style.visibility = 'visible';
			} else {
				x.style.visibility = 'hidden';
			}
		}
		function ShowHideTab4() {
			var x = document.getElementById('tab4');
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
		SELECT 'BLOCK' AS TYPE_OF_EVENT, b.DATE_START AS DATE_START, b.DATE_END AS DATE_END FROM BLOCK b JOIN USER u ON u.id=b.user_id where u.username = ?
		UNION
		SELECT 'LOGIN' AS TYPE_OF_EVENT, t.LOGINTIME AS DATE_START, t.LOGINTIMEOUT AS DATE_END FROM LOGINTIME t JOIN USER u ON u.id=t.user_id where u.username = ?
		<sql:param value="${username}" />
		<sql:param value="${username}" />
	</sql:query>

	<div style="border: 1px">
		<h1 style="color: blue; text-align: center;">
			<b>Welcome</b>
		</h1>
		<h1 style="color: blue; text-align: center;">dear ${username}</h1>
		<br> <font color="black" size="3"> <input type="button"
			value="Show/Hide table with your events" onclick="ShowHideTab();">
			<br>
			<table id="tab" class="center">
				<caption style="text-align: center">Table with your events</caption>
				<tr>
					<th>Type of event</th>
					<th>Date start</th>
					<th>Date end</th>
				</tr>
				<tr>
					<c:forEach var="row" items="${query1.rows}">
						<td><c:out value="${row.TYPE_OF_EVENT}" /></td>
						<td><c:out value="${row.DATE_START}" /></td>
						<td><c:out value="${row.DATE_END}" /></td>
				</tr>
				</c:forEach>
			</table> <br> <br> 
			<sql:query var="query2" dataSource="${db1}">
		SELECT * FROM PASSWORD p JOIN USER u ON u.id=p.user_id where date_end is null and u.username = ?
		<sql:param value="${username}" />
			</sql:query> <input type="button" value="Show/hide table with your password data"
			onclick="ShowHideTab2();"> <br>
			<table id="tab3" class="center">
				<caption style="text-align: center">Table with your
					password data</caption>
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
			</table> <br> <br>

			<sql:query var="query4" dataSource="${db1}">
		SELECT * FROM PASSWORD p JOIN USER u ON u.id=p.user_id where date_end is not null and u.username = ?
		<sql:param value="${username}" />
			</sql:query> <input type="button" value="Show/hide table with history of passwords"
			onclick="ShowHideTab4();"> <br>
			<table id="tab3" class="center">
				<caption style="text-align: center">Table with your
					history of passwords</caption>
				<tr>
					<th>Password</th>
					<th>Date_start</th>
					<th>Date_change</th>
				</tr>
				<tr>
					<c:forEach var="row" items="${query4.rows}">
						<td><c:out value="${row.password}" /></td>
						<td><c:out value="${row.DATE}" /></td>
						<td><c:out value="${row.DATE_END}" /></td>
				</tr>
				</c:forEach>
			</table> <br> <br>

			<tr>
			<td><a href="password.jsp?username=${username}">Change
					password</a></td>
			<td><div style="text-align: right">
				<a href="LoginTimedOut?username=${username}">Logout</a>
			</div></td></tr>
	</div>
</body>
</html>