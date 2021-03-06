package uo.sdi.business;

import java.util.List;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.User;

public interface UserService {

	public void registerUser(User user) throws BusinessException;

	public void updateUserDetails(User user) throws BusinessException;

	public User findLoggableUser(String login, String password)
			throws BusinessException;

	public List<User> findAll() throws BusinessException;
}