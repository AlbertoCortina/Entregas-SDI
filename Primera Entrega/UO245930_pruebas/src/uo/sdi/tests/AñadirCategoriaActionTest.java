package uo.sdi.tests;

import static org.junit.Assert.*;
import net.sourceforge.jwebunit.html.Table;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

public class AñadirCategoriaActionTest {

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
	
	@Test
	public void EXITOAñadirCategoriaTest() {
		user.setTextField("nombreCategoria","Reuniones de trabajo");
		user.submit();
		user.assertTitleEquals("TaskManager - Listado de categorías");
		user.assertTextInTable("categorias_table", "Reuniones de trabajo");
	}
	
	@Test
	public void FRACASOAñadirCategoriaTest(){
		//Cogemos la tabla y guardamos la cantidad de filas que tiene
		Table t = user.getTable("categorias_table");
		int rowsBefore = t.getRowCount();
		user.setTextField("nombreCategoria","");
		user.submit();
		user.assertTitleEquals("TaskManager - Listado de categorías");
		//guardamos la cantidad de filas posterior al cambio
		t = user.getTable("categorias_table");
		int rowsAfter = t.getRowCount();
		assertTrue(rowsBefore == rowsAfter);	//Comprobamos sigue teniendo la misma cantidad de filas
	}

}
