package com.sdi.presentation.admin.action;

import com.sdi.ws.AdminService;
import com.sdi.ws.EjbAdminServiceService;
import com.sdi.ws.User;

import alb.util.console.Console;
import alb.util.menu.Action;

public class EliminarUsuarioAction implements Action {
	
	public EliminarUsuarioAction() {}

	@Override
	public void execute() throws Exception {
		AdminService aService = new EjbAdminServiceService().getAdminServicePort();
		
		//Pedir datos
		Long userID = Console.readLong("Id del usuario que desea eliminar");
		
		//Comprobamos que exista y borramos
		try {
			User u = aService.findUserById(userID);
			
			if(u == null) {
				Console.print("\tNo existe el usuario");
			}
			else {
				aService.deepDeleteUser(u.getId());					
				Console.print("\tUsuario borrado correctamente");
			}
			
		} catch(NullPointerException e) {
			Console.print("\tNo existe el usuario");
		}		
	}
}