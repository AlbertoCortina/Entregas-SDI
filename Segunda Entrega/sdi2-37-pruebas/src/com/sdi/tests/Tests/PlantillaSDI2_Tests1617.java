package com.sdi.tests.Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
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
import org.openqa.selenium.interactions.Actions;
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

	List<WebElement> elementos = null;
	static WebDriver driver = getDriver();
	static WebDriverWait wait = new WebDriverWait(driver, 10);
	String URL = "http://localhost:8180/sdi2-37";

	public PlantillaSDI2_Tests1617() {

	}

	public static WebDriver getDriver() {
		File pathToBinary = new File("S:\\firefox\\FirefoxPortable.exe");
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		return new FirefoxDriver(ffBinary, firefoxProfile);
	}

	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	@AfterClass
	public static void end() {
		// driver.quit();
	}

	// PRUEBAS
	// ADMINISTRADOR
	// PR01: Autentificar correctamente al administrador.
	@Test
	public void prueba01() {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: admin1", elementos.get(0).getText());
	}

	// PR02: Fallo en la autenticación del administrador por introducir mal el
	// login.
	@Test
	public void prueba02() {
		TestUtils.iniciarSesion(driver, "loginmal", "admin1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes",
				10);

		assertEquals("Error: Usuario o contraseña no válido", elementos.get(0)
				.getText());
	}

	// PR03: Fallo en la autenticación del administrador por introducir mal la
	// password.
	@Test
	public void prueba03() {
		TestUtils.iniciarSesion(driver, "admin1", "passwordmal");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes",
				10);

		assertEquals("Error: Usuario o contraseña no válido", elementos.get(0)
				.getText());
	}

	// PR04: Probar que la base de datos contiene los datos insertados con
	// conexión correcta a la base de datos.
	@Test
	public void prueba04() {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

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

			for (int i = 1; i <= usuarios.size(); i++) {
				if (i == 1) {
					assertEquals("admin1", usuarios.get(i - 1).getLogin());
				} else {
					assertEquals("user" + (i - 1), usuarios.get(i - 1)
							.getLogin());

					assertEquals(
							3,
							cService.findCategoriesByUserId(
									usuarios.get(i - 1).getId()).size());

					assertEquals(
							"categoria1",
							cService.findCategoriesByUserId(
									usuarios.get(i - 1).getId()).get(0)
									.getName());
					assertEquals(
							"categoria2",
							cService.findCategoriesByUserId(
									usuarios.get(i - 1).getId()).get(1)
									.getName());
					assertEquals(
							"categoria3",
							cService.findCategoriesByUserId(
									usuarios.get(i - 1).getId()).get(2)
									.getName());

					assertEquals(30,
							tService.findByUserId(usuarios.get(i - 1).getId())
									.size());
					assertEquals(
							20,
							tService.findTodayTasksByUserId(
									usuarios.get(i - 1).getId()).size());
					assertEquals(
							30,
							tService.findWeekTasksByUserId(
									usuarios.get(i - 1).getId()).size());
					assertEquals(
							20,
							tService.findInboxTasksByUserId(
									usuarios.get(i - 1).getId()).size());

					assertEquals(
							3,
							tService.findTasksByCategoryId(
									cService.findCategoriesByUserId(
											usuarios.get(i - 1).getId()).get(0)
											.getId()).size());
					assertEquals(
							3,
							tService.findTasksByCategoryId(
									cService.findCategoriesByUserId(
											usuarios.get(i - 1).getId()).get(1)
											.getId()).size());
					assertEquals(
							4,
							tService.findTasksByCategoryId(
									cService.findCategoriesByUserId(
											usuarios.get(i - 1).getId()).get(2)
											.getId()).size());

					for (int k = 1; k <= 30; k++) {
						if (String.valueOf(k).length() == 1) {
							assertEquals(
									"tarea0" + k,
									tService.findByUserId(
											usuarios.get(i - 1).getId())
											.get(k - 1).getTitle());
						} else {
							assertEquals(
									"tarea" + k,
									tService.findByUserId(
											usuarios.get(i - 1).getId())
											.get(k - 1).getTitle());
						}

					}
				}
			}

		} catch (BusinessException e) {
		}

	}

	// PR05: Visualizar correctamente la lista de usuarios normales.
	@Test
	public void prueba05() {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: admin1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:opcion2");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		try {
			int numUsuarios = Services.getUserService().findAll().size();

			for (int i = 1; i < numUsuarios; i++) {
				elementos = driver.findElements(By
						.id("form-listado:tablalistado:" + (i - 1) + ":login"));
				assertEquals("user" + i, elementos.get(0).getText());

				elementos = driver.findElements(By
						.id("form-listado:tablalistado:" + (i - 1) + ":email"));
				assertEquals("user" + i + "@email.com", elementos.get(0)
						.getText());

				elementos = driver
						.findElements(By.id("form-listado:tablalistado:"
								+ (i - 1) + ":isAdmin"));
				assertEquals("false", elementos.get(0).getText());
			}
		} catch (BusinessException e) {
		}
	}

	// PR06: Cambiar el estado de un usuario de ENABLED a DISABLED. Y tratar de
	// entrar con el usuario que se desactivado.
	@Test
	public void prueba06() throws InterruptedException {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: admin1", elementos.get(0).getText());

		TestUtils.iniciarBaseDeDatos(driver);

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:opcion2");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		// Cambiamos el estado al user1
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
				.id("form-listado:tablalistado:0:cambiarEstado")));
		TestUtils.clicarElemento(driver,
				"form-listado:tablalistado:0:cambiarEstado");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes", 10);

		TestUtils.cerrarSesion(driver);

		TestUtils.iniciarSesion(driver, "user1", "user1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes",
				10);

		assertEquals("Error: Usuario o contraseña no válido", elementos.get(0)
				.getText());
	}

	// PR07: Cambiar el estado de un usuario a DISABLED a ENABLED. Y Y tratar de
	// entrar con el usuario que se ha activado.
	@Test
	public void prueba07() throws InterruptedException {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: admin1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:opcion2");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		// Cambiamos el estado al user1
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
				.id("form-listado:tablalistado:0:cambiarEstado")));
		TestUtils.clicarElemento(driver,
				"form-listado:tablalistado:0:cambiarEstado");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes", 10);

		TestUtils.cerrarSesion(driver);

		TestUtils.iniciarSesion(driver, "user1", "user1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: user1", elementos.get(0).getText());
	}

	// PR08: Ordenar por Login
	@Test
	public void prueba08() throws InterruptedException {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: admin1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:opcion2");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		// Clicamos el header de login para ordenar ascendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:loginH")));
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:loginH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		List<String> usuariosOrdenados = TestUtils.ordenarPorLogin(true);
		int numUsuarios = usuariosOrdenados.size();

		for (int i = 1; i < numUsuarios; i++) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.id("form-listado:tablalistado:" + (i - 1) + ":login")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":login"));
			assertEquals(elementos.get(0).getText(),
					usuariosOrdenados.get(i - 1));
		}

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		// Clicamos el header de email para ordenar descendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:loginH")));
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:loginH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		usuariosOrdenados = TestUtils.ordenarPorLogin(false);

		for (int i = 1; i < numUsuarios; i++) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.id("form-listado:tablalistado:" + (i - 1) + ":login")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":login"));
			assertEquals(elementos.get(0).getText(),
					usuariosOrdenados.get(i - 1));
		}
	}

	// PR09: Ordenar por Email
	@Test
	public void prueba09() throws InterruptedException {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: admin1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:opcion2");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		// Clicamos el header de email para ordenar ascendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:emailH")));
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:emailH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		List<String> usuariosOrdenados = TestUtils.ordenarPorEmail(true);
		int numUsuarios = usuariosOrdenados.size();

		for (int i = 1; i < numUsuarios; i++) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.id("form-listado:tablalistado:" + (i - 1) + ":email")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":email"));
			assertEquals(elementos.get(0).getText(),
					usuariosOrdenados.get(i - 1));
		}

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		// Clicamos el header de email para ordenar descendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:emailH")));
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:emailH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		usuariosOrdenados = TestUtils.ordenarPorEmail(false);

		for (int i = 1; i < numUsuarios; i++) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.id("form-listado:tablalistado:" + (i - 1) + ":email")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":email"));
			assertEquals(elementos.get(0).getText(),
					usuariosOrdenados.get(i - 1));
		}
	}

	// PR10: Ordenar por Status
	@Test
	public void prueba10() throws InterruptedException {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: admin1", elementos.get(0).getText());

		TestUtils.iniciarBaseDeDatos(driver);

		SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes", 10);

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:opcion2");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		// Cambiamos el estado al user1
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:0:cambiarEstado")));
		TestUtils.clicarElemento(driver,
				"form-listado:tablalistado:0:cambiarEstado");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		// Clicamos el header de status para ordenar ascendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:statusH")));
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:statusH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		List<String> usuariosOrdenados = TestUtils.ordenarPorStatus(true);
		int numUsuarios = usuariosOrdenados.size();

		for (int i = 0; i < numUsuarios - 1; i++) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.id("form-listado:tablalistado:" + i + ":cambiarEstado")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ i + ":cambiarEstado"));
			assertEquals(elementos.get(0).getText(), usuariosOrdenados.get(i));
		}

		// Clicamos el header de status para ordenar descendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:statusH")));
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:statusH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		usuariosOrdenados = TestUtils.ordenarPorStatus(false);

		for (int i = 0; i < numUsuarios; i++) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.id("form-listado:tablalistado:" + i + ":cambiarEstado")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ i + ":cambiarEstado"));
			assertEquals(elementos.get(0).getText(), usuariosOrdenados.get(i));
		}

		// Cambiamos el estado al user1
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:0:cambiarEstado")));
		TestUtils.clicarElemento(driver,
				"form-listado:tablalistado:0:cambiarEstado");
	}

	// PR11: Borrar una cuenta de usuario normal y datos relacionados.
	@Test
	public void prueba11() throws InterruptedException {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: admin1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:opcion2");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
				.id("form-listado:tablalistado:1:eliminar")));
		TestUtils
				.clicarElemento(driver, "form-listado:tablalistado:1:eliminar");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
				.id("form-listado:tablalistado:9:confirmacion")));
		TestUtils.clicarElemento(driver,
				"form-listado:tablalistado:9:confirmacion");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		TestUtils.cerrarSesion(driver);

		TestUtils.iniciarSesion(driver, "user2", "user2");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes",
				10);

		assertEquals("Error: Usuario o contraseña no válido", elementos.get(0)
				.getText());
	}

	// PR12: Crear una cuenta de usuario normal con datos válidos.
	@Test
	public void prueba12() {
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "enlace", 10);

		TestUtils.clicarElemento(driver, "enlace");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-registro:enlace", 10);

		elementos = driver.findElements(By.id("form-registro:input-login"));
		elementos.get(0).sendKeys("user11");

		elementos = driver.findElements(By.id("form-registro:input-correo"));
		elementos.get(0).sendKeys("user11@email.com");

		elementos = driver.findElements(By.id("form-registro:input-password"));
		elementos.get(0).sendKeys("user11user");

		elementos = driver.findElements(By.id("form-registro:input-rPassword"));
		elementos.get(0).sendKeys("user11user");

		TestUtils.clicarElemento(driver, "form-registro:enlace");

		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("mensajes"), "Exito"));
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes",
				10);

		assertEquals(
				"Exito: Se ha registrado correctamente, inicie sesión para acceder",
				elementos.get(0).getText());

		TestUtils.iniciarSesion(driver, "user11", "user11user");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: user11", elementos.get(0).getText());
	}

	// PR13: Crear una cuenta de usuario normal con login repetido.
	@Test
	public void prueba13() throws InterruptedException {
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "enlace", 10);

		TestUtils.clicarElemento(driver, "enlace");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-registro:enlace", 10);

		// Mandamos todos los datos al registro
		elementos = driver.findElements(By.id("form-registro:input-login"));
		elementos.get(0).sendKeys("user1");
		elementos = driver.findElements(By.id("form-registro:input-correo"));
		elementos.get(0).sendKeys("user1@email.com");
		elementos = driver.findElements(By.id("form-registro:input-password"));
		elementos.get(0).sendKeys("user1user");
		elementos = driver.findElements(By.id("form-registro:input-rPassword"));
		elementos.get(0).sendKeys("user1user");

		TestUtils.clicarElemento(driver, "form-registro:enlace");

		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("form-registro:j_idt11"), "Escoja"));
		elementos = driver.findElements(By.id("form-registro:j_idt11"));

		assertEquals(
				"Escoja otro usuario, el que ha seleccionado ya está en uso",
				elementos.get(0).getText());
	}

	// PR14: Crear una cuenta de usuario normal con Email incorrecto.
	@Test
	public void prueba14() throws InterruptedException {
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "enlace", 10);

		TestUtils.clicarElemento(driver, "enlace");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-registro:enlace", 10);

		elementos = driver.findElements(By.id("form-registro:input-login"));
		elementos.get(0).sendKeys("user11");

		elementos = driver.findElements(By.id("form-registro:input-correo"));
		elementos.get(0).sendKeys("user11");

		elementos = driver.findElements(By.id("form-registro:input-password"));
		elementos.get(0).sendKeys("user11user");

		elementos = driver.findElements(By.id("form-registro:input-rPassword"));
		elementos.get(0).sendKeys("user11user");

		TestUtils.clicarElemento(driver, "form-registro:enlace");

		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("form-registro:j_idt13"), "EMAIL"));
		elementos = driver.findElements(By.id("form-registro:j_idt13"));

		assertEquals("El campo EMAIL no es válido", elementos.get(0).getText());
	}

	// PR15: Crear una cuenta de usuario normal con Password incorrecta.
	@Test
	public void prueba15() throws InterruptedException {
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "enlace", 10);

		TestUtils.clicarElemento(driver, "enlace");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-registro:enlace", 10);

		elementos = driver.findElements(By.id("form-registro:input-login"));
		elementos.get(0).sendKeys("user11");

		elementos = driver.findElements(By.id("form-registro:input-correo"));
		elementos.get(0).sendKeys("user11@email.com");

		elementos = driver.findElements(By.id("form-registro:input-password"));
		elementos.get(0).sendKeys("u");

		elementos = driver.findElements(By.id("form-registro:input-rPassword"));
		elementos.get(0).sendKeys("user11");

		TestUtils.clicarElemento(driver, "form-registro:enlace");

		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("form-registro:j_idt15"), "CONTRASEÑA"));
		elementos = driver.findElements(By.id("form-registro:j_idt15"));

		assertEquals(
				elementos.get(0).getText(),
				"El campo CONTRASEÑA no es válido (debe contener números y letras y tener 8 caracteres mínimo)");
	}

	// USUARIO
	// PR16: Comprobar que en Inbox sólo aparecen listadas las tareas sin
	// categoría y que son las que tienen que. Usar paginación navegando por las
	// tres páginas.
	@Test
	public void prueba16() {
		TestUtils.iniciarSesion(driver, "user1", "user1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: user1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:listaInbox");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		List<String> inboxTask = TestUtils
				.ordenarTareasInboxFechaPlaneada(true);

		int numTarea = 0;
		int j = 2;
		for (int i = 1; i <= inboxTask.size(); i++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
					.id("form-listado:tablalistado:" + (i - 1) + ":fPlaneada")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":fPlaneada"));
			assertEquals(elementos.get(0).getText(), inboxTask.get(i - 1));

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}
	}

	// PR17: Funcionamiento correcto de la ordenación por fecha planeada.
	@Test
	public void prueba17() {
		TestUtils.iniciarSesion(driver, "user1", "user1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: user1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:listaInbox");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		// Clicamos el header de fecha planeada para ordenar ascendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:planeadaH")));
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:planeadaH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		List<String> inboxTask = TestUtils.ordenarTareasInboxFechaPlaneada(true);

		int numTarea = 0;
		int j = 2;
		for (int i = 1; i <= inboxTask.size(); i++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
					.id("form-listado:tablalistado:" + (i - 1) + ":fPlaneada")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":fPlaneada"));
			assertEquals(elementos.get(0).getText(), inboxTask.get(i - 1));

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}

		// Volvemos al principio
		driver.findElement(
				By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[2]"))
				.click();

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		// Clicamos el header de fecha planeada para ordenar descendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:planeadaH")));
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:planeadaH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		inboxTask = TestUtils.ordenarTareasInboxFechaPlaneada(false);

		numTarea = 0;
		j = 2;
		for (int i = 1; i <= inboxTask.size(); i++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
					.id("form-listado:tablalistado:" + (i - 1) + ":fPlaneada")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":fPlaneada"));
			assertEquals(elementos.get(0).getText(), inboxTask.get(i - 1));

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}
	}

	// PR18: Funcionamiento correcto del filtrado.
	@Test
	public void prueba18() {
		TestUtils.iniciarSesion(driver, "user1", "user1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: user1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:listaInbox");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		// Filtramos por la cadena "tarea"
		elementos = driver.findElements(By
				.id("form-listado:tablalistado:tituloH:filter"));
		elementos.get(0).sendKeys("2");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		List<String> inboxTask = TestUtils.filtrarTareas(driver, "2");

		int numTarea = 0;
		int j = 2;
		for (int i = 1; i <= inboxTask.size(); i++) {
			wait.until(ExpectedConditions.textToBePresentInElementLocated(
					By.id("form-listado:tablalistado:" + (i - 1) + ":title"),
					"2"));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":title"));
			assertEquals(elementos.get(0).getText(), inboxTask.get(i - 1));

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}
	}

	// PR19: Funcionamiento correcto de la ordenación por categoría.
	@Test
	public void prueba19() {
		TestUtils.iniciarSesion(driver, "user1", "user1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: user1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:listaHoy");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		// Clicamos el header de fecha planeada para ordenar ascendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:categoriaH")));
		TestUtils
				.clicarElemento(driver, "form-listado:tablalistado:categoriaH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		List<String> hoyTasks = TestUtils.ordenarTareasHoyCategoria(true);

		int numTarea = 0;
		int j = 2;
		for (int i = 1; i <= hoyTasks.size(); i++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
					.id("form-listado:tablalistado:" + (i - 1) + ":categoria")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":categoria"));
			assertEquals(elementos.get(0).getText(), hoyTasks.get(i - 1));

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}

		// Volvemos al principio
		driver.findElement(
				By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[2]"))
				.click();

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		// Clicamos el header de fecha planeada para ordenar descendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:categoriaH")));
		TestUtils
				.clicarElemento(driver, "form-listado:tablalistado:categoriaH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		hoyTasks = TestUtils.ordenarTareasHoyCategoria(false);

		numTarea = 0;
		j = 2;
		for (int i = 1; i <= hoyTasks.size(); i++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
					.id("form-listado:tablalistado:" + (i - 1) + ":categoria")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":categoria"));
			assertEquals(elementos.get(0).getText(), hoyTasks.get(i - 1));

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}
	}

	// PR20: Funcionamiento correcto de la ordenación por fecha planeada.
	@Test
	public void prueba20() {
		TestUtils.iniciarSesion(driver, "user1", "user1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: user1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:listaHoy");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		// Clicamos el header de fecha planeada para ordenar ascendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:planeadaH")));
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:planeadaH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		List<String> hoyTask = TestUtils.ordenarTareasHoyFechaPlaneada(true);

		int numTarea = 0;
		int j = 2;
		for (int i = 1; i <= hoyTask.size(); i++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
					.id("form-listado:tablalistado:" + (i - 1) + ":fPlaneada")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":fPlaneada"));
			assertEquals(elementos.get(0).getText(), hoyTask.get(i - 1));

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}

		// Volvemos al principio
		driver.findElement(
				By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[2]"))
				.click();

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		// Clicamos el header de fecha planeada para ordenar descendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:planeadaH")));
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:planeadaH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		hoyTask = TestUtils.ordenarTareasHoyFechaPlaneada(false);

		numTarea = 0;
		j = 2;
		for (int i = 1; i <= hoyTask.size(); i++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
					.id("form-listado:tablalistado:" + (i - 1) + ":fPlaneada")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":fPlaneada"));
			assertEquals(elementos.get(0).getText(), hoyTask.get(i - 1));

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}
	}

	// PR21: Comprobar que las tareas que no están en rojo son las de hoy y
	// además las que deben ser.
	@Test
	public void prueba21() {
		TestUtils.iniciarSesion(driver, "user1", "user1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: user1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:listaHoy");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		List<String> hoyTask = TestUtils.ordenarTareasHoyFechaPlaneada(true);

		int numTarea = 0;
		int j = 2;
		for (int i = 1; i <= hoyTask.size(); i++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
					.id("form-listado:tablalistado:" + (i - 1) + ":fPlaneada")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":fPlaneada"));
			assertEquals(elementos.get(0).getText(), hoyTask.get(i - 1));

			DateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
			Date hoy = null;
			Date fechaTarea = null;

			try {
				hoy = formateador.parse(formateador.format(new Date()));
				fechaTarea = formateador.parse(elementos.get(0).getText());

				if (fechaTarea.compareTo(hoy) == 0) {
					assertNotEquals("rgba(255, 0, 0, 1)", elementos.get(0)
							.getCssValue("color"));
				} else {
					assertEquals("rgba(255, 0, 0, 1)", elementos.get(0)
							.getCssValue("color"));
				}
			} catch (ParseException e) {
			}

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}
	}

	// PR22: Comprobar que las tareas retrasadas están en rojo y son las que
	// deben ser.
	@Test
	public void prueba22() {
		TestUtils.iniciarSesion(driver, "user1", "user1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: user1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:listaHoy");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		List<String> hoyTask = TestUtils.ordenarTareasHoyFechaPlaneada(true);

		int numTarea = 0;
		int j = 2;
		for (int i = 1; i <= hoyTask.size(); i++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
					.id("form-listado:tablalistado:" + (i - 1) + ":fPlaneada")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":fPlaneada"));
			assertEquals(elementos.get(0).getText(), hoyTask.get(i - 1));

			DateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
			Date hoy = null;
			Date fechaTarea = null;

			try {
				hoy = formateador.parse(formateador.format(new Date()));
				fechaTarea = formateador.parse(elementos.get(0).getText());

				if (fechaTarea.compareTo(hoy) < 0) {
					assertEquals("rgba(255, 0, 0, 1)", elementos.get(0)
							.getCssValue("color"));
				} else {
					assertEquals("rgba(79, 79, 79, 1)", elementos.get(0)
							.getCssValue("color"));
				}

			} catch (ParseException e) {
			}

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}
	}

	// PR23: Comprobar que las tareas de hoy y futuras no están en rojo y que
	// son las que deben ser.
	@Test
	public void prueba23() {
		TestUtils.iniciarSesion(driver, "user1", "user1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: user1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:listaSemana");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		List<String> semanaTask = TestUtils
				.ordenarTareasSemanaFechaPlaneada(true);

		int numTarea = 0;
		int j = 2;
		for (int i = 1; i <= semanaTask.size(); i++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
					.id("form-listado:tablalistado:" + (i - 1) + ":fPlaneada")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":fPlaneada"));
			assertEquals(elementos.get(0).getText(), semanaTask.get(i - 1));

			DateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
			Date hoy = null;
			Date fechaTarea = null;

			try {
				hoy = formateador.parse(formateador.format(new Date()));
				fechaTarea = formateador.parse(elementos.get(0).getText());

				if (fechaTarea.compareTo(hoy) == 0
						|| fechaTarea.compareTo(hoy) > 0) {
					assertNotEquals("rgba(255, 0, 0, 1)", elementos.get(0)
							.getCssValue("color"));
				} else {
					assertEquals("rgba(255, 0, 0, 1)", elementos.get(0)
							.getCssValue("color"));
				}
			} catch (ParseException e) {
			}

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}
	}

	// PR24: Funcionamiento correcto de la ordenación por día.
	@Test
	public void prueba24() {
		TestUtils.iniciarSesion(driver, "user1", "user1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: user1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:listaSemana");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		// Clicamos el header de fecha planeada para ordenar ascendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:planeadaH")));
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:planeadaH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		List<String> semanaTask = TestUtils
				.ordenarTareasSemanaFechaPlaneada(true);

		int numTarea = 0;
		int j = 2;
		for (int i = 1; i <= semanaTask.size(); i++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
					.id("form-listado:tablalistado:" + (i - 1) + ":fPlaneada")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":fPlaneada"));
			assertEquals(elementos.get(0).getText(), semanaTask.get(i - 1));

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}

		// Volvemos al principio
		driver.findElement(
				By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[2]"))
				.click();

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		// Clicamos el header de fecha planeada para ordenar descendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:planeadaH")));
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:planeadaH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		semanaTask = TestUtils.ordenarTareasSemanaFechaPlaneada(false);

		numTarea = 0;
		j = 2;
		for (int i = 1; i <= semanaTask.size(); i++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
					.id("form-listado:tablalistado:" + (i - 1) + ":fPlaneada")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":fPlaneada"));
			assertEquals(elementos.get(0).getText(), semanaTask.get(i - 1));

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}
	}

	// PR25: Funcionamiento correcto de la ordenación por nombre.
	@Test
	public void prueba25() {
		TestUtils.iniciarSesion(driver, "user1", "user1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: user1", elementos.get(0).getText());

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:listaSemana");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		// Clicamos el header de fecha planeada para ordenar ascendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:tituloH")));
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:tituloH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		List<String> semanaTask = null;
		semanaTask = TestUtils.ordenarTareasSemanaTitulo(true);

		int numTarea = 0;
		int j = 2;
		for (int i = 1; i <= semanaTask.size(); i++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
					.id("form-listado:tablalistado:" + (i - 1) + ":title")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":title"));
			assertEquals(elementos.get(0).getText(), semanaTask.get(i - 1));

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}

		// Volvemos al principio
		driver.findElement(
				By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[2]"))
				.click();

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		// Clicamos el header de fecha planeada para ordenar descendentemente
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.id("form-listado:tablalistado:tituloH")));
		TestUtils.clicarElemento(driver, "form-listado:tablalistado:tituloH");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		semanaTask = TestUtils.ordenarTareasSemanaTitulo(false);

		numTarea = 0;
		j = 2;
		for (int i = 1; i <= semanaTask.size(); i++) {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
					.id("form-listado:tablalistado:" + (i - 1) + ":title")));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":title"));
			assertEquals(elementos.get(0).getText(), semanaTask.get(i - 1));

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}
	}

	// PR26: Confirmar una tarea, inhabilitar el filtro de tareas terminadas, ir
	// a la pagina donde está la tarea terminada y comprobar que se muestra.
	@Test
	public void prueba26() throws InterruptedException {
		String titulo = "";

		TestUtils.iniciarSesion(driver, "user1", "user1");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 10);

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:listaInbox");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		titulo = SeleniumUtils
				.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado:0:title", 10).get(0)
				.getText();

		TestUtils.clicarElemento(driver,
				"form-listado:tablalistado:0:finalizar");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		TestUtils.clicarElemento(driver, "form-listado:finalizadas");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
				.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span[3]")));
		driver.findElement(
				By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span[3]"))
				.click();

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado:19:title", 20);

		assertEquals(titulo, elementos.get(0).getText());
		assertEquals("rgba(0, 128, 0, 1)", elementos.get(0)
				.getCssValue("color"));
	}

	// PR27: Crear una tarea sin categoría y comprobar que se muestra en la
	// lista Inbox.
	@Test
	public void prueba27() {
		assertTrue(false);
	}

	// PR28: Crear una tarea con categoría categoria1 y fecha planeada Hoy y
	// comprobar que se muestra en la lista Hoy.
	@Test
	public void prueba28() {
		assertTrue(false);
	}

	// PR29: Crear una tarea con categoría categoria1 y fecha planeada posterior
	// a Hoy y comprobar que se muestra en la lista Semana.
	@Test
	public void prueba29() {
		assertTrue(false);
	}

	// PR30: Editar el nombre, y categoría de una tarea (se le cambia a
	// categoría1) de la lista Inbox y comprobar que las tres pseudolista se
	// refresca correctamente.
	@Test
	public void prueba30() {
		assertTrue(false);
	}

	// PR31: Editar el nombre, y categoría (Se cambia a sin categoría) de una
	// tarea de la lista Hoy y comprobar que las tres pseudolistas se refrescan
	// correctamente.
	@Test
	public void prueba31() {
		assertTrue(false);
	}

	// PR32: Marcar una tarea como finalizada. Comprobar que desaparece de las
	// tres pseudolistas.
	@Test
	public void prueba32() throws InterruptedException {
		String titulo = "";

		TestUtils.iniciarSesion(driver, "user1", "user1");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 10);

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:listaInbox");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		titulo = SeleniumUtils
				.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado:0:title", 10).get(0)
				.getText();

		TestUtils.clicarElemento(driver,
				"form-listado:tablalistado:0:finalizar");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		// Comprobamos que no está en inbox
		List<String> inboxTask = TestUtils
				.ordenarTareasInboxFechaPlaneada(true);

		int numTarea = 0;
		int j = 2;
		for (int i = 1; i <= inboxTask.size(); i++) {
			wait.until(ExpectedConditions.textToBePresentInElementLocated(
					By.id("form-listado:tablalistado:" + (i - 1) + ":title"),
					"tarea"));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":title"));
			assertNotEquals(titulo, elementos.get(0).getText());

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}

		// Comprobamos que no está en hoy

		// Comprobamos que no está en semana
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:listaSemana");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 20);

		List<String> semanaTask = TestUtils
				.ordenarTareasSemanaFechaPlaneada(true);

		numTarea = 0;
		j = 2;
		for (int i = 1; i <= semanaTask.size(); i++) {
			wait.until(ExpectedConditions.textToBePresentInElementLocated(
					By.id("form-listado:tablalistado:" + (i - 1) + ":title"),
					"tarea"));
			elementos = driver.findElements(By.id("form-listado:tablalistado:"
					+ (i - 1) + ":title"));
			assertNotEquals(titulo, elementos.get(0).getText());

			numTarea++;

			if (numTarea % 8 == 0) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")));
				driver.findElement(
						By.xpath("//div[@id='form-listado:tablalistado_paginator_top']/span[4]/span["
								+ j + "]")).click();
				j++;
				SeleniumUtils.EsperaCargaPagina(driver, "id",
						"form-listado:tablalistado", 20);
			}
		}
	}

	// PR33: Salir de sesión desde cuenta de administrador.
	@Test
	public void prueba33() {
		TestUtils.iniciarSesion(driver, "admin1", "admin1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: admin1", elementos.get(0).getText());

		TestUtils.cerrarSesion(driver);

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"input-usuario", 10);

		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		SeleniumUtils.textoPresentePagina(driver, "Contraseña");
	}

	// PR34: Salir de sesión desde cuenta de usuario normal.
	@Test
	public void prueba34() {
		TestUtils.iniciarSesion(driver, "user1", "user1");

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);

		assertEquals("Usuario: user1", elementos.get(0).getText());

		TestUtils.cerrarSesion(driver);

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"input-usuario", 10);

		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		SeleniumUtils.textoPresentePagina(driver, "Contraseña");
	}

	// PR35: Cambio del idioma por defecto a un segundo idioma. (Probar algunas
	// vistas)
	@Test
	public void prueba35() {
		// login.xhtml
		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		SeleniumUtils.textoPresentePagina(driver, "Contraseña");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-cabecera:idiomas",
				10);
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:idiomas",
				"form-cabecera:ingles");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-login", 20);

		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("form-login:usuario"), "Username"));
		SeleniumUtils.textoPresentePagina(driver, "Autentification");
		SeleniumUtils.textoPresentePagina(driver, "Username");
		SeleniumUtils.textoPresentePagina(driver, "Password");

		// registro.xhtml
		TestUtils.clicarElemento(driver, "enlace");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-registro", 10);

		SeleniumUtils.textoPresentePagina(driver, "Sign up");
		SeleniumUtils.textoPresentePagina(driver, "Username");
		SeleniumUtils.textoPresentePagina(driver, "Email");
		SeleniumUtils.textoPresentePagina(driver, "Password");
		SeleniumUtils.textoPresentePagina(driver, "Repeat password");

		// principalAdministrador.xhtml
		TestUtils.clicarElemento(driver, "enlace");

		TestUtils.iniciarSesion(driver, "admin1", "admin1");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 20);

		SeleniumUtils.textoPresentePagina(driver, "Home");
		SeleniumUtils.textoPresentePagina(driver, "Options");
		SeleniumUtils.textoPresentePagina(driver, "Username");

		// listadoUsuarios.xhtml
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:opcion2");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);

		SeleniumUtils.textoPresentePagina(driver, "Users list");
		SeleniumUtils.textoPresentePagina(driver, "Delete user");
		SeleniumUtils.textoPresentePagina(driver, "Username");

		// principalUsuario
		TestUtils.cerrarSesion(driver);

		TestUtils.iniciarSesion(driver, "user1", "user1");
		
		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"nombreUsuario", 10);
		
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("nombreUsuario"), "Username: user1"));
		
		SeleniumUtils.textoPresentePagina(driver, "Home");
		SeleniumUtils.textoPresentePagina(driver, "Lists");
		SeleniumUtils.textoPresentePagina(driver, "Username");
	}

	// PR36: Cambio del idioma por defecto a un segundo idioma y vuelta al
	// idioma por defecto. (Probar algunas vistas)
	@Test
	public void prueba36() throws InterruptedException {
		// Login.xhtml
		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		SeleniumUtils.textoPresentePagina(driver, "Contraseña");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-login", 10);
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:idiomas",
				"form-cabecera:ingles");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-login", 10);

		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("enlace"), "Sign up"));
		
		SeleniumUtils.textoPresentePagina(driver, "Username");
		SeleniumUtils.textoPresentePagina(driver, "Password");

		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:idiomas",
				"form-cabecera:español");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-login", 10);

		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("enlace"), "Registrarse"));
		
		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		SeleniumUtils.textoPresentePagina(driver, "Contraseña");

		// Registro.xhtml
		TestUtils.clicarElemento(driver, "enlace");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-registro", 10);
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:idiomas",
				"form-cabecera:español");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-registro", 10);

		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		SeleniumUtils.textoPresentePagina(driver, "Email");
		SeleniumUtils.textoPresentePagina(driver, "Contraseña");
		SeleniumUtils.textoPresentePagina(driver, "Repetir contraseña");

		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:idiomas",
				"form-cabecera:ingles");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-registro", 10);

		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("form-registro:Rcontraseña"), "Repeat password"));
		
		SeleniumUtils.textoPresentePagina(driver, "Username");
		SeleniumUtils.textoPresentePagina(driver, "Email");
		SeleniumUtils.textoPresentePagina(driver, "Password");
		SeleniumUtils.textoPresentePagina(driver, "Repeat password");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-registro", 10);
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:idiomas",
				"form-cabecera:español");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-registro", 10);

		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("form-registro:Rcontraseña"), "Repetir contraseña"));
		
		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		SeleniumUtils.textoPresentePagina(driver, "Email");
		SeleniumUtils.textoPresentePagina(driver, "Contraseña");
		SeleniumUtils.textoPresentePagina(driver, "Repetir contraseña");

		// principalAdministrador.xhtml
		TestUtils.clicarElemento(driver, "enlace");

		TestUtils.iniciarSesion(driver, "admin1", "admin1");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 20);
	
		SeleniumUtils.textoPresentePagina(driver, "Inicio");
		SeleniumUtils.textoPresentePagina(driver, "Opciones");
		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:idiomas",
				"form-cabecera:ingles");


		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("nombreUsuario"), "Username: admin1"));
		
		SeleniumUtils.textoPresentePagina(driver, "Home");
		SeleniumUtils.textoPresentePagina(driver, "Option");
		SeleniumUtils.textoPresentePagina(driver, "Username");
		
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:idiomas",
				"form-cabecera:español");

		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("nombreUsuario"), "Usuario: admin1"));
		
		SeleniumUtils.textoPresentePagina(driver, "Inicio");
		SeleniumUtils.textoPresentePagina(driver, "Opciones");
		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		
		// listadoUsuarios.xhtml
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:submenuOpciones", "form-cabecera:opcion2");

		SeleniumUtils.EsperaCargaPagina(driver, "id",
				"form-listado:tablalistado", 10);
		
		SeleniumUtils.textoPresentePagina(driver, "Listado de usuarios");
		SeleniumUtils.textoPresentePagina(driver, "Eliminar usuario");
		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:idiomas",
				"form-cabecera:ingles");


		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("nombreUsuario"), "Username: admin1"));
		
		SeleniumUtils.textoPresentePagina(driver, "Users list");
		SeleniumUtils.textoPresentePagina(driver, "Delete user");
		SeleniumUtils.textoPresentePagina(driver, "Username");
		
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:idiomas",
				"form-cabecera:español");

		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("nombreUsuario"), "Usuario: admin1"));
		
		SeleniumUtils.textoPresentePagina(driver, "Listado de usuarios");
		SeleniumUtils.textoPresentePagina(driver, "Eliminar usuario");
		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		
		// principalUsuario
		TestUtils.cerrarSesion(driver);

		TestUtils.iniciarSesion(driver, "user1", "user1");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "nombreUsuario", 10);
		
		SeleniumUtils.textoPresentePagina(driver, "Inicio");
		SeleniumUtils.textoPresentePagina(driver, "Listas");
		SeleniumUtils.textoPresentePagina(driver, "Añadir tarea");
		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:idiomas",
				"form-cabecera:ingles");

		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("nombreUsuario"), "Username: user1"));
		
		SeleniumUtils.textoPresentePagina(driver, "Home");
		SeleniumUtils.textoPresentePagina(driver, "Lists");
		SeleniumUtils.textoPresentePagina(driver, "Add task");
		SeleniumUtils.textoPresentePagina(driver, "Username");
		
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:idiomas",
				"form-cabecera:español");

		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("nombreUsuario"), "Usuario: user1"));
		
		SeleniumUtils.textoPresentePagina(driver, "Inicio");
		SeleniumUtils.textoPresentePagina(driver, "Listas");
		SeleniumUtils.textoPresentePagina(driver, "Añadir tarea");
		SeleniumUtils.textoPresentePagina(driver, "Usuario");
	}

	// PR37: Intento de acceso a un URL privado de administrador con un usuario
	// autenticado como usuario normal.
	@Test
	public void prueba37() {
		assertTrue(false);
	}

	// PR38: Intento de acceso a un URL privado de usuario normal con un usuario
	// no autenticado.
	@Test
	public void prueba38() {
		assertTrue(false);
	}
}