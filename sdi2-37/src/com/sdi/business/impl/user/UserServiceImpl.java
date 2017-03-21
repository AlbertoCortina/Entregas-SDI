package com.sdi.business.impl.user;

import java.util.List;

import com.sdi.business.UserService;
import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.command.Command;
import com.sdi.business.impl.command.CommandExecutor;
import com.sdi.business.impl.user.command.FindLoggableUSerCommand;
import com.sdi.business.impl.user.command.RegisterUserCommand;
import com.sdi.business.impl.user.command.UpdateUserDetailsCommand;
import com.sdi.dto.User;
import com.sdi.persistence.Persistence;

public class UserServiceImpl implements UserService {

	@Override
	public Long registerUser(User user) throws BusinessException {
		return new CommandExecutor<Long>().execute( 
				new RegisterUserCommand( user ) 
		);
	}

	@Override
	public void updateUserDetails(User user) throws BusinessException {
		new CommandExecutor<Void>().execute( 
				new UpdateUserDetailsCommand( user ) 
		);
	}	

	@Override
	public User findLoggableUser(final String login, final String password) 
			throws BusinessException {
		
		return new CommandExecutor<User>().execute( 
				new FindLoggableUSerCommand<User>(login, password) 
		);
	}
	
	@Override
	public User findByLogin(final String login) throws BusinessException {
		return new CommandExecutor<User>().execute( new Command<User>() {
			@Override
			public User execute() throws BusinessException {
				return Persistence.getUserDao().findByLogin(login);
			}			
		});
	}
	
	@Override
	public void deleteAll() throws BusinessException {
		new CommandExecutor<Void>().execute( new Command<Void>() {
			@Override
			public Void execute() throws BusinessException {
				Persistence.getUserDao().deleteAll();
				return null;
			}			
		});
	}
	
	@Override
	public void save(final User user) throws BusinessException {
		new CommandExecutor<Void>().execute( new Command<Void>() {
			@Override
			public Void execute() throws BusinessException {
				Persistence.getUserDao().save(user);
				return null;
			}			
		});
	}
	
	@Override
	public List<User> findAll() throws BusinessException {
		return new CommandExecutor<List<User>>().execute( new Command<List<User>>() {
			@Override
			public List<User> execute() throws BusinessException {
				return Persistence.getUserDao().findAll();
			}			
		});
	}
}