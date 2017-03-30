package com.sdi.business;

import java.util.List;

import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Category;

public interface CategoryService {

	public Long createCategory(Category category) throws BusinessException;
	public Long duplicateCategory(Long id) throws BusinessException;
	public void updateCategory(Category category) throws BusinessException;
	public void deleteCategory(Long id) throws BusinessException;
	public void deleteAll() throws BusinessException;
	public Category findCategoryById(Long id) throws BusinessException;
	public Category findByUserIdAndName(Long id, String name) throws BusinessException;
	public List<Category> findCategoriesByUserId(Long id) throws BusinessException;
	public List<Category> findAll() throws BusinessException;
	public void save(Category category) throws BusinessException;
}