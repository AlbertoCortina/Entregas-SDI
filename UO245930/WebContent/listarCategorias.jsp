<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title>TaskManager - Listado de categorías</title>
</head>
<body>
	<center>
		<h1>Lista de categorías</h1>
	</center>
	<hr>
	<br>
	<form action="añadirCategoria" method="post" >
		<table align="center">
			<tr>
				<td align="right">Nombre de la categoría: </td>
				<td><input type="text" name="nombreCategoria" align="left" size="15"></td>
				<td><input type="submit" value="Añadir categoria"></td>
			</tr>			
		</table>		
	</form>	
	<br>
	<br>
	<table id="categorias_table" border="1" align="center">
		<tr>
			<th>ID</th>
			<th>Nombre</th>
		</tr>
		<c:forEach var="entry" items="${listaCategorias}" varStatus="i">
			<tr id="item_${i.index}">
				<td><a href="mostrarCategoria?id=${entry.id}&name=${entry.name}">${entry.id}</a></td>
				<td>${entry.name}</td>
				<td>
					<a href="editarCategoria">Editar categoria</a>
				</td>
				<td>
					<a href="eliminarCategoria">Eliminar</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>