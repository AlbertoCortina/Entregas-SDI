package uo.sdi.business;

import java.util.List;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.Category;

public interface CategoryService {

	public void createCategory(Category category) throws BusinessException;

	public void duplicateCategory(Long id) throws BusinessException;

	public void updateCategory(Category category) throws BusinessException;

	public void deleteCategory(Long id) throws BusinessException;

	public Category findCategoryById(Long id) throws BusinessException;

	public List<Category> findCategoriesByUserId(Long id)
			throws BusinessException;

}