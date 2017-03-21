package uo.sdi.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	ValidarseActionTest.class,
	RegistrarseActionTest.class,
	RealizarRegistroActionTest.class,
	ModificarDatosActionTest.class,
	ListarUsuariosActionTest.class,
	ListarCategoriasActionTest.class,
	CerrarSesionActionTest.class,
	AñadirCategoriaActionTest.class,
	CerrarSesionActionTest.class
})
public class AllTests {

}
