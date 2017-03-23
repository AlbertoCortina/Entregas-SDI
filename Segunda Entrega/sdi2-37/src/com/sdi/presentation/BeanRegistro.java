package com.sdi.presentation;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import alb.util.log.Log;

import com.sdi.business.Services;
import com.sdi.business.UserService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.User;
import com.sdi.presentation.util.Internacionalizar;

@ManagedBean(name="beanRegistro")
@SessionScoped
public class BeanRegistro {

	private String login;
	private String email;
	private String password;
	private String password2;
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword2() {
		return password2;
	}
	
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
	public String registrar() {		
		String resultado = "";
		try {
			UserService uService = Services.getUserService();
			User u = new User(login, email, password);
			uService.registerUser(u);
			resultado = "EXITO";
			Log.debug("Registro correcto");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, 
							Internacionalizar.mensajes().getString("tituloExitoRegistro") + " ", 
							Internacionalizar.mensajes().getString("tituloMensajeExitoRegistro")));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		} catch (BusinessException e) {
			resultado = "ERROR";
			Log.debug("Hubo algún problema en el registro");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, 
							Internacionalizar.mensajes().getString("tituloErrorRegistro") + " ", 
							Internacionalizar.mensajes().getString("tituloMensajeErrorRegistro")));
		}
		
		login = null;
		email = null;
		password = null;
		password2 = null;
		return resultado;
	}	
	
	public void validarLogin(FacesContext c, UIComponent ui, Object o) throws ValidatorException {
		try {
			User u = Services.getUserService().findByLogin((String) o);
			
			if(u != null) {
				Log.debug("Login repetido");
				throw new ValidatorException(new FacesMessage("login repetido"));
			}
			Log.debug("Login válido");			
		} catch (BusinessException e) {
			Log.debug("Ha ocurrido un error buscando el usuario");			
		}
	}
	
	
	public void coincidenPasswords(FacesContext c, UIComponent ui, Object o) throws ValidatorException {
		
		String field1Id = (String) ui.getAttributes().get("password");
		
		UIInput textInput = (UIInput) c.getViewRoot().findComponent(field1Id);
		
		if (textInput == null)
	        throw new IllegalArgumentException(String.format("Unable to find component with id %s",field1Id));
		
		String pass = (String) textInput.getValue();
		
		if(!pass.equals((String) o)){
			Log.debug("Contraseñas no coinciden");
			throw new ValidatorException(new FacesMessage("Contraseñas no coinciden"));
		}
		
		Log.debug("Contraseñas coinciden");
	}
	
	
//	public void validarContraseña(FacesContext c, UIComponent ui, Object passwordConfirmation) throws ValidatorException {
//		
//		Password inputTextPassword = (Password)c.getViewRoot().findComponent("form-registro:input-password");
//		String password = (String)(inputTextPassword.getSubmittedValue() != null?
//				inputTextPassword.getSubmittedValue()
//				: inputTextPassword.getValue());
//		
//		if(!password.equals(passwordConfirmation)) {
//			Log.debug("Contraseñas distintas");
//			throw new ValidatorException(new FacesMessage("contraseñas distintas"));
//		}	
//		Log.debug("Contraseñas iguales");
//	}
}