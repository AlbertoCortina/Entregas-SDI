package com.sdi.presentation.user.action;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sdi.dto.Task;
import com.sdi.presentation.user.Sesion;

import alb.util.console.Console;
import alb.util.menu.Action;

public class MarcarTareaFinalizadaAction implements Action {

	public MarcarTareaFinalizadaAction() {}
	
	@Override
	public void execute() throws Exception {
		Console.println("------MARCAR TAREA COMO FINALIZADA------");
		
		//Pedir datos
		String tareaId = Console.readString("Id de la tarea que desea finalizar");
		
		//Comprobamos que nos hayan metido un número y no letras o caracteres
		if(isNumeric(tareaId)) {
			//Buscamos la tarea por el id
			GenericType<Task> modelo = new GenericType<Task>() {};
			
			Task task = ClientBuilder.newClient()				
					.target(Sesion.getInstance().getRestServiceUrl())
					.path("buscarTarea")				
					.path("id="+tareaId)
					.request()
					.get()
					.readEntity(modelo);
			
			//Comprobamos que exista la tarea
			if(task != null && (long) task.getUserId() == Sesion.getInstance().getUser().getId()) {
				//Comprobamos que no este finalizada
				if(task.getFinished() == null) {
					Response response = ClientBuilder.newClient()				
							.target(Sesion.getInstance().getRestServiceUrl())
							.path("finalizarTarea")	
							.request()
							.put(Entity.entity(new Task(task.getId()), MediaType.APPLICATION_JSON));
								
					if(response.getStatus() == 204) {
						Console.println("\tTarea con id "+tareaId+" marcada como finalizada correctamente");
					} 
					else {
						Console.println("\tHubo algún problema al marcar como finalizada la tarea con id "+tareaId);
					}
				}
				else {
					Console.println("\tLa tarea con id "+tareaId+ " ya estaba finalizada");
				}
			}
			else {				
				Console.println("\tNo existe la tarea con id "+tareaId);
			}
		}
		else {
			Console.println("\tId introducido inválido");
		}
		
		Console.println("----------------------------------------");
	}
	
	private boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
}