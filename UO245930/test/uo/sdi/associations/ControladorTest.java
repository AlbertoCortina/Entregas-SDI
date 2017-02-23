package uo.sdi.associations;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;


public class ControladorTest {
    
	private WebTester user;
	
    @Before
    public void setUp() {
    	user = new WebTester();
    	user.setBaseUrl("http://localhost:8280/UO245930/");
    }
	
	@Test
	public void Logintest() {
		user.beginAt("/");
		user.assertTitleEquals("TaskManager - Inicie sesión");
		user.setTextField("nombreUsuario", "john");
		user.setTextField("password", "john123");
		user.submit();
	}

}
