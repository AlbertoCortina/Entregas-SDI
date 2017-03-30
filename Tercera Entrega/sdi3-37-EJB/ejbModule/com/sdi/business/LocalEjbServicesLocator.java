package com.sdi.business;

import javax.naming.*;

public class LocalEjbServicesLocator implements ServicesFactory {

	private static final String ADMIN_SERVICE_JNDI_KEY = "java:global/sdi3-37-EAR/sdi3-37-EJB/EjbAdminService!com.sdi.business.remoteServices.RemoteAdminService";
	private static final String CATEGORY_SERVICE_JNDI_KEY = "java:global/sdi3-37-EAR/sdi3-37-EJB/EjbCategoryService!com.sdi.business.remoteServices.RemoteCategoryService";
	private static final String TASK_SERVICE_JNDI_KEY = "java:global/sdi3-37-EAR/sdi3-37-EJB/EjbTaskService!com.sdi.business.localServices.LocalTaskService";
	private static final String USER_SERVICE_JNDI_KEY = "java:global/sdi3-37-EAR/sdi3-37-EJB/EjbUserService!com.sdi.business.localServices.LocalUserService";
	
	@Override
	public AdminService getAdminService() {
		try {
			Context ctx = new InitialContext();
			return (AdminService) ctx.lookup(ADMIN_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}

	@Override
	public UserService getUserService() {
		try {
			Context ctx = new InitialContext();
			return (UserService) ctx.lookup(USER_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}

	@Override
	public TaskService getTaskService() {
		try {
			Context ctx = new InitialContext();
			return (TaskService) ctx.lookup(TASK_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}

	@Override
	public CategoryService getCategoryService() {
		try {
			Context ctx = new InitialContext();
			return (CategoryService) ctx.lookup(CATEGORY_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}
}