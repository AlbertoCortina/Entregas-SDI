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
	
	public MainMenu() {			
		menuOptions = new Object[][] { 	
			{"USUARIO "+ Sesion.getInstance().getUser().getLogin(), null},
			{ "Listar categorías", 				ListarCategoriasAction.class}, 
			{ "Ver tareas de una categoría", 	TareasCategoriaAction.class},
			{ "Marcar tarea como finalizada", 	MarcarTareaFinalizadaAction.class },
			{ "Crear tarea",					CrearTareaAction.class}
		};
	}

	public static void main(String[] args) {
		String login = Console.readString("Usuario");
		String password = Console.readString("Contraseña");
		
		login(login, password);
		
		if(Sesion.getInstance().getUser() != null) {
			Console.println();
			Console.print("LOGIN CORRECTO, INICIANDO SESIÓN------>");
			new MainMenu().execute();
		}
		else{
			Console.print("\tNo existe el usuario");
		}
	}
	
	private static void login(String login, String password) {
		GenericType<User> modelo = new GenericType<User>() {};
		
		User user = ClientBuilder.newClient()			
			.target(Sesion.getInstance().getRestServiceUrl())
			.path("login")
			.path("username="+login+"&&password="+password)
			.request()
			.get()
			.readEntity(modelo);
		
		Sesion.getInstance().setUser(user);			
	}
}