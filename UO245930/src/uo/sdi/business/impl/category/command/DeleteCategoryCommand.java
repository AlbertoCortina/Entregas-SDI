package uo.sdi.business.impl.category.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.model.Category;
import uo.sdi.persistence.TaskFinder;
import uo.sdi.persistence.util.Jpa;

public class DeleteCategoryCommand implements Command<Void> {

	private Long catId;

	public DeleteCategoryCommand(Long catId) {
		this.catId = catId;
	}

	@Override
	public Void execute() throws BusinessException {
		// Comprobamos que exista la categoría
		Category c = Jpa.getManager().find(Category.class, catId);
		BusinessCheck.isNotNull(c, "Category does not exist");

		TaskFinder.deleteByCategoryId(catId);
		Jpa.getManager().remove(catId);

		return null;
	}
}