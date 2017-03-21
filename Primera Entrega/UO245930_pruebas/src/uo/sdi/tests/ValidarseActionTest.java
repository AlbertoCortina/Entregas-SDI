package uo.sdi.tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

public class ValidarseActionTest {
	
	private WebTester user;
	
    @Before
    public void setUp() {
    	user = new WebTester();
    	user.setBaseUrl("http://localhost:8280/UO245930/");
    	user.beginAt("/");
    }
	
	@Test
	public void EXITOUsuarioLoginTest() {
		user.assertTitleEquals("TaskManager - Inicie sesión");
		user.setTextField("nombreUsuario", "john");
		user.setTextField("password", "john123");
		user.submit();
		user.assertTitleEquals("TaskManager - Página principal del usuario");
		user.assertTextInElement("email", "john@mail.com");
		user.assertTextInElement("isAdmin", "no");
		user.assertTextInElement("login", "john");
		user.assertTextInElement("status","ENABLED");
	}
	
	@Test
	public void EXITOAdminLoginTest() {
		user.assertTitleEquals("TaskManager - Inicie sesión");
		user.setTextField("nombreUsuario", "admin");
		user.setTextField("password", "admin123");
		user.submit();
		user.assertTitleEquals("TaskManager - Página principal del administrador");
		user.assertTextInElement("email", "admin@system.gtd");
		user.assertTextInElement("isAdmin", "si");
		user.assertTextInElement("login", "admin");
		user.assertTextInElement("status", "ENABLED");
	}
	
	@Test
	public void FRACASOLoginTest(){
		user.assertTitleEquals("TaskManager - Inicie sesión");
		user.setTextField("nombreUsuario", "usuarioNoRegistrado");
		user.setTextField("password", "123456");
		user.submit();
		user.assertTitleEquals("TaskManager - Inicie sesión");
		
	}
}
