package com.sdi.presentation;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.sdi.dto.User;
import com.sdi.dto.types.UserStatus;

/**
 * Se encarga de:
 * 	
 * @author Alberto Cortina
 *
 */
@ManagedBean(name="beanUser")
@SessionScoped
public class BeanUser implements Serializable {		
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String login;
	private String email;		
	private Boolean isAdmin = false;
	private UserStatus status = UserStatus.ENABLED;
	
	public BeanUser(User user) {
		this.id = user.getId();
		this.login = user.getLogin();
		this.email = user.getEmail();
		this.email = user.getEmail();
		this.isAdmin = user.getIsAdmin();
		this.status = user.getStatus();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}	
}