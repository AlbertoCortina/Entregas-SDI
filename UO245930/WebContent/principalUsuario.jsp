<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title>TaskManager - Página principal del usuario</title>
</head>
<body>	
	<center>
		<h1>Entrada principal usuario</h1>
	</center>
	<hr>
	<br>
	<i>Iniciaste sesión el <fmt:formatDate
			pattern="dd-MM-yyyy' a las 'HH:mm"
			value="${sessionScope.fechaInicioSesion}" /> (usuario número
		${contador})
	</i>
	<br />
	
	<h1>Información del usuario</h1>
	<jsp:useBean id="user" class="uo.sdi.model.User" scope="session" />
	<table>
		<tr>
			<td>Id:</td>
			<td id="id"><jsp:getProperty property="id" name="user" /></td>
		</tr>
		<tr>
			<td>Email:</td>
			<td id="email">
				<form action="modificarDatos" method="POST">
					<input type="text" name="email" size="15"
						value="<jsp:getProperty property="email" name="user"/>"> <input
						type="submit" value="Modificar">
				</form>
			</td>
		</tr>
		<tr>
			<td>Es administrador:</td>
			<td id="isAdmin"><jsp:getProperty property="isAdmin" name="user" /></td>
		</tr>
		<tr>
			<td>Login:</td>
			<td id="login"><jsp:getProperty property="login" name="user" /></td>
		</tr>
		<tr>
			<td>Estado:</td>
			<td id="status"><jsp:getProperty property="status" name="user" /></td>
		</tr>
	</table>
	<br />
	
	<h1>Listados</h1>
		<ul>
			<li>
				<form action="listarTareasInbox">
					<table>
						<tr>						
							<td>Listado de tareas Inbox</td>							
							<td><label><input name="checkBox" type="checkBox" value="mostrarFinalizadas" >Mostrar finalizadas</label></td>
							<td><input type="submit" value="Ver tareas"></td>
						</tr>
					</table>					
				</form>			
			</li>
					
			<li><a href="listarTareasHoy">Listado de tareas Hoy</a></li>
			
			<li><a href="listarCategorias?id=<jsp:getProperty property="id" name="user"/>">Listado de categorias</a></li>	
		</ul>		
	
	<h1>Otras opciones</h1>
	<a id="cerrarSesion_link_id" href="cerrarSesion">Cerrar sesión</a>

	<%@ include file="pieDePagina.jsp"%>
</body>
</html>