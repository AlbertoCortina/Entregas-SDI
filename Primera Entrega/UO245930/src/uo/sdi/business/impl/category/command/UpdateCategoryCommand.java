package uo.sdi.business.impl.category.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.CategoryCheck;
import uo.sdi.model.Category;
import uo.sdi.persistence.util.Jpa;

public class UpdateCategoryCommand implements Command<Void> {

	private Category category;

	public UpdateCategoryCommand(Category category) {
		this.category = category;
	}

	@Override
	public Void execute() throws BusinessException {
		Category previous = Jpa.getManager().find(Category.class,
				category.getId());

		checkCategoryExists(previous);
		CategoryCheck.nameIsNotNull(category);
		CategoryCheck.nameIsNotEmpty(category);
		if (nameIsChanged(previous, category)) {
			CategoryCheck.isUniqueName(category);
		}
		checkUserIsNotChanged(previous, category);

		Jpa.getManager().merge(previous);
		return null;
	}

	private void checkUserIsNotChanged(Category previous, Category current)
			throws BusinessException {
		BusinessCheck.isTrue(
				previous.getUser().getId().equals(current.getUser().getId()),
				"A category cannot be changed to other user");
	}

	private boolean nameIsChanged(Category previous, Category current) {
		return !previous.getName().equals(current.getName());
	}

	private void checkCategoryExists(Category c) throws BusinessException {
		BusinessCheck.isNotNull(c, "The category does not exist");
	}
}