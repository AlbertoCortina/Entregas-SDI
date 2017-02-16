package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.model.Task;
import uo.sdi.model.User;
import uo.sdi.persistence.TaskFinder;

public class ListarTareasAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = "EXITO";
		
		User user = (User) request.getAttribute("user");
		List<Task> listaTareas = null;
		TaskService taskService = Services.getTaskService();
//		listaTareas = taskService.;
		request.setAttribute("listaTareas", listaTareas);
		
		return resultado;
	}
}
