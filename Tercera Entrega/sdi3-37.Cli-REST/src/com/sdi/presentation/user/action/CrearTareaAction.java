package com.sdi.presentation.user.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sdi.dto.Task;
import com.sdi.presentation.user.Authenticator;
import com.sdi.presentation.user.Sesion;

import alb.util.console.Console;
import alb.util.menu.Action;

public class CrearTareaAction implements Action {

	public CrearTareaAction() {}
	
	@Override
	public void execute() throws Exception {
		Console.println("------CREAR TAREA------");
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		
		//Pedir datos
		String nombre = Console.readString("Nombre de la tarea");
		String comentario = Console.readString("Comentario de la tarea");
		comentario = comentario.replace(":", "_");
		String fecha = Console.readString("Fecha planeada(yyyy-mm-dd)");
		
		//Creamos tarea con los datos
		Task t = new Task();
		t.setUserId(Sesion.getInstance().getUser().getId());
		t.setTitle(nombre);
		t.setComments(comentario);
		t.setCreated(formateador.parse(formateador.format(new Date())));
		t.setFinished(null);
		try {
			t.setPlanned(formateador.parse(fecha));
			
			//Comprobamos si la fecha planeada esta bien
			if(t.getPlanned().compareTo(t.getCreated()) >= 0) {
				Response response = ClientBuilder.newClient()
						.register(new Authenticator(Sesion.getInstance().getUser().getLogin(), Sesion.getInstance().getUser().getPassword()))
						.target(Sesion.getInstance().getRestServiceUrl())
						.path("crearTarea")	
						.request()
						.post(Entity.entity(t, MediaType.APPLICATION_JSON));
				
				if(response.getStatus() == 204) {
					Console.println("\tSe ha creada la tarea correctamente");
				} 
				else {
					Console.println("\tError en la petición de crear tarea");
				}
			}
			else {
				Console.println("\tFecha planeada menor que fecha actual, no se crea la tarea");
			}				
		} catch(ParseException e){
			Console.println("\tFormato de fecha introducido incorrecto");
		}
		Console.println("-----------------------");
	}
}