package uo.sdi.tests;

import static org.junit.Assert.*;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

public class ListarCategoriasActionTest {
	
	private WebTester user;
	
    @Before
    public void setUp() {
    	user = new WebTester();
    	user.setBaseUrl("http://localhost:8280/UO245930/");
    	user.beginAt("/");
    	user.setTextField("nombreUsuario", "john");
		user.setTextField("password", "john123");
		user.submit();
    }
    
    private void loginWithUsuario(){
    	
    }
	
	@Test
	public void test() {
		
	}

}
