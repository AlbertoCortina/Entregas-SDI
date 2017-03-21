package uo.sdi.business.impl.user.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.UserCheck;
import uo.sdi.model.User;
import uo.sdi.persistence.util.Jpa;

public class UpdateUserDetailsCommand implements Command<Void> {

	private User user;

	public UpdateUserDetailsCommand(User user) {
		this.user = user;
	}

	@Override
	public Void execute() throws BusinessException {
		User u = Jpa.getManager().find(User.class, user.getId());

		checkUserExist(u);
		checkStatusIsNotChanged(u, user);
		checkIsAdminNotChanged(u, user);
		UserCheck.isValidEmailSyntax(user);
		UserCheck.minLoginLength(user);
		UserCheck.minPasswordLength(user);
		UserCheck.hasNumbersAndLettersPassword(user);

		if (loginIsChanged(u, user)) {
			UserCheck.notRepeatedLogin(user);
		}

		Jpa.getManager().merge(user);

		return null;
	}

	private void checkIsAdminNotChanged(User previous, User current)
			throws BusinessException {
		BusinessCheck.isTrue(isAdminNotChanged(previous, current),
				"A user cannot be upgraded or downgraded");
	}

	private void checkUserExist(User previous) throws BusinessException {
		BusinessCheck.isNotNull(previous, "The user does not exist");
	}

	private void checkStatusIsNotChanged(User previous, User current)
			throws BusinessException {
		BusinessCheck.isTrue(statusIsNotChanged(previous, current),
				"Only the admin can change the satus");
	}

	private boolean statusIsNotChanged(User previous, User current) {
		return previous.getStatus().equals(current.getStatus());
	}

	private boolean loginIsChanged(User previous, User current) {
		return !previous.getLogin().equals(current.getLogin());
	}

	private boolean isAdminNotChanged(User previous, User current) {
		return previous.getIsAdmin() == current.getIsAdmin();
	}
}