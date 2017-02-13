package uo.sdi.business.impl.task;

import java.util.List;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.CommandExecutor;
import uo.sdi.business.impl.task.command.CreateTaskCommand;
import uo.sdi.business.impl.task.command.DeleteTaskCommand;
import uo.sdi.business.impl.task.command.FindFinishedInboxTasksByUserId;
import uo.sdi.business.impl.task.command.FindFinishedTasksByCategoryId;
import uo.sdi.business.impl.task.command.FindInboxTasksByUserId;
import uo.sdi.business.impl.task.command.FindTaskByIdCommand;
import uo.sdi.business.impl.task.command.FindTodayTasksByUserId;
import uo.sdi.business.impl.task.command.FindUnfinishedTasksByCategoryId;
import uo.sdi.business.impl.task.command.FindWeekTasksByUserId;
import uo.sdi.business.impl.task.command.MarkTaskAsFinishedCommand;
import uo.sdi.business.impl.task.command.UpdateTaskCommand;
import uo.sdi.model.*;

public class TaskServiceImpl implements TaskService {

	@Override
	public void createTask(Task task) throws BusinessException {
		new CommandExecutor<Void>().execute(new CreateTaskCommand(task));
	}

	@Override
	public void deleteTask(Long id) throws BusinessException {
		new CommandExecutor<Void>().execute(new DeleteTaskCommand(id));
	}

	@Override
	public void markTaskAsFinished(Long id) throws BusinessException {
		new CommandExecutor<Void>().execute(new MarkTaskAsFinishedCommand(id));
	}

	@Override
	public void updateTask(Task task) throws BusinessException {
		new CommandExecutor<Void>().execute(new UpdateTaskCommand(task));
	}

	@Override
	public Task findTaskById(Long id) throws BusinessException {
		return new CommandExecutor<Task>().execute(new FindTaskByIdCommand(id));
	}

	@Override
	public List<Task> findInboxTasksByUserId(Long id) throws BusinessException {
		return new CommandExecutor<List<Task>>()
				.execute(new FindInboxTasksByUserId(id));
	}

	@Override
	public List<Task> findWeekTasksByUserId(Long id) throws BusinessException {
		return new CommandExecutor<List<Task>>()
				.execute(new FindWeekTasksByUserId(id));
	}

	@Override
	public List<Task> findTodayTasksByUserId(Long id) throws BusinessException {
		return new CommandExecutor<List<Task>>()
				.execute(new FindTodayTasksByUserId(id));
	}

	@Override
	public List<Task> findUnfinishedTasksByCategoryId(Long id)
			throws BusinessException {
		return new CommandExecutor<List<Task>>()
				.execute(new FindUnfinishedTasksByCategoryId(id));
	}

	@Override
	public List<Task> findFinishedTasksByCategoryId(final Long id)
			throws BusinessException {
		return new CommandExecutor<List<Task>>()
				.execute(new FindFinishedTasksByCategoryId(id));
	}

	@Override
	public List<Task> findFinishedInboxTasksByUserId(Long id)
			throws BusinessException {
		return new CommandExecutor<List<Task>>()
				.execute(new FindFinishedInboxTasksByUserId(id));
	}
}