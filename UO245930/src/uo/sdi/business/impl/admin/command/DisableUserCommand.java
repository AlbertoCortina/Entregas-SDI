package uo.sdi.business.impl.admin.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.User;
import uo.sdi.model.types.UserStatus;
import uo.sdi.persistence.util.Jpa;

public class DisableUserCommand implements Command<Void> {

	private Long userId;

	public DisableUserCommand(Long id) {
		this.userId = id;
	}

	@Override
	public Void execute() throws BusinessException {
		// Comprobamos que el usuario exista
		User user = Jpa.getManager().find(User.class, userId);
		BusinessCheck.isNotNull(user, "User does not exist");

		user.setStatus(UserStatus.DISADLED);
		Jpa.getManager().merge(user);

		return null;
	}
}