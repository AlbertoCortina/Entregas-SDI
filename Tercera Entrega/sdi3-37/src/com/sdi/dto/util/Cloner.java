package com.sdi.dto.util;

import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.dto.User;


public class Cloner {

	public static User clone(User u) {
		User user = new User();
		user.setId( 		u.getId() );
		user.setEmail( 		u.getEmail() );
		user.setIsAdmin(	u.getIsAdmin() );
		user.setLogin( 		u.getLogin() );
		user.setPassword( 	u.getPassword() );
		user.setStatus( 	u.getStatus() );
		return user;
	}
	
	public static Task clone(Task t) {
		Task t2 = new Task();		
	
		t2.setCategoryId( t.getCategoryId() );
		t2.setComments( 	t.getComments() );
		t2.setCreated( 	t.getCreated() );
		t2.setFinished( 	t.getFinished() );
		t2.setId( 		t.getId() );
		t2.setPlanned( 	t.getPlanned() );
		t2.setTitle( 		t.getTitle() );
		t2.setUserId( 	t.getUserId() );
		
		return t2;
	}

	public static Category clone(Category c) {
		Category c2 = new Category();
		
		c2.setName( 	c.getName() );
		c2.setUserId( c.getUserId() );
		
		return c2;
	}

}
