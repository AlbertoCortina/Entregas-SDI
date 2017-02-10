package uo.sdi.associations;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.*;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.*;

public class PerformTest {

	private User user;
	private Task task;
	
	@Before
	public void setUp() throws BusinessException {
		user = new User("user de prueba");
		task = new Task("task de prueba", null);
		Association.Perform.link(user, task);
	}
	
	@Test
	public void testPerformAdd() throws BusinessException {
		assertTrue( user.getTasks().contains( task ));
		assertTrue( task.getUser() == user );
	}

	@Test
	public void testPerformRemove() throws BusinessException {
		Association.Perform.unlink(user, task);

		assertTrue( ! user.getTasks().contains( task ));
		assertTrue( task.getUser() == null );
	}

	@Test
	public void testSafeReturn() throws BusinessException {
		Set<Task> tasks = user.getTasks();
		tasks.remove( task );

		assertTrue( tasks.size() == 0 );
		assertTrue( "Se debe retornar copia de la coleccion o hacerla de solo lectura", 
			user.getTasks().size() == 1
		);
	}
}