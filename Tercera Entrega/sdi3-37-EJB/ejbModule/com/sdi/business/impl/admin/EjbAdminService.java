package com.sdi.business.impl.admin;

import java.util.List;

import javax.ejb.Stateless;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.admin.command.DeepDeleteUserCommand;
import com.sdi.business.impl.admin.command.DisableUserCommand;
import com.sdi.business.impl.admin.command.EnableUserCommand;
import com.sdi.business.impl.command.Command;
import com.sdi.business.impl.command.CommandExecutor;
import com.sdi.business.localServices.LocalAdminService;
import com.sdi.business.remoteServices.RemoteAdminService;
import com.sdi.dto.User;
import com.sdi.infrastructure.Factories;

@Stateless
public class EjbAdminService implements LocalAdminService, RemoteAdminService {
	
	@Override
	public void deepDeleteUser(Long id) throws BusinessException {
		new CommandExecutor<Void>().execute( new DeepDeleteUserCommand( id ) );
	}

	@Override
	public void disableUser(Long id) throws BusinessException {
		new CommandExecutor<Void>().execute( new DisableUserCommand( id ) );
	}

	@Override
	public void enableUser(Long id) throws BusinessException {
		new CommandExecutor<Void>().execute( new EnableUserCommand( id ) );
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserById(final Long id) throws BusinessException {
		return new CommandExecutor<User>().execute( new Command<User>() {
			@Override public User execute() throws BusinessException {
				return Factories.persistence.getUserDao().findById(id);
			}
		});
	}

}
