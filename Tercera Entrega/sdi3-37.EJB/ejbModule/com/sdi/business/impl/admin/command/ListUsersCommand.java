package com.sdi.business.impl.admin.command;

import java.util.ArrayList;
import java.util.List;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.command.Command;
import com.sdi.dto.User;
import com.sdi.dto.UserInfo;
import com.sdi.infrastructure.Factories;

public class ListUsersCommand implements Command<List<UserInfo>> {

	@Override
	public List<UserInfo> execute() throws BusinessException {		
		List<User> usuarios = Factories.persistence.getUserDao().findAll();
				
		List<UserInfo> info = new ArrayList<UserInfo>();
		
		int completadas = 0;
		int completadasRetrasadas = 0;
		int planificadas = 0;
		int noPlanificadas = 0;
		
		for(User u: usuarios) {
			completadas = Factories.persistence.getTaskDao().tasksCompleted(u);
			completadasRetrasadas =  Factories.persistence.getTaskDao().tasksCompletedDelayed(u);
			planificadas =  Factories.persistence.getTaskDao().tasksPlanned(u);
			noPlanificadas =  Factories.persistence.getTaskDao().tasksNotPlanned(u);
			
			UserInfo userInfo = new UserInfo();
			userInfo.setTareasCompletadas(completadas);
			userInfo.setTareasCompletadasRetrasadas(completadasRetrasadas);
			userInfo.setTareasPlanificadas(planificadas);
			userInfo.setTareasSinPlanificar(noPlanificadas);
			userInfo.setUser(u);
			
			info.add(userInfo);			
		}
		
		return info;
	}
}