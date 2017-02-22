package uo.sdi.business.impl.task.command;

import alb.util.date.DateUtil;
import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.Task;
import uo.sdi.persistence.util.Jpa;

public class MarkTaskAsFinishedCommand implements Command<Void> {

	private Long id;

	public MarkTaskAsFinishedCommand(Long id) {
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		Task t = Jpa.getManager().find(Task.class, id);
		BusinessCheck.isNotNull(t, "The task does not exist");
		BusinessCheck.isNull(t.getFinished(), "La tarea ya está finalizada");
		t.setFinished(DateUtil.today());
		
		return null;
	}
}