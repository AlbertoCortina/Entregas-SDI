package com.sdi.presentation.admin.action;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.sdi.business.AdminService;
import com.sdi.dto.User;

import alb.util.menu.Action;

public class ListarUsuariosAction implements Action {
	
	private static final String ADMIN_SERVICE_JNDI_KEY = "sdi3-37/sdi3-37.EJB/EjbAdminService!com.sdi.business.impl.admin.RemoteAdminService";
	
	public ListarUsuariosAction() {}

	@Override
	public void execute() throws Exception {
		try {
		Context ctx = new InitialContext();
		AdminService aService = (AdminService) ctx.lookup(ADMIN_SERVICE_JNDI_KEY);
		
		List<User> usuario = aService.findAllUsers();
		
		for(User u: usuario) {
			System.out.println(u.getLogin());
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}