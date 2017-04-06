package com.sdi.business.impl.task;

import java.util.List;

import javax.ejb.Stateless;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.task.command.CreateTaskCommand;
import com.sdi.business.impl.task.command.MarkTaskAsFinishedCommand;
import com.sdi.business.impl.task.command.UpdateTaskCommand;
import com.sdi.dto.Task;
import com.sdi.infrastructure.Factories;

@Stateless
public class EjbTaskService implements LocalTaskService, RemoteTaskService {	

	@Override
	public Long createTask(Task task) throws BusinessException {
		return new CreateTaskCommand(task).execute();
	}
	
	@Override
	public void save(final Task task) throws BusinessException {
		Factories.persistence.getTaskDao().save(task);
	}

	@Override
	public void deleteTask(final Long id) throws BusinessException {
		Factories.persistence.getTaskDao().delete(id);
	}
	
	@Override
	public void deleteAll() throws BusinessException {
		Factories.persistence.getTaskDao().deleteAll();
	}

	@Override
	public void markTaskAsFinished(Long id) throws BusinessException {
		new MarkTaskAsFinishedCommand(id).execute();
	}

	@Override
	public void updateTask(Task task) throws BusinessException {
		new UpdateTaskCommand(task).execute();
	}

	@Override
	public Task findTaskById(final Long id) throws BusinessException {
		return Factories.persistence.getTaskDao().findById(id);
	}

	@Override
	public List<Task> findInboxTasksByUserId(final Long id) throws BusinessException {
		return Factories.persistence.getTaskDao().findInboxTasksByUserId(id);
	}

	@Override
	public List<Task> findWeekTasksByUserId(final Long id) throws BusinessException {
		return Factories.persistence.getTaskDao().findWeekTasksByUserId(id);
	}

	@Override
	public List<Task> findTodayTasksByUserId(final Long id) throws BusinessException {
		return Factories.persistence.getTaskDao().findTodayTasksByUserId(id);
	}

	@Override
	public List<Task> findTasksByCategoryId(final Long id) throws BusinessException {
		return Factories.persistence.getTaskDao().findTasksByCategoryId(id);
	}

	@Override
	public List<Task> findFinishedTasksByCategoryId(final Long id) throws BusinessException {
		return Factories.persistence.getTaskDao().findFinishedTasksByCategoryId(id);
	}

	@Override
	public List<Task> findFinishedInboxTasksByUserId(final Long id) throws BusinessException {
		return Factories.persistence.getTaskDao().findFinishedTasksInboxByUserId(id);
	}
	
	@Override
	public List<Task> findAll() throws BusinessException {
		return Factories.persistence.getTaskDao().findAll();
	}
	
	@Override
	public List<Task> findByUserId(final Long id) throws BusinessException {
		return Factories.persistence.getTaskDao().findByUserId(id);
	}
}