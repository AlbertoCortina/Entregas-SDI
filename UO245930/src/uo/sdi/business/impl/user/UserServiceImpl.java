package uo.sdi.business.impl.user;

import java.util.List;

import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.CommandExecutor;
import uo.sdi.business.impl.user.command.FindAllCommand;
import uo.sdi.business.impl.user.command.FindLoggableUserCommand;
import uo.sdi.business.impl.user.command.RegisterUserCommand;
import uo.sdi.business.impl.user.command.UpdateUserDetailsCommand;
import uo.sdi.model.User;

public class UserServiceImpl implements UserService {

	@Override
	public void registerUser(User user) throws BusinessException {
		new CommandExecutor<Void>().execute(new RegisterUserCommand(user));
	}

	@Override
	public void updateUserDetails(User user) throws BusinessException {
		new CommandExecutor<Void>().execute(new UpdateUserDetailsCommand(user));
	}

	@Override
	public User findLoggableUser(String login, String password)
			throws BusinessException {
		return new CommandExecutor<User>()
				.execute(new FindLoggableUserCommand<User>(login, password));
	}

	@Override
	public List<User> findAll() throws BusinessException {
		return new CommandExecutor<List<User>>()
				.execute(new FindAllCommand());
	}
}