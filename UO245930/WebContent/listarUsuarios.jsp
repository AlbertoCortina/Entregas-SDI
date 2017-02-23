<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>	
		<title>TaskManager - Listado de usuarios</title>
	</head>
	<body>
		<center>
			<h1>Lista de usuarios registrados</h1>
		</center>
		<hr>
		<br>
		<table id="lista_usuarios_table" border="1" align="center">
			<tr>
				<th>Id</th>			
				<th>Login</th>
				<th>Email</th>
				<th>IsAdmin</th>
				<th>Status</th>						
			</tr>
			<c:forEach items="${listaUsuarios}" var="user" varStatus="i">
				<tr id="item_${i.index}">					
					<td>"${user.id}"</td>				
					<td>${user.login}</td>
					<td>${user.email}</td>
					<td>${user.isAdmin}</td>					
					<td><a id="habilitar/Deshabilitar_link_id_${user.login}" href="cambiarEstado?id=${user.id}&status=${user.status}">${user.status}</a></td>
				</tr>
			</c:forEach>
		</table>		
	</body>
</html>