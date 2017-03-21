package com.sdi.presentation.util;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class Internacionalizar {

	public static ResourceBundle mensajes() {
		FacesContext c = FacesContext.getCurrentInstance();
		ResourceBundle b = c
				.getApplication()
				.getResourceBundle(c, "msgs");
		return b;
	}
}