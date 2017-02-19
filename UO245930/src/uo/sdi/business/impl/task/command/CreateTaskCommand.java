package uo.sdi.business.impl.task.command;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.TaskCheck;
import uo.sdi.model.Task;
import uo.sdi.persistence.util.Jpa;
import alb.util.date.DateUtil;

public class CreateTaskCommand implements Command<Void> {

	private Task task;

	public CreateTaskCommand(Task task) {
		this.task = task;
	}

	@Override
	public Void execute() throws BusinessException {
		TaskCheck.userExists(task);
		TaskCheck.userIsNotDisabled(task);
		TaskCheck.userIsNotAdmin(task);
		TaskCheck.titleIsNotNull(task);
		TaskCheck.titleIsNotEmpty(task);
		
		if (task.getCategory().getId() != null) {
			TaskCheck.categoryExists(task);
		}

		task.setCreated(DateUtil.today());
		task.setFinished(null);

		Jpa.getManager().persist(task);

		return null;
	}
}