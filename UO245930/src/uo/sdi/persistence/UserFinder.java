package uo.sdi.persistence;

import java.util.List;

import uo.sdi.model.User;
import uo.sdi.persistence.util.Jpa;

public class UserFinder {

//	public static void insert (User user) {
//		Jpa.getManager().persist(user);
//	}
//	
//	public static void update (User user) {
//		Jpa.getManager().merge(user);
//	}
//	
//	public static void delete (User user) {
//		User aux = findById(user.getId());
//		Jpa.getManager().remove(aux);
//	}
//	
//	public static User findById (Long id) {
//		return Jpa.getManager().find(User.class, id);
//	}
	
	public static List<User> findAll () {
		return Jpa.getManager().createNamedQuery("User.findAll", User.class).getResultList();
	}
	
	public static User findByLogin (String login) {
		return Jpa.getManager().createNamedQuery("User.finByLogin", User.class).setParameter(1, login).getSingleResult();
	}
	
	public static User findByLoginAndPassword (String login, String password) {
		return Jpa.getManager().createNamedQuery("User.findByLoginAndPassword", User.class).setParameter(1, login).setParameter(2, password).getSingleResult();
	}
}