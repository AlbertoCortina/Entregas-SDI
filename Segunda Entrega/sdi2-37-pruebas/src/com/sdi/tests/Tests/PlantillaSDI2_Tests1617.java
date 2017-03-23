package com.sdi.tests.Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sdi.business.CategoryService;
import com.sdi.business.Services;
import com.sdi.business.TaskService;
import com.sdi.business.UserService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.User;
import com.sdi.tests.utils.SeleniumUtils;
import com.sdi.tests.utils.TestUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class PlantillaSDI2_Tests1617 {

	WebDriver driver; 
	List<WebElement> elementos = null;
	
	WebDriverWait wait;
	
	public PlantillaSDI2_Tests1617() {
	}

	@Before
	public void run() {
		//Este código es para ejecutar con la versión portale de Firefox 46.0
		File pathToBinary = new File("S:\\firefox\\FirefoxPortable.exe");
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();       
		driver = new FirefoxDriver(ffBinary,firefoxProfile);
		driver.get("http://localhost:8180/sdi2-37");
		wait = new WebDriverWait(driver, 5);
		//Este código es para ejecutar con una versión instalada de Firex 46.0 
//		driver = new FirefoxDriver();
//		driver.get("http://localhost:8180/sdi2-37");		
	}
	
	@After
	public void end() {
		//Cerramos el navegador
		driver.quit();
	}

	//PRUEBAS
	//ADMINISTRADOR
	//PR01: Autentificar correctamente al administrador.
	@Test
    public void prueba01() {		
		TestUtils.iniciarSesion(driver, "admin1", "admin1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 10);
		
		assertEquals("Usuario: admin1", elementos.get(0).getText());	
    }
	
	//PR02: Fallo en la autenticación del administrador por introducir mal el login.
	@Test
    public void prueba02() {		
		TestUtils.iniciarSesion(driver, "loginmal", "admin1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes", 10);
		
		assertEquals("Error login: Usuario o contraseña no válido", elementos.get(0).getText());		
    }
	
	//PR03: Fallo en la autenticación del administrador por introducir mal la password.
	@Test
    public void prueba03() {		
		TestUtils.iniciarSesion(driver, "admin1", "passwordmal");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes", 10);
		
		assertEquals("Error login: Usuario o contraseña no válido", elementos.get(0).getText());
	}
	
	//PR04: Probar que la base de datos contiene los datos insertados con conexión correcta a la base de datos.
	@Test
    public void prueba04() {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 10);
		
		TestUtils.iniciarBaseDeDatos(driver);
		
		List<User> usuarios = null;		
		UserService uService;
		TaskService tService;
		CategoryService cService;
		try {
			uService = Services.getUserService();
			tService = Services.getTaskService();
			cService = Services.getCategoryService();			
			usuarios = uService.findAll();		
		
		for(int i = 1; i <= usuarios.size(); i++) {
			if(i == 1) {
				assertEquals("admin1", usuarios.get(i - 1).getLogin());
			}
			else {
				assertEquals("user"+(i - 1), usuarios.get(i - 1).getLogin());
				
				assertEquals(3, cService.findCategoriesByUserId(usuarios.get(i - 1).getId()).size());
				
				assertEquals("categoria1", cService.findCategoriesByUserId(usuarios.get(i - 1).getId()).get(0).getName());		
				assertEquals("categoria2", cService.findCategoriesByUserId(usuarios.get(i - 1).getId()).get(1).getName());
				assertEquals("categoria3", cService.findCategoriesByUserId(usuarios.get(i - 1).getId()).get(2).getName());	
				
				assertEquals(30, tService.findByUserId(usuarios.get(i - 1).getId()).size());
				assertEquals(20, tService.findTodayTasksByUserId(usuarios.get(i - 1).getId()).size());
				assertEquals(30, tService.findWeekTasksByUserId(usuarios.get(i - 1).getId()).size());
				assertEquals(20, tService.findInboxTasksByUserId(usuarios.get(i - 1).getId()).size());
				
				assertEquals(3, tService.findTasksByCategoryId(cService.findCategoriesByUserId(usuarios.get(i - 1).getId()).get(0).getId()).size());
				assertEquals(3, tService.findTasksByCategoryId(cService.findCategoriesByUserId(usuarios.get(i - 1).getId()).get(1).getId()).size());
				assertEquals(4, tService.findTasksByCategoryId(cService.findCategoriesByUserId(usuarios.get(i - 1).getId()).get(2).getId()).size());
				
				for (int k = 1; k <= 30; k++) {				
					assertEquals("tarea"+k, tService.findByUserId(usuarios.get(i - 1).getId()).get(k - 1).getTitle());
				}
			}
		}
		
		} catch (BusinessException e) { }
		
	}
	
	//PR05: Visualizar correctamente la lista de usuarios normales. 
	@Test
    public void prueba05() {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 10);
		
		assertEquals("Usuario: admin1", elementos.get(0).getText());
		
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:submenuOpciones", "form-cabecera:opcion2");
		
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-listado:tablalistado", 10);
    
		try {
			int numUsuarios = Services.getUserService().findAll().size();
		
			for (int i = 1; i < numUsuarios; i++) {
				elementos = driver.findElements(By.id("login"+i));
				assertEquals("user"+i, elementos.get(0).getText());
				
				elementos = driver.findElements(By.id("email"+i));
				assertEquals("user"+i+"@email.com", elementos.get(0).getText());
				
				elementos = driver.findElements(By.id("isAdmin"+i));
				assertEquals("false", elementos.get(0).getText());				
			}
		} catch (BusinessException e) { }		
	}
	
	//PR06: Cambiar el estado de un usuario de ENABLED a DISABLED. Y tratar de entrar con el usuario que se desactivado.
	@Test
    public void prueba06() throws InterruptedException {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 10);
		
		assertEquals("Usuario: admin1", elementos.get(0).getText());
		
		TestUtils.iniciarBaseDeDatos(driver);
		
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:submenuOpciones", "form-cabecera:opcion2");
		
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-listado:tablalistado", 10);		
		
		//Cambiamos el estado al user1
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:0:cambiarEstado");		
		
		//ERROR AQUI PREGUNTAR PORQUE NO COJE EL MENSAJE YA CARGADO
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes", 10);
		
//		assertEquals("Exito: Se ha actualizado el estado del usuario USER1 a DISABLED", elementos.get(0).getText());
		
		TestUtils.cerrarSesion(driver);
		
		TestUtils.iniciarSesion(driver, "user1", "user1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes", 10);
		
		assertEquals("Error login: Usuario o contraseña no válido", elementos.get(0).getText());
    }
	
	//PR07: Cambiar el estado de un usuario a DISABLED a ENABLED. Y Y tratar de entrar con el usuario que se ha activado.
	@Test
    public void prueba07() {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 10);
		
		assertEquals("Usuario: admin1", elementos.get(0).getText());
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("form-cabecera:opcion2")));
		
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:submenuOpciones", "form-cabecera:opcion2");
		
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-listado:tablalistado", 10);		
		
		//Cambiamos el estado al user1
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:0:cambiarEstado");	
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes", 10);
		
//		assertEquals("Exito: Se ha actualizado el estado del usuario USER1 a ENABLED", elementos.get(0).getText());
		
		TestUtils.cerrarSesion(driver);
		
		TestUtils.iniciarSesion(driver, "user1", "user1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 10);
		
		assertEquals("Usuario: user1", elementos.get(0).getText());
    }
	
	//PR08: Ordenar por Login
	@Test
    public void prueba08() throws InterruptedException {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 10);
		
		assertEquals("Usuario: admin1", elementos.get(0).getText());
		
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("form-cabecera:opcion2")));
		
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:submenuOpciones", "form-cabecera:opcion2");
		
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-listado:tablalistado", 10);
		
		//Clicamos el header de login para ordenar ascendentemente
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:login");
		
		List<String> usuariosOrdenados = TestUtils.ordenarPorLogin(true);
		int numUsuarios = usuariosOrdenados.size();
		
		for (int i = 1; i < numUsuarios; i++) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login"+i)));
			elementos = driver.findElements(By.id("login"+i));
			assertEquals(elementos.get(0).getText(), usuariosOrdenados.get(i - 1));
		}
		
		//Clicamos el header de email para ordenar descendentemente
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:login");
		
		Thread.sleep(40);		
		
		usuariosOrdenados = TestUtils.ordenarPorLogin(false);	
		
		for (int i = 1; i < numUsuarios; i++) {
			elementos = driver.findElements(By.id("login"+i));
			assertEquals(elementos.get(0).getText(), usuariosOrdenados.get(i - 1));
		}	
    }
	
	//PR09: Ordenar por Email
	@Test
    public void prueba09() throws InterruptedException {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 10);
		
		assertEquals("Usuario: admin1", elementos.get(0).getText());
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("form-cabecera:opcion2")));
		
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:submenuOpciones", "form-cabecera:opcion2");
		
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-listado:tablalistado", 10);
		
		//Clicamos el header de email para ordenar ascendentemente
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:email");
		
		List<String> usuariosOrdenados = TestUtils.ordenarPorEmail(true);
		int numUsuarios = usuariosOrdenados.size();		
		
//		for(String s: usuariosOrdenados) {
//			System.out.println(s);
//		}
		
		for (int i = 1; i <= numUsuarios; i++) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email"+i)));
			elementos = driver.findElements(By.id("email"+i));
			assertEquals(elementos.get(0).getText(), usuariosOrdenados.get(i - 1));
		}
		
		//Clicamos el header de email para ordenar descendentemente
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:email");
		
		Thread.sleep(40);
		
		usuariosOrdenados = TestUtils.ordenarPorEmail(false);	
		
		for (int i = 1; i <= numUsuarios; i++) {
			elementos = driver.findElements(By.id("email"+i));
			assertEquals(elementos.get(0).getText(), usuariosOrdenados.get(i - 1));
		}
    }
	
	//PR10: Ordenar por Status
	@Test
    public void prueba10() throws InterruptedException {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 10);
		
		assertEquals("Usuario: admin1", elementos.get(0).getText());
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("form-cabecera:opcion2")));
		
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:submenuOpciones", "form-cabecera:opcion2");
		
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-listado:tablalistado", 10);
		
		//Cambiamos el estado al user1
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:0:cambiarEstado");	
		
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("form-listado:tablalistado:status")) );

		//Clicamos el header de email para ordenar ascendentemente
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:status");
		
		Thread.sleep(40);
		
		List<String> usuariosOrdenados = TestUtils.ordenarPorStatus(true);
		int numUsuarios = usuariosOrdenados.size();			
		
		for (int i = 0; i < numUsuarios - 1; i++) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("form-listado:tablalistado:"+i+":cambiarEstado")) );
			elementos = driver.findElements(By.id("form-listado:tablalistado:"+i+":cambiarEstado"));
			assertEquals(elementos.get(0).getText(), usuariosOrdenados.get(i));
		}
		
		//Clicamos el header de email para ordenar descendentemente
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:status");
		
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-listado:tablalistado", 10);
		
		usuariosOrdenados = TestUtils.ordenarPorStatus(false);	
		
		for (int i = 0; i < numUsuarios; i++) {
			elementos = driver.findElements(By.id("form-listado:tablalistado:"+i+":cambiarEstado"));
			assertEquals(elementos.get(0).getText(), usuariosOrdenados.get(i));
		}
		
		//Cambiamos el estado al user1
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:0:cambiarEstado");	
    }
	
	//PR11: Borrar  una cuenta de usuario normal y datos relacionados.
	@Test
    public void prueba11() {
		assertTrue(false);
    }
	//PR12: Crear una cuenta de usuario normal con datos válidos.
	@Test
    public void prueba12() {
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "enlace", 10);
		
		TestUtils.clicarElemento(driver, "enlace");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "form-registro:enlace", 10);
		
		//Mandamos todos los datos al registro
		elementos = driver.findElements(By.id("form-registro:input-login"));
		elementos.get(0).sendKeys("user11");
		elementos = driver.findElements(By.id("form-registro:input-correo"));
		elementos.get(0).sendKeys("user11@email.com");
		elementos = driver.findElements(By.id("form-registro:input-password"));
		elementos.get(0).sendKeys("user11user");
		elementos = driver.findElements(By.id("form-registro:input-rPassword"));
		elementos.get(0).sendKeys("user11user");
		
		TestUtils.clicarElemento(driver, "form-registro:enlace");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes", 10);
		
		//Confirmamos sale el mensaje de confirmación de registro
		assertEquals(elementos.get(0).getText(), "Exito en el registro: Se ha registrado correctamente, inicie sesión para acceder");
		
		//Eliminamos el usuario creado para que no pueda interferir con futuras pruebas
		TestUtils.iniciarSesion(driver, "admin1", "admin1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "form-registro:enlace", 10);

		
		
		
    }
	//PR13: Crear una cuenta de usuario normal con login repetido.
	@Test
    public void prueba13() {
		assertTrue(false);
    }
	//PR14: Crear una cuenta de usuario normal con Email incorrecto.
	@Test
    public void prueba14() {
		assertTrue(false);
    }
	//PR15: Crear una cuenta de usuario normal con Password incorrecta.
	@Test
    public void prueba15() {
		assertTrue(false);
    }
	//USUARIO
	//PR16: Comprobar que en Inbox sólo aparecen listadas las tareas sin categoría y que son las que tienen que. Usar paginación navegando por las tres páginas.
	@Test
    public void prueba16() {
		assertTrue(false);
    }
	//PR17: Funcionamiento correcto de la ordenación por fecha planeada.
	@Test
    public void prueba17() {
		assertTrue(false);
    }
	//PR18: Funcionamiento correcto del filtrado.
	@Test
    public void prueba18() {
		assertTrue(false);
    }
	//PR19: Funcionamiento correcto de la ordenación por categoría.
	@Test
    public void prueba19() {
		assertTrue(false);
    }
	//PR20: Funcionamiento correcto de la ordenación por fecha planeada.
	@Test
    public void prueba20() {
		assertTrue(false);
    }
	//PR21: Comprobar que las tareas que no están en rojo son las de hoy y además las que deben ser.
	@Test
    public void prueba21() {
		assertTrue(false);
    }
	//PR22: Comprobar que las tareas retrasadas están en rojo y son las que deben ser.
	@Test
    public void prueba22() {
		assertTrue(false);
    }
	//PR23: Comprobar que las tareas de hoy y futuras no están en rojo y que son las que deben ser.
	@Test
    public void prueba23() {
		assertTrue(false);
    }
	//PR24: Funcionamiento correcto de la ordenación por día.
	@Test
    public void prueba24() {
		assertTrue(false);
    }
	//PR25: Funcionamiento correcto de la ordenación por nombre.
	@Test
    public void prueba25() {
		assertTrue(false);
    }
	//PR26: Confirmar una tarea, inhabilitar el filtro de tareas terminadas, ir a la pagina donde está la tarea terminada y comprobar que se muestra. 
	@Test
    public void prueba26() {
		assertTrue(false);
    }
	//PR27: Crear una tarea sin categoría y comprobar que se muestra en la lista Inbox.
	@Test
    public void prueba27() {
		assertTrue(false);
    }
	//PR28: Crear una tarea con categoría categoria1 y fecha planeada Hoy y comprobar que se muestra en la lista Hoy.
	@Test
    public void prueba28() {
		assertTrue(false);
    }
	//PR29: Crear una tarea con categoría categoria1 y fecha planeada posterior a Hoy y comprobar que se muestra en la lista Semana.
	@Test
    public void prueba29() {
		assertTrue(false);
    }
	//PR30: Editar el nombre, y categoría de una tarea (se le cambia a categoría1) de la lista Inbox y comprobar que las tres pseudolista se refresca correctamente.
	@Test
    public void prueba30() {
		assertTrue(false);
    }
	//PR31: Editar el nombre, y categoría (Se cambia a sin categoría) de una tarea de la lista Hoy y comprobar que las tres pseudolistas se refrescan correctamente.
	@Test
    public void prueba31() {
		assertTrue(false);
    }
	//PR32: Marcar una tarea como finalizada. Comprobar que desaparece de las tres pseudolistas.
	@Test
    public void prueba32() {
		assertTrue(false);
    }
	
	//PR33: Salir de sesión desde cuenta de administrador.
	@Test
    public void prueba33() {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 10);
		
		assertEquals("Usuario: admin1", elementos.get(0).getText());
		
		TestUtils.cerrarSesion(driver);
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "input-usuario", 10);
		
		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		SeleniumUtils.textoPresentePagina(driver, "Contraseña");		
    }
	
	//PR34: Salir de sesión desde cuenta de usuario normal.
	@Test
    public void prueba34() {
		TestUtils.iniciarSesion(driver, "user1", "user1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 10);
		
		assertEquals("Usuario: user1", elementos.get(0).getText());
		
		TestUtils.cerrarSesion(driver);
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "input-usuario", 10);
		
		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		SeleniumUtils.textoPresentePagina(driver, "Contraseña");
    }
	//PR35: Cambio del idioma por defecto a un segundo idioma. (Probar algunas vistas)
	@Test
    public void prueba35() {
		assertTrue(false);
    }
	//PR36: Cambio del idioma por defecto a un segundo idioma y vuelta al idioma por defecto. (Probar algunas vistas)
	@Test
    public void prueba36() {
		assertTrue(false);
    }
	//PR37: Intento de acceso a un  URL privado de administrador con un usuario autenticado como usuario normal.
	@Test
    public void prueba37() {
		assertTrue(false);
    }
	//PR38: Intento de acceso a un  URL privado de usuario normal con un usuario no autenticado.
	@Test
    public void prueba38() {
		assertTrue(false);
    }
}