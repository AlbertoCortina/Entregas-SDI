package com.sdi.presentation.user;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import com.sdi.dto.User;

import alb.util.console.Console;

public class Login {

	public static void main(String[] args) {
		String login = Console.readString("Usuario");
		String password = Console.readString("Contraseña");
		
		login(login, password);
		
		if(Sesion.getInstance().getUser() != null) {
			new MainMenu().execute();
		}
		else{
			Console.print("\tNo existe el usuario");
		}
	}
	
	private static void login(String login, String password) {
		GenericType<User> modelo = new GenericType<User>() {};
		
		 Response response = ClientBuilder.newClient()			
			.target(Sesion.getInstance().getRestServiceUrl())
			.path("login")
			.path("username="+login+"&&password="+password)
			.request()
			.get();
		
		User user = null;
		if(response.getStatus() == 200) {
			user = response.readEntity(modelo);
		}		
		Sesion.getInstance().setUser(user);	
	}	
}