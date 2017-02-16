package uo.sdi.business.impl.user.command;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.User;
import uo.sdi.model.types.UserStatus;
import uo.sdi.persistence.UserFinder;

public class FindLoggableUserCommand<T> implements Command<User> {

	private String login;
	private String password;

	public FindLoggableUserCommand(String login, String password) {
		this.login = login;
		this.password = password;
	}

	@Override
	public User execute() throws BusinessException {
		User user = UserFinder.findByLoginAndPassword(login, password);
		
		return (user != null && user.getStatus().equals(UserStatus.ENABLED)) ? user
				: null;
	}
}