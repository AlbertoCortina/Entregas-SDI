package uo.sdi.persistence_jpa;

import java.util.List;

import uo.sdi.model.Category;
import uo.sdi.persistence_jpa.util.Jpa;

public class CategoryFinder {

//	public static void insert (Category category) {
//		Jpa.getManager().persist(category);
//	}
//	
//	public static void update (Category category) {
//		Jpa.getManager().merge(category);
//	}
//	
//	public static void delete (Category category) {
//		Category aux = findById(category.getId());
//		Jpa.getManager().remove(aux);
//	}
	
	public static void deleteByUserId (Long userId) {
		Category aux = findByUserId(userId);
		Jpa.getManager().remove(aux);
	}

//	public static Category findById (Long id) {
//		return Jpa.getManager().find(Category.class, id);
//	}
	
	public static List<Category> findAll () {
		return Jpa.getManager().createNamedQuery("Category.findAll", Category.class).getResultList();
	}
	
	public static Category findByUserId (Long userId) {
		return Jpa.getManager().createNamedQuery("Category.findByUserId", Category.class).setParameter(1, userId).getSingleResult();
	}
	
	public static Category findByUserIdAndName (Long userId, String name) {
		return Jpa.getManager().createNamedQuery("Category.findByUserIdAndName", Category.class).setParameter(1, userId).setParameter(3, name).getSingleResult();
	}	
}