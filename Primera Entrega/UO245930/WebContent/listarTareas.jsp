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
		<center>
			<h1>Listado de tareas</h1>
		</center>	
		<hr>
		<br>		
		<form action="añadirTarea" method="post">
			<input type="button" value="Añadir tarea">
		</form>
		<br>
		<br>
		
		<jsp:useBean id="today" class="java.util.Date" scope="page" />		
		
		<table border="1" align="center">
			<tr>
				<th>Titulo</th>
				<th>Comentarios</th>
				<th>Fecha creación</th>
				<th>Fecha planeada</th>
				<th>Fecha finalizada</th>
				<th></th>
				<th></th>
			</tr>
			
			<c:forEach items= "${requestScope.tasks}" var="task" varStatus="i">
				<tr id="item_${i.index}">
					<td>${task.title}</td>
					<td>${task.comments}</td>
					<td>${task.created}</td>									
					<td>
						<c:choose>
							<c:when test="${task.planned != null && task.planned < today && task.finished == null}">
								<p style="color:red;">${task.planned}</p>
							</c:when>
							<c:otherwise>								
								${task.planned}
							</c:otherwise>
						</c:choose>
					</td>
					<td>${task.finished}</td>	
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