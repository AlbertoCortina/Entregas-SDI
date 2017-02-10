package uo.sdi.associations;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.*;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.*;

public class ClassifyTest {

	private Task task;
	private Category category;
	
	@Before
	public void setUp() throws BusinessException {
		category = new Category("category de prueba");
		task = new Task("task de prueba", null);
		Association.Classify.link(category, task);
	}
	
	@Test
	public void testClassifyAdd() throws BusinessException {
		assertTrue( category.getTasks().contains( task ));
		assertTrue( task.getCategory() == category );
	}

	@Test
	public void testPoseerRemove() throws BusinessException {
		Association.Classify.unlink(category, task);

		assertTrue( ! category.getTasks().contains( task ));
		assertTrue( task.getCategory() == null );
	}

	@Test
	public void testSafeReturn() throws BusinessException {
		Set<Task> tasks = category.getTasks();
		tasks.remove( task );

		assertTrue( tasks.size() == 0 );
		assertTrue( "Se debe retornar copia de la coleccion o hacerla de solo lectura", 
			category.getTasks().size() == 1
		);
	}
}