package com.sdi.business.impl.category;

import java.util.List;

import javax.ejb.Stateless;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.category.command.CreateCategoryCommand;
import com.sdi.business.impl.category.command.DeleteCategoryCommand;
import com.sdi.business.impl.category.command.DuplicateCategoryCommand;
import com.sdi.business.impl.category.command.UpdateCategoryCommand;
import com.sdi.dto.Category;
import com.sdi.infrastructure.Factories;

@Stateless
public class EjbCategoryService implements LocalCategoryService, RemoteCategoryService {

	@Override
	public Long createCategory(Category category) throws BusinessException {		
		return new CreateCategoryCommand(category).execute();	
	}

	@Override
	public Long duplicateCategory(Long id) throws BusinessException {
		return new DuplicateCategoryCommand(id).execute();
	}

	@Override
	public void updateCategory(Category category) throws BusinessException {
		new UpdateCategoryCommand( category ).execute();		
	}

	@Override
	public void deleteCategory(Long catId) throws BusinessException {
		new DeleteCategoryCommand(catId).execute();
	}
	
	@Override
	public void deleteAll() throws BusinessException {
		Factories.persistence.getCategoryDao().deleteAll();
	}

	@Override
	public Category findCategoryById(final Long id) throws BusinessException {
		return Factories.persistence.getCategoryDao().findById(id);
	}
	
	@Override
	public Category findByUserIdAndName(final Long id, final String name) throws BusinessException {
		return Factories.persistence.getCategoryDao().findByUserIdAndName(id, name);
	}

	@Override
	public List<Category> findCategoriesByUserId(final Long id) throws BusinessException {
		return Factories.persistence.getCategoryDao().findByUserId(id);
	}
	
	@Override
	public List<Category> findAll() throws BusinessException {
		return Factories.persistence.getCategoryDao().findAll();
	}
	
	@Override
	public void save(final Category category) throws BusinessException {
		Factories.persistence.getCategoryDao().save(category);
	}
}