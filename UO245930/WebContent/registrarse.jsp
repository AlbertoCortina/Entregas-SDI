<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title>TaskManager - Registro</title>
</head>
<body>
	<form action="realizarRegistro" method="post">
		<center>
			<h1>Registrarse</h1>
		</center>
		<hr>
		
		<table align="center">
			<tr>
				<td align="right">Login: </td>
				<td><input type="text" name="login" align="left" size="15" required></td>
			</tr>
				
			<tr>
				<td align="right">Email: </td>
				<td><input type="text" name="email" align="left" size="15" required></td>
			</tr>
				
			<tr>
				<td align="right">Password: </td>
				<td><input type="password" name="password" align="left" size="15" required></td>
			</tr>
				
			<tr>
				<td align="right">Repetir password: </td>
				<td><input type="password" name="passwordRepetida" align="left" size="15"required></td>
			</tr>
					
			<tr>
				<td><input type="submit" value="Registrarse"/></td>
			</tr>
		</table>
	</form>
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>