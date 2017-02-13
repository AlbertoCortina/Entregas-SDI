package uo.sdi.business.impl.util;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.*;
import uo.sdi.model.types.UserStatus;
import uo.sdi.persistence.util.Jpa;

public class TaskCheck {

	public static void categoryExists(Task task) throws BusinessException {
		Category c = Jpa.getManager().find(Category.class,
				task.getCategory().getId());
		BusinessCheck.isNotNull(c, "The category of the task does not exist");
	}

	public static void userExists(Task task) throws BusinessException {
		User u = Jpa.getManager().find(User.class, task.getUser().getId());
		BusinessCheck.isNotNull(u, "The user of the task does not exist");
	}

	public static void userIsNotDisabled(Task task) throws BusinessException {
		User u = Jpa.getManager().find(User.class, task.getUser().getId());
		BusinessCheck.isTrue(u.getStatus().equals(UserStatus.ENABLED),
				"The user of the task is disabled");
	}

	public static void userIsNotAdmin(Task task) throws BusinessException {
		User u = Jpa.getManager().find(User.class, task.getUser().getId());
		BusinessCheck.isFalse(u.isAdmin(),
				"The user of the task cannot be an admin");
	}

	public static void titleIsNotNull(Task task) throws BusinessException {
		BusinessCheck.isTrue(task.getTitle() != null,
				"The title of the task is cannot be null");
	}

	public static void titleIsNotEmpty(Task task) throws BusinessException {
		BusinessCheck.isTrue(!"".equals(task.getTitle()),
				"The title of the task is cannot be empty");
	}
}