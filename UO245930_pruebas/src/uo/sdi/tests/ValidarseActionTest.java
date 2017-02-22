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
    }
	
	@Test
	public void EXITOLoginTest() {
		user.beginAt("/");
		user.assertTitleEquals("TaskManager - Inicie sesión");
		user.setTextField("nombreUsuario", "john");
		user.setTextField("password", "john123");
		user.submit();
		user.assertTitleEquals("TaskManager - Página principal del usuario");
		user.assertTextInElement("id", "2");
		user.assertTextInElement("email", "john@mail.com");
		user.assertTextInElement("isAdmin", "si");
		user.assertTextInElement("login", "john");
		user.assertTextInElement("status","ENABLED");
	}
	
	@Test
	public void FRACASOLoginTest(){
		user.beginAt("/");
		user.assertTitleEquals("TaskManager - Inicie sesión");
		user.setTextField("nombreUsuario", "usuarioNoRegistrado");
		user.setTextField("password", "123456");
		user.submit();
		user.assertTitleEquals("TaskManager - Inicie sesión");
		
	}
}
