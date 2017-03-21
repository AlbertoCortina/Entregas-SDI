package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;

public class FinalizarTareaHoyAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		Long id = Long.parseLong(request.getParameter("id"));		
		TaskService taskService = Services.getTaskService();
		try {					
			taskService.markTaskAsFinished(id);			
		} catch (BusinessException e) {
			resultado = "FRACASO";
		}		
		return resultado;
	}
}