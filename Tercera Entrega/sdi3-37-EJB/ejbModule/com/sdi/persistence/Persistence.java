package com.sdi.persistence;

import com.sdi.persistence.impl.CategoryDaoJdbcImpl;
import com.sdi.persistence.impl.TaskDaoJdbcImpl;
import com.sdi.persistence.impl.UserDaoJdbcImpl;

public class Persistence implements PersistenceFactory {
	
	public UserDao getUserDao() {
		return new UserDaoJdbcImpl();
	}

	public TaskDao getTaskDao() {
		return new TaskDaoJdbcImpl();
	}

	public CategoryDao getCategoryDao() {
		return new CategoryDaoJdbcImpl();
	}
}