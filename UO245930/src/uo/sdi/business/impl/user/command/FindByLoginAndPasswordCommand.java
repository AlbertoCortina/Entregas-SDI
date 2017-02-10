package uo.sdi.business.impl.user.command;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.User;
import uo.sdi.persistence.UserFinder;

public class FindByLoginAndPasswordCommand implements Command<User> {

	private String login;
	private String password;
	
	public FindByLoginAndPasswordCommand (String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	@Override
	public User execute () throws BusinessException {
		return UserFinder.findByLoginAndPassword(login, password);
	}
}