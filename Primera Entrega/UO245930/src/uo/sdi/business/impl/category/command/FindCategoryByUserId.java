package uo.sdi.business.impl.category.command;

import java.util.List;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.Category;
import uo.sdi.persistence.CategoryFinder;

public class FindCategoryByUserId implements Command<List<Category>> {

	private Long id;

	public FindCategoryByUserId(Long id) {
		this.id = id;
	}

	@Override
	public List<Category> execute() throws BusinessException {
		return CategoryFinder.findByUserId(id);
	}
}