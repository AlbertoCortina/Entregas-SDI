package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.User;
import uo.sdi.model.types.UserStatus;

public class CambiarEstadoUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		Long id =  Long.parseLong(request.getParameter("id"));
//		String status = (String) request.getAttribute("status");
		//Como obtengo el usuario que esta seleccionado
		AdminService adminService = Services.getAdminService();		
		
		try {			
//			if(status.equals("ENABLED")) {
//				adminService.disableUser(id);
//			}
//			else if(status.equals("DISABLED")) {
//				adminService.enableUser(id);
//			}
			adminService.disableUser(id);
			Log.info("Habilitar/Deshabilitar correcto");
		} catch (BusinessException e) {
			Log.debug("Se ha producido algún error al habilitar/deshabilitar: %s", e.getMessage());
			resultado = "FRACASO";
		}
		return resultado;
	}
}