package com.sdi.tests.utils;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.sdi.business.Services;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Task;
import com.sdi.dto.User;

public class TestUtils {
	
	static List<WebElement> elementos = null;
	
	/**
	 * Método que inicia sesión a un usuario
	 * 
	 * @param driver
	 * @param login
	 * @param password
	 */
	public static void iniciarSesion(WebDriver driver, String login, String password) {
		//Pone el login
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "form-login:input-usuario", 10);
		elementos.get(0).sendKeys(login);
		
		//Pone la contraseña
		elementos = driver.findElements(By.id("form-login:input-contraseña"));
		elementos.get(0).sendKeys(password);
		
		//Clica el botón iniciar sesión
		elementos = driver.findElements(By.id("form-login:botonIniciarSesion"));
		elementos.get(0).click();
	}
	
	/**
	 * Método para cerrar sesión de un usuario
	 * @param driver
	 */
	public static void cerrarSesion(WebDriver driver) {
		elementos = driver.findElements(By.id("form-cabecera:botonCerrarSesion"));
		elementos.get(0).click();
	}
	
	/**
	 * Método que inicia la base de datos (se supone que el administrador ya está logueado)
	 * @param driver
	 */
	public static void iniciarBaseDeDatos(WebDriver driver) {
		//Clica el la opción "Iniciar base de datos"
		SeleniumUtils.ClickSubopcionMenuHover(driver, "form-cabecera:submenuOpciones", "form-cabecera:opcion1");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "mensajes", 10);
		
		assertEquals("Exito: Se han introducido de forma correcta los datos en la base de datos", elementos.get(0).getText());
	}
	
	/**
	 * Método para clicar sobre un elemento que no puede hacerse directamente. Ej: un enlace
	 * dentro de una tabla
	 * @param driver
	 * @param id
	 */
	public static void clicarElemento(WebDriver driver, String id) {
		elementos = driver.findElements(By.id(id));
		
		Actions action = new Actions(driver);		
		action.moveToElement(elementos.get(0)).perform();		
		action.click().perform();
	}
	
	/**
	 * Método que nos ordena los usuarios (quitando el admin) por login
	 * @param ascendentemente
	 * @return
	 */
	public static List<String> ordenarPorLogin(boolean ascendentemente) {
		List<String> usuariosOrdenados = new ArrayList<String>();
		List<User> usuarios = null;
		
		try {
			usuarios = Services.getUserService().findAll();
			
			if(ascendentemente) {
				Collections.sort(usuarios, new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((User) o1).getLogin().compareTo(((User)o2).getLogin());
					}
				});
			}
			else {
				Collections.sort(usuarios, new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((User) o2).getLogin().compareTo(((User)o1).getLogin());
					}
				});
			}
			
			for(User u: usuarios) {
				if(!u.getLogin().startsWith("admin1"))
					usuariosOrdenados.add(u.getLogin());
			}
			
		} catch (BusinessException e) { }	
		
		return usuariosOrdenados;
	}
	
	/**
	 * Método que nos ordena los usuarios (quitando el admin) por email
	 * @param ascendentemente
	 * @return
	 */
	public static List<String> ordenarPorEmail(boolean ascendentemente) {
		List<String> usuariosOrdenados = new ArrayList<String>();
		List<User> usuarios = null;
		
		try {
			usuarios = Services.getUserService().findAll();
			
			if(ascendentemente) {
				Collections.sort(usuarios, new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((User) o1).getLogin().compareTo(((User)o2).getLogin());
					}
				});
			}
			else {
				Collections.sort(usuarios, new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((User) o2).getLogin().compareTo(((User)o1).getLogin());
					}
				});
			}
			
			for(User u: usuarios) {
				if(!u.getLogin().startsWith("admin"))
					usuariosOrdenados.add(u.getEmail());
			}
			
		} catch (BusinessException e) { }	
		
		return usuariosOrdenados;
	}
	
	/**
	 * Método que nos ordena los usuarios (quitando el admin) por status
	 * @param ascendentemente
	 * @return
	 */
	public static List<String> ordenarPorStatus(boolean ascendentemente) {
		List<String> usuariosOrdenados = new ArrayList<String>();
		List<User> usuarios = null;
		
		try {
			usuarios = Services.getUserService().findAll();
			
			if(ascendentemente) {
				Collections.sort(usuarios, new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((User) o1).getStatus().compareTo(((User)o2).getStatus());
					}
				});
			}
			else {
				Collections.sort(usuarios, new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((User) o2).getStatus().compareTo(((User)o1).getStatus());
					}
				});
			}
			
			for(User u: usuarios) {
				if(!u.getLogin().startsWith("admin"))
					usuariosOrdenados.add(String.valueOf(u.getStatus()));
			}
			
		} catch (BusinessException e) { }	
		
		return usuariosOrdenados;
	}
	
	/**
	 * Método que nos ordena las tareas Inbox (quitando el admin) por fecha planeada
	 * @param ascendentemente
	 * @return
	 */
	public static List<String> ordenarTareasInboxFechaPlaneada(boolean ascendentemente) {
		List<String> tareasOrdenados = new ArrayList<String>();
		List<Task> tasks = null;
		List<User> usuarios = null;
		Long id = 0L;
		DateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			usuarios = Services.getUserService().findAll();
			
			for(User u: usuarios) {
				if(u.getLogin().equals("user1")){
					id = u.getId();
				}
			}
			
			tasks = Services.getTaskService().findInboxTasksByUserId(id);
			
			if(ascendentemente) {
				Collections.sort(tasks, new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((Task) o1).getPlanned().compareTo(((Task)o2).getPlanned());
					}
				});
			}
			else {
				Collections.sort(tasks, new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((Task) o2).getPlanned().compareTo(((Task)o1).getPlanned());
					}
				});
			}		
			
			for (Task t: tasks) {
				tareasOrdenados.add(formateador.format(t.getPlanned()));
			}
			
		} catch (BusinessException e) { }	
		
		return tareasOrdenados;
	}
	
	/**
	 * Método que nos ordena las tareas Inbox (quitando el admin) por fecha planeada
	 * @param ascendentemente
	 * @return
	 */
	public static List<String> ordenarTareasSemanaFechaPlaneada(boolean ascendentemente) {
		List<String> tareasOrdenados = new ArrayList<String>();
		List<Task> tasks = null;
		List<User> usuarios = null;
		Long id = 0L;
		DateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			usuarios = Services.getUserService().findAll();
			
			for(User u: usuarios) {
				if(u.getLogin().equals("user1")){
					id = u.getId();
				}
			}
			
			tasks = Services.getTaskService().findWeekTasksByUserId(id);
			
			if(ascendentemente) {
				Collections.sort(tasks, new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((Task) o1).getPlanned().compareTo(((Task)o2).getPlanned());
					}
				});
			}
			else {
				Collections.sort(tasks, new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((Task) o2).getPlanned().compareTo(((Task)o1).getPlanned());
					}
				});
			}		
			
			for (Task t: tasks) {
				tareasOrdenados.add(formateador.format(t.getPlanned()));
			}
			
		} catch (BusinessException e) { }	
		
		return tareasOrdenados;
	}
	
	public static List<String> ordenarTareasSemanaTitulo(boolean ascendentemente) {
		List<String> tareasOrdenados = new ArrayList<String>();
		List<Task> tasks = null;
		List<User> usuarios = null;
		Long id = 0L;
		
		try {
			usuarios = Services.getUserService().findAll();
			
			for(User u: usuarios) {
				if(u.getLogin().equals("user1")){
					id = u.getId();
				}
			}
			
			tasks = Services.getTaskService().findWeekTasksByUserId(id);
			
			if(ascendentemente) {
				Collections.sort(tasks, new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((Task) o1).getTitle().compareTo(((Task)o2).getTitle());
					}
				});
			}
			else {
				Collections.sort(tasks, new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((Task) o2).getTitle().compareTo(((Task)o1).getTitle());
					}
				});
			}		
			
			for (Task t: tasks) {
				tareasOrdenados.add(t.getTitle());
			}
			
		} catch (BusinessException e) { }	
		
		return tareasOrdenados;
	}
	
	/**
	 * Método que filtrar las tareas por el nombre según una cadena que indica el usuario
	 * @param driver
	 * @param cadena
	 * @return
	 */
	public static List<String> filtrarTareas(WebDriver driver, String cadena) {
		List<String> tareasFiltradas = new ArrayList<String>();
		List<Task> tasks = null;
		List<User> usuarios = null;
		Long id = 0L;
		
		try {
			usuarios = Services.getUserService().findAll();
			
			for(User u: usuarios) {
				if(u.getLogin().equals("user1")){
					id = u.getId();
				}
			}
			
			tasks = Services.getTaskService().findInboxTasksByUserId(id);
			
			Collections.sort(tasks, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return ((Task) o1).getPlanned().compareTo(((Task)o2).getPlanned());
				}
			});
			
			for (Task t: tasks){
				if(t.getTitle().contains(cadena)) {
					tareasFiltradas.add(t.getTitle());
				}
			}
			
		} catch (BusinessException e) { }
		
		return tareasFiltradas;
	}

	
}