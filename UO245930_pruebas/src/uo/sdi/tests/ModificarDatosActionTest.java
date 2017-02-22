package uo.sdi.tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

public class ModificarDatosActionTest {
	private WebTester user;
	
    @Before
    public void setUp() {
    	user = new WebTester();
    	user.setBaseUrl("http://localhost:8280/UO245930/");
    	user.beginAt("/");
    }
    
    private void loginWithUsuario(){
    	user.setTextField("nombreUsuario", "john");
		user.setTextField("password", "john123");
		user.submit();
    }
    
    private void loginWithAdmin(){
    	user.setTextField("nombreUsuario", "admin");
		user.setTextField("password", "admin123");
		user.submit();
    }
    
    @Test
	public void EXITOUsuarioModificarDatosTest() {
    	loginWithUsuario();
    	
    }
}
