package uo.sdi.business.impl.task.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.TaskCheck;
import uo.sdi.model.Task;
import uo.sdi.persistence.util.Jpa;

public class UpdateTaskCommand implements Command<Void> {

	private Task task;

	public UpdateTaskCommand(Task task) {
		this.task = task;
	}

	@Override
	public Void execute() throws BusinessException {
		TaskCheck.titleIsNotNull(task);
		TaskCheck.titleIsNotEmpty(task);
		if ( task.getCategory().getId() != null) {
			TaskCheck.categoryExists( task );
		}
		
		Task previous = Jpa.getManager().find(Task.class, task);
		BusinessCheck.isNotNull(previous, "Task does not exist");
		checktaskAlreadyExist(previous);
		checkUserNotChanged(previous);
		
		task.setCreated( previous.getCreated() ); // change ignored
		Jpa.getManager().merge(task);
		return null;
	}

	private void checktaskAlreadyExist(Task previous) throws BusinessException {
		BusinessCheck.isNotNull( previous, "The task does not exist");
	}

	private void checkUserNotChanged(Task previous) throws BusinessException {
		BusinessCheck.isTrue( task.getUser().getId().equals( previous.getUser().getId()),
			"A task cannot be changed to other user");
	}
}