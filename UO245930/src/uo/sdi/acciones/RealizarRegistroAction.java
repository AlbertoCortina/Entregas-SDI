package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;

public class RealizarRegistroAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		User user = new User ();
		user.setEmail(request.getParameter("email"));
		user.setIsAdmin(false);
		user.setLogin(request.getParameter("login"));
		user.setPassword(request.getParameter("password"));
		user.setStatus(UserStatus.ENABLED);
		
		String passwordRepetida = request.getParameter("passwordRepetida");
		if (passwordRepetida == null || !passwordRepetida.equals(user.getPassword())){
			//Error
			request.setAttribute("Error", "Password no coincide");
			return "ERROR";
		}
		else{
			try {
				Services.getUserService().registerUser(user);
				return "EXITO";
			} catch(BusinessException e) {
				//Error
				e.getMessage();
			}
		}
	}

}
