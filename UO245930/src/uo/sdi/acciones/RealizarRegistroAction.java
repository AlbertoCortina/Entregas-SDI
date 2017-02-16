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
		user.setAdmin(false);
		user.setPassword(request.getParameter("password"));
		user.setStatus(UserStatus.ENABLED);

		String passwordRepetida = request.getParameter("passwordRepetida");

		if (passwordRepetida == null
				|| !passwordRepetida.equals(user.getPassword())) {
			// Error
			request.setAttribute("Error", "Password no coincide");
			resultado = "ERROR";
		} else {
			try {
				Services.getUserService().registerUser(user);
				resultado = "EXITO";
			} catch (BusinessException e) {
				// Error
				resultado = e.getMessage();
			}
		}
		return resultado;
	}
}