package uo.sdi.business.impl.admin.command;

import java.util.List;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.User;
import uo.sdi.persistence.UserFinder;

public class FindAllUsersCommand implements Command<List<User>> {

	public FindAllUsersCommand () {		
	}
	
	@Override
	public List<User> execute() throws BusinessException {
		return UserFinder.findAll();
	}	
}