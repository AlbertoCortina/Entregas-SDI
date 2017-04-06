package com.sdi.business;

public interface ServicesFactory {
	
	public AdminService getAdminService();

	public UserService getUserService();

	public TaskService getTaskService();
	
	public CategoryService getCategoryService();
	
}