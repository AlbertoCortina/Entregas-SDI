package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.User;

public class AñadirTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = "EXITO";
		String nombre = request.getParameter("nombreTarea");
		User user = (User) request.getSession().getAttribute("user");		
		TaskService taskService = Services.getTaskService();
		try {
			taskService.createTask(nombre, user.getId());
		} catch (BusinessException e){
			resultado = "FRACASO";
		}		
		return resultado;
	}

}
