package uo.sdi.business.impl.category.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.Category;
import uo.sdi.persistence.util.Jpa;

public class FindCategoryById implements Command<Category> {

	private Long id;

	public FindCategoryById(Long id) {
		this.id = id;
	}

	@Override
	public Category execute() throws BusinessException {
		Category c = Jpa.getManager().find(Category.class, id);
		BusinessCheck.isNotNull(c, "Category does not exist");

		return c;
	}
}