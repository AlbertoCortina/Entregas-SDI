package uo.sdi.tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

public class ListarUsuariosActionTest{

	private WebTester user;
	
    @Before
    public void setUp() {
    	user = new WebTester();
    	user.setBaseUrl("http://localhost:8280/UO245930/");
    	user.beginAt("/");
    	user.setTextField("nombreUsuario", "admin");
		user.setTextField("password", "admin123");
		user.submit();
    }
	
	@Test
	public void ListarUsuariosTest() {
		user.assertTitleEquals("TaskManager - Página principal del administrador");
		user.assertLinkPresent("listar_usuarios_link");
		user.assertTitleEquals("TaskManager - Listado de usuarios");
	}

}
