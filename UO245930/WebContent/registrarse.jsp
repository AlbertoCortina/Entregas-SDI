<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Registrese</title>
<body>
	<form action="realizarRegistro" method="post">

		<center>
			<h1>Registrarse</h1>
		</center>
		<hr>
		<br>
		<table align="center">
			<tr>
				<!--  <label for="login">Login</label>
    		<input type="text" id="login" name="login" align="left" size="15"></td>
    		-->
				<td align="right">Login</td>
				<td><input type="text" name="login" align="left" size="15"
					required="required"></td>
				<td align="right">Email</td>
				<td><input type="text" name="email" align="left" size="15"></td>
				<td align="right">Password</td>
				<td><input type="text" name="password" align="left" size="15"></td>
				<td align="right">Password</td>
				<td><input type="text" name="passwordRepetida" align="left"
					size="15"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Enviar" /></td>
			</tr>
		</table>
	</form>
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>