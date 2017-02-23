package uo.sdi.tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

public class CerrarSesionActionTest {
	
	private WebTester user;
	
    @Before
    public void setUp() {
    	user = new WebTester();
    	user.setBaseUrl("http://localhost:8280/UO245930/");
    	user.beginAt("/");
    }
    
    private void loginUsuario(){
    	user.setTextField("nombreUsuario", "john");
		user.setTextField("password", "john123");
		user.submit();
    }
    
    private void loginAdmin(){
    	user.setTextField("nombreUsuario", "admin");
		user.setTextField("password", "admin123");
		user.submit();
    }
    
    @Test
    public void cerrarSesionUsuario(){
    	loginUsuario();
    	user.assertTitleEquals("TaskManager - Página principal del usuario");
    	user.assertLinkPresent("cerrarSesion_link_id");
    	user.clickLink("cerrarSesion_link_id");
    	user.assertTitleEquals("TaskManager - Inicie sesión");
    }
    
    @Test
    public void cerrarSesionAdmin(){
    	loginAdmin();
    	user.assertTitleEquals("TaskManager - Página principal del administrador");
    	user.assertLinkPresent("cerrarSesion_link_id");
    	user.clickLink("cerrarSesion_link_id");
    	user.assertTitleEquals("TaskManager - Inicie sesión");
    }
    
}
