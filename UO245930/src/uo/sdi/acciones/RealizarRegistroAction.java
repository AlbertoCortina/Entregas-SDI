package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.User;
import uo.sdi.model.types.UserStatus;

public class RealizarRegistroAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";

		User user = new User(request.getParameter("login"));
		user.setEmail(request.getParameter("email"));
		user.setIsAdmin(false);
		user.setPassword(request.getParameter("password"));
		user.setStatus(UserStatus.ENABLED);

		String passwordRepetida = request.getParameter("passwordRepetida");

		if (passwordRepetida == null
				|| !passwordRepetida.equals(user.getPassword())) {
			request.setAttribute("mensajeParaElUsuario", "Password no coincide");
			resultado = "FRACASO";
		} else {
			try {
				Services.getUserService().registerUser(user);
				request.setAttribute("mensajeParaElUsuario",
						"Registro realizado con éxito. Inicie sesión para entrar");
				resultado = "EXITO";
			} catch (BusinessException e) {
				request.setAttribute("mensajeParaElUsuario", e.getMessage());
				resultado = "FRACASO";
			}
		}
		return resultado;
	}
}