package com.sdi.presentation.admin.action;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.sdi.business.AdminService;
import com.sdi.dto.User;

import alb.util.console.Console;
import alb.util.menu.Action;

public class EliminarUsuarioAction implements Action {

	private static final String ADMIN_SERVICE_JNDI_KEY = "sdi3-37/sdi3-37.EJB/EjbAdminService!com.sdi.business.impl.admin.RemoteAdminService";
	
	public EliminarUsuarioAction() {}

	@Override
	public void execute() throws Exception {
		Context ctx = new InitialContext();
		AdminService aService = (AdminService) ctx.lookup(ADMIN_SERVICE_JNDI_KEY);
		
		//Pedir datos
		Long userID = Console.readLong("Id del usuario que desea eliminar");
		
		//Comprobamos que exista y borramos
		try {
			User u = aService.findUserById(userID);
			aService.deepDeleteUser(u.getId());			
			
			Console.print("\tUsuario borrado correctamente");
		} catch(NullPointerException e) {
			Console.print("\tNo existe el usuario");
		}		
	}
}