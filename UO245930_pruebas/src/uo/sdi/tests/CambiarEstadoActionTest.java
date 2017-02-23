package uo.sdi.tests;

import static org.junit.Assert.*;

import java.util.List;

import net.sourceforge.jwebunit.html.Cell;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

public class CambiarEstadoActionTest {

	private WebTester user;
	
    @Before
    public void setUp() {
    	user = new WebTester();
    	user.setBaseUrl("http://localhost:8280/UO245930/");
    	user.beginAt("/");
    	user.setTextField("nombreUsuario", "admin");
		user.setTextField("password", "admin123");
		user.submit();
		user.clickLink("listar_usuarios_link");
    }
	
	@Test
	public void CambiarEstadoTest() {
		user.assertTitleEquals("TaskManager - Listado de usuarios");
		List<Cell> cells = user.getTable("lista_usuarios_table").getRows().get(0).getCells();
		String statusBefore = cells.get(cells.size()-1).getValue();
		String login = cells.get(1).getValue();
		user.assertLinkPresent("habilitar/Deshabilitar_link_id_"+login);
		user.clickLink("habilitar/Deshabilitar_link_id_"+login);
		user.assertTitleEquals("TaskManager - Listado de usuarios");
		cells = user.getTable("lista_usuarios_table").getRows().get(0).getCells();
		String statusAfter = cells.get(cells.size()-1).getValue();
		assertFalse(statusBefore.equals(statusAfter));
	}

}
