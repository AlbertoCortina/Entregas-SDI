package uo.sdi.business.impl.admin.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.User;
import uo.sdi.persistence.*;
import uo.sdi.persistence.util.Jpa;

public class DeepDeleteUserCommand implements Command<Void> {

	private Long id;

	public DeepDeleteUserCommand(Long id) {
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		//Comprobamos que  exista el usuario
		User user = Jpa.getManager().find(User.class, id);
		BusinessCheck.isNotNull(user, "User does not exist");
		
		TaskFinder.deleteByUserId(id);
		CategoryFinder.deleteByUserId(id);
		Jpa.getManager().remove(user);
		
		return null;
	}
}