package uo.sdi.acciones;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.Task;
import uo.sdi.model.User;

public class ListarTareasInboxAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = "EXITO";		
		User user = (User) request.getSession().getAttribute("user");
		String mostrarFinalizadas = request.getParameter("checkBox");
		request.setAttribute("checkBox", mostrarFinalizadas);
		TaskService taskService = Services.getTaskService();
		try {
			List<Task> tareasSinFinalizar = taskService.findInboxTasksByUserId(user.getId());
			
			if (mostrarFinalizadas != null) {
				List<Task> tareasFinalizadas = taskService.findFinishedInboxTasksByUserId(user.getId());
				tareasSinFinalizar.addAll(tareasFinalizadas);
			}
			request.setAttribute("tasks", tareasSinFinalizar);			
			
		} catch(BusinessException e){
			resultado = "FRACASO";
		}
		return resultado;
	}
}