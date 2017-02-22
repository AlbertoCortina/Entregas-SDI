package uo.sdi.tests;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

public class RegistrarseActionTest {
	
	private WebTester user;
	
    @Before
    public void setUp() {
    	user = new WebTester();
    	user.setBaseUrl("http://localhost:8280/UO245930/");
    }
    
    @Test
	public void registrarseTest() {
    	user.clickLink("registrarUsuario_link_id");
    	user.assertTitleEquals("TaskManager - Registro");
    }
}
