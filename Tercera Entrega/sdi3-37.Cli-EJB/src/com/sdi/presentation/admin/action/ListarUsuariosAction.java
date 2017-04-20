package com.sdi.presentation.admin.action;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.sdi.business.AdminService;
import com.sdi.dto.UserInfo;

import alb.util.console.Console;
import alb.util.menu.Action;

public class ListarUsuariosAction implements Action {
	
	private static final String ADMIN_SERVICE_JNDI_KEY = "sdi3-37/sdi3-37.EJB/EjbAdminService!com.sdi.business.impl.admin.RemoteAdminService";
	
	public ListarUsuariosAction() {}

	@Override
	public void execute() throws Exception {
		try {
			Context ctx = new InitialContext();
			AdminService aService = (AdminService) ctx.lookup(ADMIN_SERVICE_JNDI_KEY);
			
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
			if(!u.getUser().getIsAdmin()) {				
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