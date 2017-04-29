package com.sdi.presentation.user.action;

import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

import com.sdi.dto.Category;
import com.sdi.presentation.user.MainMenu;

import alb.util.console.Console;
import alb.util.menu.Action;

public class ListarCategoriasAction implements Action {

	private static final String REST_SERVICE_URL = "http://localhost:8280/sdi3-37.Web/rest/UsersServiceRS";
		
	public ListarCategoriasAction() {}
	
	@Override
	public void execute() throws Exception {
		Console.println("------LISTA DE CATEGORIAS PARA EL USUARIO "+MainMenu.getUser().getLogin()+"------");
		
		GenericType<List<Category>> modelo = new GenericType<List<Category>>() {};
		
		List<Category> categorias = ClientBuilder.newClient()				
				.target(REST_SERVICE_URL)
				.path("categorias")
				.path(String.valueOf(MainMenu.getUser().getId()))
				.request()
				.get()
				.readEntity(modelo);
		
		print(categorias);
		
		Console.println("-----------------------------------------------------");
	}

	private void print(List<Category> categorias) {		
		for(Category c: categorias) {
			Console.println(c.getName());
		}		
	}
}