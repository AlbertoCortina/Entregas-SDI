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
		<h1>Listado de Usuarios</h1>
		
		<!-- <form action="filtrar" method="post">
			<fieldset>
				<legend>Ordenar por:</legend>
				<label>
					<input type="radio" name="ordenado">Ordenar por login				
				</label>
				
				<label>
					<input type="radio" name="ordenado">Ordenar por email				
				</label>
				
				<label>
					<input type="radio" name="ordenado">Ordenar por status				
				</label>
			</fieldset>
		</form>	 -->	
		
		<table border="1">
			<tr>
				<th>Id</th>			
				<th>Login</th>
				<th>Email</th>
				<th>IsAdmin</th>
				<th>Status</th>
				<th>Habilitar/Deshabilitar</th>				
			</tr>
			<c:forEach items= "${listaUsuarios}" var="user">
				<tr>					
					<td><c:out value="${user.id}"/></td>				
					<td>${user.login}</td>
					<td>${user.email}</td>
					<td>${user.isAdmin}</td>					
					<td><c:out value="${user.status}"/></td>	
					<td>	
						<!--  <form action="habilitar/deshabilitar">
							<input type="button" value="Habilitar/Deshabilitar"/>
						</form>	-->
						<a id="habilitar/Deshabilitar_link_id" href="cambiarEstado?id=${user.id}">Habilitar/Deshabilitar</a>				
					</td>				
				</tr>
			</c:forEach>
		</table>		
	</body>
</html>