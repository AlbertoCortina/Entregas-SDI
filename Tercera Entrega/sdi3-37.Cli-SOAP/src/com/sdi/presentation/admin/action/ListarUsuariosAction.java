package com.sdi.presentation.admin.action;

import java.util.List;

import com.sdi.ws.AdminService;
import com.sdi.ws.EjbAdminServiceService;
import com.sdi.ws.UserInfo;
import alb.util.console.Console;
import alb.util.menu.Action;

public class ListarUsuariosAction implements Action {	
	
	public ListarUsuariosAction() {}

	@Override
	public void execute() throws Exception {
		try {
			AdminService aService = new EjbAdminServiceService().getAdminServicePort();
			
			List<UserInfo> usuarios = aService.listAllUsers();
			
			print(usuarios);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void print(List<UserInfo> userInfo) {		
		Console.printf("%-20s%-20s%-20s%-20s%-27s%-20s%-20s\n","LOGIN","EMAIL","ESTADO","COMPLETADAS", "COMPLETADAS_RETRASADAS", "PLANEADAS", "NO_PLANEADAS");
		for(UserInfo u: userInfo) {
			if(!u.getUser().isIsAdmin()) {				
				Console.printf("%-20s", u.getUser().getLogin());
				Console.printf("%-20s", u.getUser().getEmail());
				Console.printf("%-25s", String.valueOf(u.getUser().getStatus()));
				Console.printf("%-25s",u.getTareasCompletadas());
				Console.printf("%-21s",u.getTareasCompletadasRetrasadas());
				Console.printf("%-22s",u.getTareasPlanificadas());
				Console.printf("%-20s\n", u.getTareasSinPlanificar());				
			}			
		}
	}
}