package com.sdi.business.impl.category;

import java.util.List;

import com.sdi.business.CategoryService;
import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.category.command.CreateCategoryCommand;
import com.sdi.business.impl.category.command.DeleteCategoryCommand;
import com.sdi.business.impl.category.command.DuplicateCategoryCommand;
import com.sdi.business.impl.category.command.UpdateCategoryCommand;
import com.sdi.business.impl.command.Command;
import com.sdi.business.impl.command.CommandExecutor;
import com.sdi.dto.Category;
import com.sdi.persistence.Persistence;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public Long createCategory(Category category) throws BusinessException {
		return new CommandExecutor<Long>().execute( 
			new CreateCategoryCommand( category )
		);
	}

	@Override
	public Long duplicateCategory(Long id) throws BusinessException {
		return new CommandExecutor<Long>().execute( 
				new DuplicateCategoryCommand( id )
			);
	}

	@Override
	public void updateCategory(Category category) throws BusinessException {
		new CommandExecutor<Void>().execute( 
				new UpdateCategoryCommand( category )
			);
	}

	@Override
	public void deleteCategory(Long catId) throws BusinessException {
		new CommandExecutor<Void>().execute( 
				new DeleteCategoryCommand( catId )
			);
	}
	
	@Override
	public void deleteAll() throws BusinessException {
		new CommandExecutor<Void>().execute( new Command<Void>() {
			@Override
			public Void execute() throws BusinessException {
				Persistence.getCategoryDao().deleteAll();
				return null;
			}			
		});
	}

	@Override
	public Category findCategoryById(final Long id) throws BusinessException {
		return new CommandExecutor<Category>().execute( new Command<Category>() {
			@Override public Category execute() throws BusinessException {
				
				return Persistence.getCategoryDao().findById(id);
			}
		});
	}
	
	@Override
	public Category findByUserIdAndName(final Long id, final String name) throws BusinessException {
		return new CommandExecutor<Category>().execute( new Command<Category>() {
			@Override
			public Category execute() throws BusinessException {
				return Persistence.getCategoryDao().findByUserIdAndName(id, name);
			}			
		});
	}

	@Override
	public List<Category> findCategoriesByUserId(final Long id) throws BusinessException {
		return new CommandExecutor<List<Category>>().execute( new Command<List<Category>>() {
			@Override public List<Category> execute() throws BusinessException {
				
				return Persistence.getCategoryDao().findByUserId(id);
			}
		});
	}
	
	@Override
	public List<Category> findAll() throws BusinessException {
		return new CommandExecutor<List<Category>>().execute( new Command<List<Category>>() {
			@Override public List<Category> execute() throws BusinessException {				
				return Persistence.getCategoryDao().findAll();
			}
		});
	}
	
	@Override
	public void save(final Category category) throws BusinessException {
		new CommandExecutor<Void>().execute( new Command<Void>() {
			@Override
			public Void execute() throws BusinessException {
				Persistence.getCategoryDao().save(category);
				return null;
			}			
		});
	}
}
