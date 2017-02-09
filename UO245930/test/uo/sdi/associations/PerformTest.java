package uo.sdi.associations;

import org.junit.*;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.*;

public class PerformTest {

	private User user;
	private Task task;
	
	@Before
	public void setUp() throws BusinessException {
		cliente = new Cliente("dni-cliente", "nombre", "apellidos");
		vehiculo = new Vehiculo("1234 GJI", "seat", "ibiza");
		Association.Poseer.link(cliente, vehiculo);
	}
	
	@Test
	public void testPerformAdd() throws BusinessException {
		assertTrue( cliente.getVehiculos().contains( vehiculo ));
		assertTrue( vehiculo.getCliente() == cliente );
	}

	@Test
	public void testPerformRemove() throws BusinessException {
		Association.Perform.unlink(cliente, vehiculo);

		assertTrue( ! cliente.getVehiculos().contains( vehiculo ));
		assertTrue( vehiculo.getCliente() == null );
	}

	@Test
	public void testSafeReturn() throws BusinessException {
		Set<Vehiculo> vehiculos = cliente.getVehiculos();
		vehiculos.remove( vehiculo );

		assertTrue( vehiculos.size() == 0 );
		assertTrue( "Se debe retornar copia de la coleccion o hacerla de solo lectura", 
			cliente.getVehiculos().size() == 1
		);
	}
}