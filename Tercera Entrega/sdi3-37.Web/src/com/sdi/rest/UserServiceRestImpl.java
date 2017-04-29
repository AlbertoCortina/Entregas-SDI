package com.sdi.rest;

import java.util.List;

import com.sdi.business.CategoryService;
import com.sdi.business.TaskService;
import com.sdi.business.UserService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.dto.User;
import com.sdi.infrastructure.Factories;

public class UserServiceRestImpl implements UserServiceRest {

	CategoryService cService = Factories.services.getCategoryService();
	UserService uService = Factories.services.getUserService();
	TaskService tService = Factories.services.getTaskService();
	
	@Override
	public User login(String username, String password) throws BusinessException {
		return uService.findLoggableUser(username, password);
	}

	@Override
	public List<Category> getCategories(Long userId) throws BusinessException {
		return cService.findCategoriesByUserId(userId);
	}
	
	@Override
	public Task findTaskById(Long tareaId) throws BusinessException {
		return tService.findTaskById(tareaId);
	}	

	@Override
	public void markTaskAsFinished(Task task) throws BusinessException {
		tService.markTaskAsFinished(task.getId());
	}

	@Override
	public Category findByUserIdAndName(Long userId, String categoryName) throws BusinessException {
		return cService.findByUserIdAndName(userId, categoryName);
	}
	
	@Override
	public List<Task> findPendingAndDelayed(Long userId, String categoryName) throws BusinessException {
		return tService.findPendingAndDelayed(userId, categoryName);
	}

	@Override
	public void newTask(Task task) throws BusinessException {		
		tService.createTask(task);
	}
}