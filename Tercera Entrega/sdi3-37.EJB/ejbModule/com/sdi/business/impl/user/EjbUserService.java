package com.sdi.business.impl.user;

import java.util.List;

import javax.ejb.Stateless;
import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.user.command.FindLoggableUSerCommand;
import com.sdi.business.impl.user.command.RegisterUserCommand;
import com.sdi.business.impl.user.command.UpdateUserDetailsCommand;
import com.sdi.dto.User;
import com.sdi.infrastructure.Factories;

@Stateless
public class EjbUserService implements LocalUserService, RemoteUserService {

	@Override
	public Long registerUser(User user) throws BusinessException {
		return new RegisterUserCommand(user).execute();
	}

	@Override
	public void updateUserDetails(User user) throws BusinessException {
		new UpdateUserDetailsCommand(user).execute();
	}

	@Override
	public User findLoggableUser(final String login, final String password)
			throws BusinessException {
		return new FindLoggableUSerCommand<User>(login, password).execute();
	}

	@Override
	public User findByLogin(final String login) throws BusinessException {
		return Factories.persistence.getUserDao().findByLogin(login);
	}

	@Override
	public void deleteAll() throws BusinessException {
		Factories.persistence.getUserDao().deleteAll();
	}

	@Override
	public void save(final User user) throws BusinessException {
		Factories.persistence.getUserDao().save(user);
	}

	@Override
	public List<User> findAll() throws BusinessException {
		return Factories.persistence.getUserDao().findAll();
	}
}