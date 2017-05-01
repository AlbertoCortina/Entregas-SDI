package com.sdi.presentation.user.action;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.sdi.dto.Task;

import alb.util.console.Console;
import alb.util.menu.Action;

public class CrearTareaAction implements Action {

	private static final String REST_SERVICE_URL = "http://localhost:8280/sdi3-37.Web/rest/UsersServiceRS";
	
	public CrearTareaAction() {}
	
	@Override
	public void execute() throws Exception {
		Console.println("------CREACIÓN DE TAREA------");
		
		//Pedimos los datos que añadiremos a la tarea
		String taskName = Console.readString("Nombre de la nueva tarea: ");
		
		Task t = new Task();
		t.setTitle(taskName);
		
		Response response = ClientBuilder.newClient()
				.target( REST_SERVICE_URL )
				.path("crearTarea")
				.request()
				.put(Entity.entity(t, MediaType.APPLICATION_JSON));
		
		if(response.getStatus() == 204) {
			Console.println("\tTarea agregada con éxito");
		} 
		else {
			Console.println("\tHubo algún problema al crear la tarea");
		}
		
	}

}
