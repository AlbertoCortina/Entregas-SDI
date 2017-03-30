package com.sdi.persistence;

public interface PersistenceFactory {

	public Transaction newTransaction();
	
	public UserDao getUserDao();

	public TaskDao getTaskDao();

	public  CategoryDao getCategoryDao();
	
}