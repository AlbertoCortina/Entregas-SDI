package com.sdi.business.impl.task;

import java.util.List;

import com.sdi.business.TaskService;
import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.command.Command;
import com.sdi.business.impl.command.CommandExecutor;
import com.sdi.business.impl.task.command.CreateTaskCommand;
import com.sdi.business.impl.task.command.MarkTaskAsFinishedCommand;
import com.sdi.business.impl.task.command.UpdateTaskCommand;
import com.sdi.dto.Task;
import com.sdi.persistence.Persistence;

public class TaskServiceImpl implements TaskService {	

	@Override
	public Long createTask(Task task) throws BusinessException {
		return new CommandExecutor<Long>().execute( 
				new CreateTaskCommand( task )
			);
	}
	
	@Override
	public void save(final Task task) throws BusinessException {
		new CommandExecutor<Void>().execute( new Command<Void>() {
			@Override
			public Void execute() throws BusinessException {
				Persistence.getTaskDao().save(task);
				return null;
			}			
		});
	}

	@Override
	public void deleteTask(final Long id) throws BusinessException {
		new CommandExecutor<Void>().execute( new Command<Void>() {
			@Override
			public Void execute() throws BusinessException {
				Persistence.getTaskDao().delete(id);
				return null;
			}
		}); 
	}
	
	@Override
	public void deleteAll() throws BusinessException {
		new CommandExecutor<Void>().execute( new Command<Void>() {
			@Override
			public Void execute() throws BusinessException {
				Persistence.getTaskDao().deleteAll();
				return null;
			}			
		});
	}

	@Override
	public void markTaskAsFinished(Long id) throws BusinessException {
		new CommandExecutor<Void>().execute( 
				new MarkTaskAsFinishedCommand( id )
			);
	}

	@Override
	public void updateTask(Task task) throws BusinessException {
		new CommandExecutor<Void>().execute( 
				new UpdateTaskCommand( task )
			);
	}

	@Override
	public Task findTaskById(final Long id) throws BusinessException {
		return new CommandExecutor<Task>().execute( new Command<Task>() {
			@Override public Task execute() throws BusinessException {
				
				return Persistence.getTaskDao().findById(id);
			}
		});
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
	
	@Override
	public List<Task> findAll() throws BusinessException {
		return new CommandExecutor<List<Task>>().execute( new Command<List<Task>>() {
			@Override public List<Task> execute() throws BusinessException {				
				return Persistence.getTaskDao().findAll();
			}
		});
	}
	
	@Override
	public List<Task> findByUserId(final Long id) throws BusinessException {
		return new CommandExecutor<List<Task>>().execute( new Command<List<Task>>() {
			@Override
			public List<Task> execute() throws BusinessException {
				return Persistence.getTaskDao().findByUserId(id);
			}			
		});
	}

}
