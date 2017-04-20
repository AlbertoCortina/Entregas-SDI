package com.sdi.presentation.admin.action;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.sdi.business.AdminService;
import com.sdi.dto.User;
import com.sdi.dto.types.UserStatus;

import alb.util.console.Console;
import alb.util.menu.Action;

public class DeshabilitarUsuarioAction implements Action {

	private static final String ADMIN_SERVICE_JNDI_KEY = "sdi3-37/sdi3-37.EJB/EjbAdminService!com.sdi.business.impl.admin.RemoteAdminService";
	
	public DeshabilitarUsuarioAction() {}

	@Override
	public void execute() throws Exception {
		Context ctx = new InitialContext();
		AdminService aService = (AdminService) ctx.lookup(ADMIN_SERVICE_JNDI_KEY);
		
		//Pedir datos
		Long userID = Console.readLong("Id del usuario que desea deshabilitar");
		
		//Comprobamos que exista y deshabilitamos
		try {
			User u = aService.findUserById(userID);
			
			if(u.getStatus().equals(UserStatus.DISABLED)) {
				Console.print("\tUsuario ya estaba deshabilitado");
			}
			else {
				aService.disableUser(u.getId());
				Console.print("\tUsuario deshabilitado correctamente");
			}
		} catch(NullPointerException e) {
			Console.print("\tNo existe el usuario");
		}		
	}
}