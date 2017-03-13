package com.sdi.business;

import java.util.List;

import com.sdi.business.exception.BusinessException;
import com.sdi.dto.User;

public interface UserService {

	public Long registerUser(User user) throws BusinessException;
	public void updateUserDetails(User user) throws BusinessException;	
	public User findLoggableUser(String login, String password) throws BusinessException;
	public void deleteAll() throws BusinessException;
	public void save(User user) throws BusinessException;
	public List<User> findAll() throws BusinessException;
	
}