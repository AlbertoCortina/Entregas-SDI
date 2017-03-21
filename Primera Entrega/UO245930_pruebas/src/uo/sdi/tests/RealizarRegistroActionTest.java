package uo.sdi.tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

public class RealizarRegistroActionTest {

	private WebTester user;
	
    @Before
    public void setUp() {
    	user = new WebTester();
    	user.setBaseUrl("http://localhost:8280/UO245930/");
    	user.clickLink("registrarUsuario_link_id");
    	user.assertTitleEquals("TaskManager - Registro");
    }
    
    @Test
	public void EXITORealizarRegistroTest() {
    	user.setTextField("login", "usuarioPrueba");
    	user.setTextField("email", "prueba@mail.com");
    	user.setTextField("password", "123456");
    	user.setTextField("passwordRepetida", "123456");
    	user.submit();
    	user.assertTitleEquals("TaskManager - Inicie sesión");
    	
    	//Comprobamos que inicia sesion
    	
    	user.assertTitleEquals("TaskManager - Inicie sesión");
		user.setTextField("nombreUsuario", "usuarioPrueba");
		user.setTextField("password", "123456");
		user.submit();
		user.assertTitleEquals("TaskManager - Página principal del usuario");
		user.assertTextInElement("email", "prueba@mail.com");
		user.assertTextInElement("isAdmin", "no");
		user.assertTextInElement("login", "usuarioPrueba");
		user.assertTextInElement("status","ENABLED");
    	
    }
    
    @Test
	public void FRACASORealizarRegistroTest() {
    	//Provocamos el fracaso poniendo la contraseña repetida distinta
    	user.setTextField("login", "usuarioPrueba");
    	user.setTextField("email", "prueba@mail.com");
    	user.setTextField("password", "123456");
    	user.setTextField("passwordRepetida", "123");
    	user.submit();
    	user.assertTitleEquals("TaskManager - Registro");
    }
}
