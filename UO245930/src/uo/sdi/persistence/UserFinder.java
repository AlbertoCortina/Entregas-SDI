package uo.sdi.persistence;

import java.util.List;

import uo.sdi.model.User;
import uo.sdi.persistence.util.Jpa;

public class UserFinder {

	public static List<User> findAll() {
		return Jpa.getManager().createNamedQuery("User.findAll", User.class)
				.getResultList();
	}

	public static User findByLogin(String login) {
		List<User> users = Jpa.getManager()
				.createNamedQuery("User.findByLogin", User.class)
				.setParameter(1, login).getResultList();

		return users.isEmpty() ? null : users.get(0);
	}

	public static User findByLoginAndPassword(String login, String password) {
		List<User> users = Jpa.getManager()
				.createNamedQuery("User.findByLoginAndPassword", User.class)
				.setParameter(1, login).setParameter(2, password)
				.getResultList();
		return users.isEmpty() ? null : users.get(0);
	}
}