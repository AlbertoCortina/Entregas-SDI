package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.Task;
import uo.sdi.model.User;

public class ListaTareasHoyAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = "EXITO";
		User user = (User) request.getSession().getAttribute("user");
		TaskService taskService = Services.getTaskService();
		try {
			List<Task> tareasHoy = taskService.findTodayTasksByUserId(user.getId());
			request.setAttribute("tasks", tareasHoy);
		} catch(BusinessException e) {
			
		}
		
		return resultado;		
	}
}
