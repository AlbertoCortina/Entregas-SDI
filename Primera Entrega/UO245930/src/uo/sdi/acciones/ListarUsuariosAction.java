package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.User;

public class ListarUsuariosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		List<User> listaUsuarios;		
		UserService userService = Services.getUserService();
		try {
			listaUsuarios = userService.findAll();
			request.setAttribute("listaUsuarios", listaUsuarios);
			Log.debug(
					"Obtenida lista de usuarios conteniendo [%d] usuarios",
					listaUsuarios.size());
		} catch (BusinessException e) {
			Log.debug("Algo ha ocurrido obteniendo lista de usuarios: %s",
					e.getMessage());
			resultado = "FRACASO";
		}
		return resultado;
	}
}