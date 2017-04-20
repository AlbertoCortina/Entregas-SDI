package com.sdi.business.impl.admin;

import java.util.List;

import javax.ejb.Stateless;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.admin.command.DeepDeleteUserCommand;
import com.sdi.business.impl.admin.command.DisableUserCommand;
import com.sdi.business.impl.admin.command.EnableUserCommand;
import com.sdi.business.impl.admin.command.ListUsersCommand;
import com.sdi.dto.User;
import com.sdi.dto.UserInfo;
import com.sdi.infrastructure.Factories;

@Stateless
public class EjbAdminService implements LocalAdminService, RemoteAdminService {
	
	@Override
	public void deepDeleteUser(Long id) throws BusinessException {
		new DeepDeleteUserCommand(id).execute();		
	}

	@Override
	public void disableUser(Long id) throws BusinessException {
		new DisableUserCommand(id).execute();
	}

	@Override
	public void enableUser(Long id) throws BusinessException {
		new EnableUserCommand(id).execute();
	}

	@Override
	public List<User> findAllUsers() {
		return Factories.persistence.getUserDao().findAll();
	}

	@Override
	public User findUserById(final Long id) throws BusinessException {
		return Factories.persistence.getUserDao().findById(id);		
	}

	@Override
	public List<UserInfo> listAllUsers() throws BusinessException {
		return new ListUsersCommand().execute();
	}
}