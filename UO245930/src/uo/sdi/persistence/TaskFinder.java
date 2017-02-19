package uo.sdi.persistence;

import java.util.List;

import uo.sdi.model.Task;
import uo.sdi.persistence.util.Jpa;

public class TaskFinder {

	public static void deleteByUserId(Long userId) {
		Jpa.createEntityManager().createNamedQuery("Task.deleteByUserId")
				.setParameter(1, userId);
	}

	public static void deleteByCategoryId(Long categoryId) {
		Jpa.createEntityManager().createNamedQuery("Task.deleteByCategoryId")
				.setParameter(1, categoryId);
	}

	public static List<Task> findAll() {
		return Jpa.getManager().createNamedQuery("Task.findAll", Task.class)
				.getResultList();
	}

	public static List<Task> findByUserId(Long userId) {
		return Jpa.getManager()
				.createNamedQuery("Task.findByUserId", Task.class)
				.getResultList();
	}

	public static List<Task> findInboxByUserId(Long userId) {
		return Jpa.getManager()
				.createNamedQuery("Task.findInboxUserId", Task.class)
				.setParameter(1, userId).getResultList();
	}

	public static List<Task> findTodayByUserId(Long userId) {
		return null;
	}

	public static List<Task> findWeekByUserId(Long userId) {
		return null;
	}

	public static List<Task> findUnfinishedByCategoryId(Long categoryId) {
		return null;
	}

	public static List<Task> findFinishedByCategoryId(Long categoryId) {
		return null;
	}

	public static List<Task> findFinishedInboxByUserId(Long userId) {
		return null;
	}
}