package com.sdi.rest;

import java.util.List;

import com.sdi.business.CategoryService;
import com.sdi.business.TaskService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.infrastructure.Factories;

public class AdminServicesRestImpl implements AdminServicesRest{

	CategoryService catService = Factories.services.getCategoryService();
	TaskService taskService = Factories.services.getTaskService();
	
	@Override
	public List<Category> getCategories(Long id) throws BusinessException {
		return catService.findCategoriesByUserId(id);
	}

	@Override
	public List<Task> getTaksByCategoryId(Long id) throws BusinessException {
		return taskService.findTasksByCategoryId(id);
	}

	@Override
	public void markTaskAsComplete(Task task) throws BusinessException {
		taskService.updateTask(task);
	}

	@Override
	public void newTask(Task task) throws BusinessException {
		taskService.save(task);
	}

}
