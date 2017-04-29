package com.sdi.business;

import java.util.List;

import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Task;

public interface TaskService {

	public Long createTask(Task task) throws BusinessException;
	public void save(Task task) throws BusinessException;
	public void deleteTask(Long id) throws BusinessException;
	public void deleteAll() throws BusinessException;
	public void markTaskAsFinished(Long id) throws BusinessException;
	public void updateTask(Task task) throws BusinessException;
	public Task findTaskById(Long id) throws BusinessException;

	public List<Task> findInboxTasksByUserId(Long id) throws BusinessException;
	public List<Task> findWeekTasksByUserId(Long id) throws BusinessException;
	public List<Task> findTodayTasksByUserId(Long id) throws BusinessException;
	public List<Task> findTasksByCategoryId(Long catId) throws BusinessException;
	public List<Task> findFinishedTasksByCategoryId(Long catId) throws BusinessException;
	public List<Task> findFinishedInboxTasksByUserId(Long userId) throws BusinessException;
	public List<Task> findAll() throws BusinessException;
	public List<Task> findByUserId(Long id) throws BusinessException;
	public List<Task> findPendingAndDelayed(Long userId, String categoryName) throws BusinessException;
}