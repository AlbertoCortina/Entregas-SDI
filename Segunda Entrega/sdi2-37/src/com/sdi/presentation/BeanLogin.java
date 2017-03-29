package com.sdi.presentation;

import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import alb.util.log.Log;

import com.sdi.business.Services;
import com.sdi.dto.User;
import com.sdi.presentation.util.Internacionalizar;

/**
 * Se encarga de: - Autenticación - Cerrar sesión del usuario
 * 
 * @author Alberto Cortina
 * 
 */
@ManagedBean(name = "beanLogin")
@SessionScoped
public class BeanLogin implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String login;
	private String contraseña;
	
	private String result = "login_form_result_valid";

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String loguearse() {
		String resultado = "";
		try {
			User u = Services.getUserService().findLoggableUser(login,
					contraseña);

			u.setPassword(null);

			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("userSession", new BeanUser(u));

			if (u.getIsAdmin()) {
				Log.debug("Encontró un usuario, y es admin");
				resultado = "EXITO_ADMIN";
				putUserInSession(u);
			} else if (!u.getIsAdmin()) {
				Log.debug("Encontró un usuario, y no es un admin");
				resultado = "EXITO_NORMAL";
				putUserInSession(u);
			}

		} catch (Exception e) {			
			resultado = "ERROR";
			
			setResult("login_form_result_error");
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, 
							Internacionalizar.mensajes().getString("tituloError") + " ", 
							Internacionalizar.mensajes().getString("mensajeError")));
			
			Log.debug("No se ha encontrado un usuario con el login y la contraseña especificadas");
		}

		login = null;
		contraseña = null;
		return resultado;
	}
	
	private void putUserInSession(User user){
		Map<String,	Object>	session	=	FacesContext
										.getCurrentInstance()
										.getExternalContext()
										.getSessionMap();
		
		session.put("LOGGEDIN_USER",	user);
	}

	public String cerrarSesion() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("userSession", null);
		Log.debug("Se cierra la sesión correctamente");
		return "EXITO";
	}
}