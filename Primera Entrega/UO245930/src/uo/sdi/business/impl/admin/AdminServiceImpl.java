package uo.sdi.business.impl.admin;

import java.util.List;
import uo.sdi.business.AdminService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.admin.command.DeepDeleteUserCommand;
import uo.sdi.business.impl.admin.command.DisableUserCommand;
import uo.sdi.business.impl.admin.command.EnableUserCommand;
import uo.sdi.business.impl.admin.command.FindAllUsersCommand;
import uo.sdi.business.impl.admin.command.FindUserByIdCommand;
import uo.sdi.business.impl.command.CommandExecutor;
import uo.sdi.model.User;

public class AdminServiceImpl implements AdminService {

	@Override
	public void deepDeleteUser(Long id) throws BusinessException {
		new CommandExecutor<Void>().execute(new DeepDeleteUserCommand(id));
	}

	@Override
	public void disableUser(Long id) throws BusinessException {
		new CommandExecutor<Void>().execute(new DisableUserCommand(id));
	}

	@Override
	public void enableUser(Long id) throws BusinessException {
		new CommandExecutor<Void>().execute(new EnableUserCommand(id));
	}

	@Override
	public List<User> findAllUsers() throws BusinessException {
		return new CommandExecutor<List<User>>()
				.execute(new FindAllUsersCommand());
	}

	@Override
	public User findUserById(Long id) throws BusinessException {
		return new CommandExecutor<User>().execute(new FindUserByIdCommand(id));
	}
}