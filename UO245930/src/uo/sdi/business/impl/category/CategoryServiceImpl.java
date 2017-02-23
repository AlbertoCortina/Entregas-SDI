package uo.sdi.business.impl.category;

import java.util.List;

import uo.sdi.business.CategoryService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.category.command.CreateCategoryCommand;
import uo.sdi.business.impl.category.command.DeleteCategoryCommand;
import uo.sdi.business.impl.category.command.FindCategoryById;
import uo.sdi.business.impl.category.command.FindCategoryByUserId;
import uo.sdi.business.impl.category.command.UpdateCategoryCommand;
import uo.sdi.business.impl.command.CommandExecutor;
import uo.sdi.model.Category;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public void createCategory(Category category) throws BusinessException {
		new CommandExecutor<Void>()
				.execute(new CreateCategoryCommand(category));
	}

	@Override
	public void duplicateCategory(Long id) throws BusinessException {
		// new CommandExecutor<Long>().execute(new
		// DuplicateCategoryCommand(id));
	}

	@Override
	public void updateCategory(Category category) throws BusinessException {
		new CommandExecutor<Void>()
				.execute(new UpdateCategoryCommand(category));
	}

	@Override
	public void deleteCategory(Long catId) throws BusinessException {
		new CommandExecutor<Void>().execute(new DeleteCategoryCommand(catId));
	}

	@Override
	public Category findCategoryById(Long id) throws BusinessException {
		return new CommandExecutor<Category>()
				.execute(new FindCategoryById(id));
	}

	@Override
	public List<Category> findCategoriesByUserId(final Long id)
			throws BusinessException {
		return new CommandExecutor<List<Category>>()
				.execute(new FindCategoryByUserId(id));
	}
}