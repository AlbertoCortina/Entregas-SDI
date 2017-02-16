<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	
	<title>TaskManager - Listado de tareas</title>
	</head>
	<body>
		<h1>Listado de tareas</h1>
		<table>
			<tr>
				<th>Titulo</th>
				<th>Creada</th>
			</tr>
			<c:forEach items= "${requestScope.tasks}" var="task">
				<tr>
					<td>${pageScope.task.title}</td>
					<td>
						<fmt:formatDate value="${pageScope.task.created}" type="date"/>
					</td>
				</tr>
			</c:forEach>
		</table>
			
		
	</body>
</html>