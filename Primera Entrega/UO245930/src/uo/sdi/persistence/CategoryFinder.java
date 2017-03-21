package uo.sdi.persistence;

import java.util.List;

import uo.sdi.model.Category;
import uo.sdi.persistence.util.Jpa;

public class CategoryFinder {

	public static void deleteByUserId(Long userId) {
		Jpa.getManager()
				.createNamedQuery("Category.deleteByUserId", Category.class)
				.setParameter(1, userId);
	}

	public static List<Category> findAll() {
		return Jpa.getManager()
				.createNamedQuery("Category.findAll", Category.class)
				.getResultList();
	}

	public static List<Category> findByUserId(Long userId) {
		return Jpa.getManager()
				.createNamedQuery("Category.findByUserId", Category.class)
				.setParameter(1, userId).getResultList();
	}

	public static Category findByUserIdAndName(Long userId, String name) {
		List<Category> categorias = Jpa
				.getManager()
				.createNamedQuery("Category.findByUserIdAndName", Category.class)
				.setParameter(1, userId)
				.setParameter(2, name).getResultList();

		return categorias.isEmpty() ? null : categorias.get(0);
	}
}