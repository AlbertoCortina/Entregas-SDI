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
		<hr>
		<br>
		
		<form action="a�adirTarea" method="post">
			<input type="button" value="A�adir tarea">
		</form>
		<br>
		<br>
		<table border="1" align="center">
			<tr>
				<th>Titulo</th>
				<th>Comentarios</th>
				<th>Fecha creaci�n</th>
				<th></th>
			</tr>
			<c:forEach items= "${requestScope.tasks}" var="task" varStatus="i">
				<tr id="item_${i.index}">
					<td>${task.title}</td>
					<td>${task.comments}</td>
					<td>
						<fmt:formatDate value="${task.created}" type="date"/>
					</td>
					<td>
						<a href="editarTarea">Editar tarea</a>
					</td>
					<td>
						<a href="finalizarTarea">Finalizar</a>
					</td>
				</tr>
			</c:forEach>
		</table>		
	</body>
</html>