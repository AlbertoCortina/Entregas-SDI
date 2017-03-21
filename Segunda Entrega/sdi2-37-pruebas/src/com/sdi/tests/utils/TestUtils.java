package com.sdi.tests.utils;

import static org.junit.Assert.assertEquals;

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
		//Pone el lgoin
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
	
}