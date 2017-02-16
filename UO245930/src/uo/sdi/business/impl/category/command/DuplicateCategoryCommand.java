package uo.sdi.business.impl.category.command;

import java.util.List;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.*;
import uo.sdi.persistence.util.Jpa;

public class DuplicateCategoryCommand implements Command<Void> {

	private Long origId;

	public DuplicateCategoryCommand(Long id) {
		this.origId = id;
	}

	@Override
	public Void execute() throws BusinessException {
//		Long copyId = duplicateCategory(origId);
//		duplicateTasks(origId, copyId);

		return null;
	}

//	private Long duplicateCategory(Long id) throws BusinessException {
//		Category original = Jpa.getManager().find(Category.class, id);
//		BusinessCheck.isNotNull(original, "The category does not exist");
//		checkUserNotDisabled(original);
//
//		Category copy = Cloner.clone(original);
//		copy.setName(copy.getName() + " - copy");
//		return cDao.save(copy);
//	}

//	private void checkUserNotDisabled(Category c) throws BusinessException {
//		User u = Jpa.getManager().find(User.class, c.getUser().getId());
//		BusinessCheck.isTrue(u.getStatus().equals(UserStatus.ENABLED),
//				"User disables, category cannot be duplicated.");
//	}

//	private void duplicateTasks(Long catId, Long copyId) {
//		TaskDao tDao = Persistence.getTaskDao();
//
//		List<Task> tasks = tDao.findTasksByCategoryId(catId);
//		for (Task t : tasks) {
//			Task copy = Cloner.clone(t).setCategoryId(copyId);
//			tDao.save(copy);
//		}
//	}
}