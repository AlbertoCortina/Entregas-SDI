package com.sdi.presentation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import alb.util.log.Log;

import com.sdi.business.Services;
import com.sdi.dto.User;

@ManagedBean(name="user")
@SessionScoped
public class BeanUser {
	
	private User user = new User();
	private String contraseña;
	
	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	
	
	public String loguearse() {
		String resultado = "";		
		try {
			User u = Services.getUserService().findLoggableUser(user.getLogin(), user.getPassword());
			
			if(u.getIsAdmin()) {
				Log.debug("Encontro un usuario, y es admin");
				System.err.println("Usuario admin");
				resultado = "EXITO_ADMIN";
			}
			else if (!u.getIsAdmin()){
				Log.debug("Encontro un usuario, y no es un admin");
				System.err.println("Usuario normal");
				resultado = "EXITO_NORMAL";
			}
		} catch (Exception e) {
			Log.debug("No se ha encontrado un usuario con el login y la contraseña especificadas");
			resultado = "ERROR";
		}
		return resultado;
	}
	
	public void registrar() {
		String resultado = "EXITO";
		System.out.println(user);
	}
}