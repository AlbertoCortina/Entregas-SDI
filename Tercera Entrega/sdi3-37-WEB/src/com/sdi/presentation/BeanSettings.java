package com.sdi.presentation;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Se encarga de:
 * 	- Internacionalizar
 * 
 * @author Alberto Cortina
 *
 */
@ManagedBean(name="beanSettings")
@SessionScoped
public class BeanSettings implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Locale ENGLISH = new Locale("en");
	private static final Locale SPANISH = new Locale("es");
	private Locale locale = null;
	
	public Locale getLocale() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(locale == null) {
			locale = req.getLocale();
		}
		//Aqui habria que cambiar algo de código para coger locale del navegador
		//la primera vez que se accede a getLocale(), de momento dejamos como 
		//idioma de partida “es”
		return locale;
	}
	
	public void setSpanish() {
		locale = SPANISH;
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
	}
	
	public void setEnglish() {
		locale = ENGLISH;
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
	}
}
