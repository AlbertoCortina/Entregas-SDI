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
		
		
		
		for(User u: usuarios) {
			List<Integer> numeros = Factories.persistence.getTaskDao().numberOfTasks(u);
			
			UserInfo userInfo = new UserInfo();
			userInfo.setTareasCompletadas(numeros.get(0));
			userInfo.setTareasCompletadasRetrasadas(numeros.get(1));
			userInfo.setTareasPlanificadas(numeros.get(2));
			userInfo.setTareasSinPlanificar(numeros.get(3));
			userInfo.setUser(u);
			
			info.add(userInfo);			
		}
		
		return info;
	}
}