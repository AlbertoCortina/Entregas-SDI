package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import alb.util.log.Log;

public class CambiarEstadoUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		Long id =  Long.parseLong(request.getParameter("id"));
		
		String status = (String) request.getParameter("status");		
		AdminService adminService = Services.getAdminService();			
		try {			
			if(status.equals("ENABLED")) {
				adminService.disableUser(id);
			}
			else if(status.equals("DISABLED")) {
				adminService.enableUser(id);
			}			
			request.setAttribute("listaUsuarios", adminService.findAllUsers());
			Log.info("Habilitar/Deshabilitar correcto");
		} catch (BusinessException e) {
			Log.debug("Se ha producido algún error al habilitar/deshabilitar: %s", e.getMessage());
			resultado = "FRACASO";
		}
		return resultado;
	}
}