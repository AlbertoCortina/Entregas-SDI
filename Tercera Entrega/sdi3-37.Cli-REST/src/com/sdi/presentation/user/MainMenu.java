package com.sdi.presentation.user;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import com.sdi.dto.User;
import com.sdi.presentation.user.action.CrearTareaAction;
import com.sdi.presentation.user.action.ListarCategoriasAction;
import com.sdi.presentation.user.action.MarcarTareaFinalizadaAction;
import com.sdi.presentation.user.action.TareasCategoriaAction;

import alb.util.console.Console;
import alb.util.menu.BaseMenu;

public class MainMenu extends BaseMenu {

	private static final String REST_SERVICE_URL = "http://localhost:8280/sdi3-37.Web/rest/UsersServiceRS";
	
	private static User user;	
	
	public static User getUser() {
		return user;
	}
	
	public MainMenu() {			
		menuOptions = new Object[][] { 	
			{"USUARIO "+ user.getLogin(), null},
			{ "Listar categorías", 				ListarCategoriasAction.class}, 
			{ "Ver tareas de una categoría", 	TareasCategoriaAction.class},
			{ "Marcar como finalizada", 		MarcarTareaFinalizadaAction.class },
			{ "Crear tarea",					CrearTareaAction.class}
		};
	}

	public static void main(String[] args) {
		String login = Console.readString("Usuario");
		String password = Console.readString("Contraseña");
		
		user = login(login, password);
		
		if(user != null) {
			Console.println();
			Console.print("LOGIN CORRECTO, INICIANDO SESIÓN------>");
			new MainMenu().execute();
		}
		else{
			Console.print("No existe el usuario");
		}
	}
	
	private static User login(String login, String password) {
		GenericType<User> modelo = new GenericType<User>() {};
		
		User user = ClientBuilder.newClient()			
			.target(REST_SERVICE_URL)
			.path("login")
			.path("username="+login+"&&password="+password)
			.request()
			.get()
			.readEntity(modelo);
		
		return user;	
	}
}