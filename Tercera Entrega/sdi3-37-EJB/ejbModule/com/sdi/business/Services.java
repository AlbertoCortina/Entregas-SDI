package com.sdi.business;

import com.sdi.business.impl.admin.EjbAdminService;
import com.sdi.business.impl.category.EjbCategoryService;
import com.sdi.business.impl.task.EjbTaskService;
import com.sdi.business.impl.user.EjbUserService;

public class Services implements ServicesFactory {

	public AdminService getAdminService() {
		return new EjbAdminService();
	}

	public UserService getUserService() {
		return new EjbUserService();
	}

	public TaskService getTaskService() {
		return new EjbTaskService();
	}
	
	public CategoryService getCategoryService() {
		return new EjbCategoryService();
	}
}