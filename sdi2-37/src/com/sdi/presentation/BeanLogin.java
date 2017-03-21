package com.sdi.presentation;

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
public class BeanLogin {

	private String login;
	private String contraseña;

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
			} else if (!u.getIsAdmin()) {
				Log.debug("Encontró un usuario, y no es un admin");
				resultado = "EXITO_NORMAL";
			}

		} catch (Exception e) {
			Log.debug("No se ha encontrado un usuario con el login y la contraseña especificadas");
			resultado = "ERROR";
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, 
							Internacionalizar.mensajes().getString("tituloError") + " ", 
							Internacionalizar.mensajes().getString("mensajeError")));
		}

		login = null;
		contraseña = null;
		return resultado;
	}

	public String cerrarSesion() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("userSession", null);
		Log.debug("Se cierra la sesión correctamente");
		return "EXITO";
	}
}