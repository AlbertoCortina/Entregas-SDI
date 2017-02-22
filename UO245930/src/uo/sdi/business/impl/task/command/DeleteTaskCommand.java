package uo.sdi.business.impl.task.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.Task;
import uo.sdi.persistence.util.Jpa;

public class DeleteTaskCommand implements Command<Void> {

	private Long id;

	public DeleteTaskCommand(Long id) {
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {		
		Task t = Jpa.getManager().find(Task.class, id);
		BusinessCheck.isNotNull(t, "Task does not exist");
		
		t.desvincularTarea();

		Jpa.getManager().remove(t);
		return null;
	}
}