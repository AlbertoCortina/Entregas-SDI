package com.sdi.presentation.user.action;

import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import com.sdi.dto.Category;
import com.sdi.presentation.user.Authenticator;
import com.sdi.presentation.user.Sesion;

import alb.util.console.Console;
import alb.util.menu.Action;

public class ListarCategoriasAction implements Action {
	
	public ListarCategoriasAction() {}
	
	@Override
	public void execute() throws Exception {
		Console.println("------LISTA DE CATEGORIAS PARA EL USUARIO "+Sesion.getInstance().getUser().getLogin()+"------");
		
		GenericType<List<Category>> modelo = new GenericType<List<Category>>() {};
		
		Response response = ClientBuilder.newClient()				
				.register(new Authenticator(Sesion.getInstance().getUser().getLogin(), Sesion.getInstance().getUser().getPassword()))
				.target(Sesion.getInstance().getRestServiceUrl())
				.path("categorias")
				.path(String.valueOf(Sesion.getInstance().getUser().getId()))
				.request()
				.get();				
		
		List<Category> categorias = null;
		if(response.getStatus() == 200) {
			categorias = response.readEntity(modelo);
			print(categorias);
		}
		else{
			Console.println("\tError en la petición de listar categorias");
		}
		
		Console.println("-----------------------------------------------------");
	}

	private void print(List<Category> categorias) {		
		for(Category c: categorias) {
			Console.println(c.getName());
		}		
	}
}