<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Inicie sesión</title>
<body>
	<form  id="form_validarse" action="validarse" method="post" name="validarse_form_name">

		<center>
			<h1>Inicie sesión</h1>
		</center>
		<hr>
		<br>
		<table align="center">
			<tr>
				<td align="right">Usuario</td>
				<td><input type="text" name="nombreUsuario" align="left" size="15"></td>				
			</tr>
			
			<tr>
				<td align="right">Contraseña</td>
				<td><input type="password" name="password" align="left" size="15"></td>
			</tr>
			
			<tr>
				<td><input type="submit" value="Enviar" /></td>
			</tr>
		</table>
	</form>
	<a id="registrarUsuario_link_id" href="registrarse">Registrarse</a>
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>