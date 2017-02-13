package uo.sdi.persistence;

import java.util.List;

import uo.sdi.model.Task;
import uo.sdi.persistence.util.Jpa;

public class TaskFinder {

	// public static void insert (Task task) {
	// Jpa.getManager().persist(task);
	// }
	//
	// public static void update (Task task) {
	// Jpa.getManager().merge(task);
	// }
	//
	// public static void delete (Task task) {
	// Task aux = findById(task.getId());
	// Jpa.getManager().remove(aux);
	// }

	public static void deleteByUserId(Long userId) {

	}

	public static void deleteByCategoryId(Long categoryId) {

	}

	// public static Task findyById (Long id) {
	// return Jpa.getManager().find(Task.class, id);
	// }

	public static List<Task> findAll() {
		return null;
	}

	public static List<Task> findByUserId(Long userId) {
		return null;
	}

	public static List<Task> findInboxByUserId(Long userId) {
		return null;
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