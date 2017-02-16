package uo.sdi.business;

import uo.sdi.business.impl.admin.AdminServiceImpl;
import uo.sdi.business.impl.category.CategoryServiceImpl;
import uo.sdi.business.impl.task.TaskServiceImpl;
import uo.sdi.business.impl.user.UserServiceImpl;

public class Services {

	public static AdminService getAdminService() {
		return new AdminServiceImpl();
	}

	public static UserService getUserService() {
		return new UserServiceImpl();
	}

	public static TaskService getTaskService() {
		return new TaskServiceImpl();
	}
	
	public static CategoryService getCategoryService() {
		return new CategoryServiceImpl();
	}
}