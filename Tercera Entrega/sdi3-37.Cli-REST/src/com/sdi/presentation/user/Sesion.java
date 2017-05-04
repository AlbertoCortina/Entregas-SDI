package com.sdi.presentation.user;

import com.sdi.dto.User;

public class Sesion {

	private static Sesion INSTANCE = null;
	
	private String REST_SERVICE_URL = "http://localhost:8280/sdi3-37.Web/rest/UsersServiceRS";
	private User user;
	
	private Sesion() {}
	
	public static Sesion getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Sesion();
		}
		return INSTANCE;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRestServiceUrl() {
		return REST_SERVICE_URL;
	}
}
