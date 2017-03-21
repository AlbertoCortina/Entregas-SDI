package uo.sdi.business.impl.task.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.TaskCheck;
import uo.sdi.model.Task;
import uo.sdi.model.User;
import uo.sdi.persistence.UserFinder;
import uo.sdi.persistence.util.Jpa;

public class CreateTaskCommand implements Command<Void> {

	private String nombre;
	private Long id;

	public CreateTaskCommand(String nombre, Long id) {
		this.nombre = nombre;
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		User user = UserFinder.findById(id);
		BusinessCheck.isNotNull(user, "El usuario no existe");

		Task task = new Task(user);
		task.setTitle(nombre);

		TaskCheck.userExists(task);
		TaskCheck.userIsNotDisabled(task);
		TaskCheck.userIsNotAdmin(task);
		TaskCheck.titleIsNotNull(task);
		TaskCheck.titleIsNotEmpty(task);

		Jpa.getManager().persist(task);

		return null;
	}
}