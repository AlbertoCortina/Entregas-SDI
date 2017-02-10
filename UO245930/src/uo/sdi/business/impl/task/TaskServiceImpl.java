package uo.sdi.business.impl.task;

import java.util.List;

import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.category.command.CreateCategoryCommand;
import uo.sdi.business.impl.category.command.DeleteCategoryCommand;
import uo.sdi.business.impl.category.command.DuplicateCategoryCommand;
import uo.sdi.business.impl.category.command.UpdateCategoryCommand;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.command.CommandExecutor;
import uo.sdi.business.impl.task.command.CreateTaskCommand;
import uo.sdi.business.impl.task.command.DeleteTaskCommand;
import uo.sdi.business.impl.task.command.FindTaskByIdCommand;
import uo.sdi.business.impl.task.command.MarkTaskAsFinishedCommand;
import uo.sdi.business.impl.task.command.UpdateTaskCommand;
import uo.sdi.model.*;
import uo.sdi.persistence.util.Jpa;

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
	public List<Task> findInboxTasksByUserId(final Long id) throws BusinessException {
		return new CommandExecutor<List<Task>>().execute( new Command<List<Task>>() {
			@Override public List<Task> execute() throws BusinessException {
				
				return Persistence.getTaskDao().findInboxTasksByUserId(id);
			}
		});
	}

	@Override
	public List<Task> findWeekTasksByUserId(final Long id) throws BusinessException {
		return new CommandExecutor<List<Task>>().execute( new Command<List<Task>>() {
			@Override public List<Task> execute() throws BusinessException {
				
				return Persistence.getTaskDao().findWeekTasksByUserId(id);
			}
		});
	}

	@Override
	public List<Task> findTodayTasksByUserId(final Long id) throws BusinessException {
		return new CommandExecutor<List<Task>>().execute( new Command<List<Task>>() {
			@Override public List<Task> execute() throws BusinessException {
				
				return Persistence.getTaskDao().findTodayTasksByUserId(id);
			}
		});
	}

	@Override
	public List<Task> findTasksByCategoryId(final Long id) throws BusinessException {
		return new CommandExecutor<List<Task>>().execute( new Command<List<Task>>() {
			@Override public List<Task> execute() throws BusinessException {
				
				return Persistence.getTaskDao().findTasksByCategoryId(id);
			}
		});
	}

	@Override
	public List<Task> findFinishedTasksByCategoryId(final Long id) throws BusinessException {
		return new CommandExecutor<List<Task>>().execute( new Command<List<Task>>() {
			@Override public List<Task> execute() throws BusinessException {
				
				return Persistence.getTaskDao().findFinishedTasksByCategoryId(id);
			}
		});
	}

	@Override
	public List<Task> findFinishedInboxTasksByUserId(final Long id) throws BusinessException {
		return new CommandExecutor<List<Task>>().execute( new Command<List<Task>>() {
			@Override public List<Task> execute() throws BusinessException {
				
				return Persistence.getTaskDao().findFinishedTasksInboxByUserId(id);
			}
		});
	}
}