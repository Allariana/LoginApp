<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Dane</title>
</head>
<body>
	<sql:setDataSource var="db1" driver="org.h2.Driver"
		url="jdbc:h2:tcp://localhost/~/test10" user="sa" password="" />
	<c:set var="select" value="SELECT * FROM USER;" />
	<c:out value="${select}" />
	<br>
	<sql:query var="query1" dataSource="${db1}" sql="${select}" />

	<table border="1">
		<tr>
			<th>ID</th>
			<th>Surname</th>
		</tr>
		<c:forEach var="row" items="${query1.rows}">
			<tr>
				<td><c:out value="${row.id}" /></td>
				<td><c:out value="${row.surname}" /></td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>