package com.sdi.presentation.user.action;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.presentation.user.Sesion;

import alb.util.console.Console;
import alb.util.menu.Action;

public class TareasCategoriaAction implements Action {

	public TareasCategoriaAction() {}
	
	@Override
	public void execute() throws Exception {
		Console.println("------TAREAS PENDIENTES Y RETRASADAS DE UNA CATEGORIA------");
		
		//Pedir datos
		String categoryName = Console.readString("Nombre de la categoría");
		
		//Comprobamos que exista la categoria para nuestro usuario
		GenericType<Category> modelo = new GenericType<Category>() {};
		
		Category categoria = ClientBuilder.newClient()				
				.target(Sesion.getInstance().getRestServiceUrl())
				.path("buscarCategoria")
				.path("id="+String.valueOf(Sesion.getInstance().getUser().getId())+"&&categoria="+categoryName)
				.request()
				.get()
				.readEntity(modelo);
		
		//Si existe mostramos las tareas
		if(categoria != null) {
			GenericType<List<Task>> modelo2 = new GenericType<List<Task>>() {};
			
			List<Task> tareas = ClientBuilder.newClient()				
					.target(Sesion.getInstance().getRestServiceUrl())
					.path("tareas")
					.path("id="+String.valueOf(Sesion.getInstance().getUser().getId())+"&&categoria="+categoryName)
					.request()
					.get()
					.readEntity(modelo2);
			
			print(tareas);
		} 
		else {
			Console.println("\tNo existe la categoría introducida");
		}
		
		Console.println("-----------------------------------------------------------");
		
	}

	private void print(List<Task> tareas) {
		Console.printf("%-20s%-20s%-20s%-25s%-20s\n", "ID", "TITULO", "FECHA_CREACION", "FECHA_FINALIZACION", "FECHA_PLANEADA");
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		for(Task t: tareas) {
			Console.printf("%-20s", t.getId());
			Console.printf("%-20s", t.getTitle());
			Console.printf("%-20s", formateador.format(t.getCreated()));
			Console.printf("%-25s", "");
			Console.printf("%-20s\n", formateador.format(t.getPlanned()));			
		}
	}
}