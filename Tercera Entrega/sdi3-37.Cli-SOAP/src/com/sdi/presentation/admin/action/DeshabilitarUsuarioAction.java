package com.sdi.presentation.admin.action;

import com.sdi.ws.AdminService;
import com.sdi.ws.EjbAdminServiceService;
import com.sdi.ws.User;
import com.sdi.ws.UserStatus;

import alb.util.console.Console;
import alb.util.menu.Action;

public class DeshabilitarUsuarioAction implements Action {

	public DeshabilitarUsuarioAction() {}

	@Override
	public void execute() throws Exception {
		AdminService aService = new EjbAdminServiceService().getAdminServicePort();
				
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