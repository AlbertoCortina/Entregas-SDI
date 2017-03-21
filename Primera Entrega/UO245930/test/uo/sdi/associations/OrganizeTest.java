package uo.sdi.associations;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.*;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.*;

public class OrganizeTest {

	private User user;
	private Category category;
	
	@Before
	public void setUp() throws BusinessException {
		user = new User("user de prueba");
		category = new Category("category de prueba");
		Association.Organize.link(user, category);
	}
	
	@Test
	public void testOrganizeAdd() throws BusinessException {
		assertTrue( user.getCategories().contains( category ));
		assertTrue( category.getUser() == user );
	}

	@Test
	public void testOrganizeRemove() throws BusinessException {
		Association.Organize.unlink(user, category);

		assertTrue( ! user.getCategories().contains( category ));
		assertTrue( category.getUser() == null );
	}

	@Test
	public void testSafeReturn() throws BusinessException {
		Set<Category> categories = user.getCategories();
		categories.remove( category );

		assertTrue( categories.size() == 0 );
		assertTrue( "Se debe retornar copia de la coleccion o hacerla de solo lectura", 
			user.getCategories().size() == 1
		);
	}
}