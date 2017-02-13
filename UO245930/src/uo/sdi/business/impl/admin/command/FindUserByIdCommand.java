package uo.sdi.business.impl.admin.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.User;
import uo.sdi.persistence.util.Jpa;

public class FindUserByIdCommand implements Command<User> {

	private Long id;

	public FindUserByIdCommand(Long id) {
		this.id = id;
	}

	@Override
	public User execute() throws BusinessException {
		User user = Jpa.getManager().find(User.class, id);
		BusinessCheck.isNotNull(user, "User does not exist");

		return user;
	}
}