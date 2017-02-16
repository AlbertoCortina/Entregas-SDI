package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.User;

public class ModificarDatosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

//		String resultado = "EXITO";
//
//		String nuevoEmail = request.getParameter("email");
//		HttpSession session = request.getSession();
//		User user = ((User) session.getAttribute("user"));
//		User userClone = Cloner.clone(user);
//		userClone.setEmail(nuevoEmail);
//		try {
//			UserService userService = Services.getUserService();
//			userService.updateUserDetails(userClone);
//			Log.debug("Modificado email de [%s] con el valor [%s]",
//					userClone.getLogin(), nuevoEmail);
//			session.setAttribute("user", userClone);
//		} catch (BusinessException b) {
//			Log.debug(
//					"Algo ha ocurrido actualizando el email de [%s] a [%s]: %s",
//					user.getLogin(), nuevoEmail, b.getMessage());
//			resultado = "FRACASO";
//		}
		return null;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
}