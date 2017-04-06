package com.sdi.persistence;

public interface PersistenceFactory {
	
	public UserDao getUserDao();

	public TaskDao getTaskDao();

	public  CategoryDao getCategoryDao();
	
}