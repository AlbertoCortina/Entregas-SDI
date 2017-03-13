package com.sdi.presentation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import alb.util.log.Log;

import com.sdi.business.AdminService;
import com.sdi.business.CategoryService;
import com.sdi.business.Services;
import com.sdi.business.TaskService;
import com.sdi.business.UserService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.dto.User;
import com.sdi.dto.types.UserStatus;

/**
 * Se encarga de:
 * 	- Iniciar base de datos
 * 	- Listar usuarios normales
 * 	- Cambiar el estado de un usuario (de habilitado a deshabilitado y viceversa)
 * @author Alberto Cortina
 *
 */
@ManagedBean(name="beanAdmin")
@SessionScoped
public class BeanAdmin {

	private final static int NUMERO_USUARIOS = 10;
	private final static int NUMERO_CATEGORIAS_POR_USUARIO = 3;
	private final static int NUMERO_TAREAS_HOY = 10;
	private final static int NUMERO_TAREAS_SEMANA = 10;
	private final static int NUMERO_TAREAS_RETRASADAS = 10;
	
	private User[] users = null;	
	
	public User[] getUsers() {
		return users;
	}

	public void setUsers(User[] users) {
		this.users = users;
	}

	public void iniciarBaseDatos() {
		try {
			borrarDatos();
			crearUsuarios();
			crearCategorias();
			crearTareasHoy();
			crearTareasSemana();
			crearTareasRetrasadas();			
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito: ", "Se han introducido de forma correcta los datos en la base de datos"));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			Log.debug("Base de datos iniciada correctamente");
			
		} catch (BusinessException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fallo: ", "Ha habido algún problema con la carga de datos en la base de datos"));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			Log.debug("El inicio de la base de datos falló");
		}			
	}
	
	public String listarUsuarios() {
		String resultado = "";
		UserService uService = Services.getUserService();
		
		try {
			List<User> usersConAdmin = uService.findAll();
			List<User> usersSinAdmin = new ArrayList<User>();
			for(User u: usersConAdmin){
				if(!u.getIsAdmin()) {
					usersSinAdmin.add(u);
				}
			}
			users = (User[]) usersSinAdmin.toArray(new User[0]);			
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito: ", "Se ha cargado con éxito la lista de usuarios"));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			Log.debug("La carga de la lista de usuarios se realizo correctamente");
			
			resultado = "EXITO";			
		} catch (BusinessException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fallo: ", "Ha habido algún problema cargando la lista de usuarios"));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			Log.debug("La carga de la lista de usuarios falló");
			resultado = "FALLO";
		}
		return resultado;
	}
	
	private void borrarDatos() throws BusinessException {
		Services.getTaskService().deleteAll();
		Services.getCategoryService().deleteAll();
		Services.getUserService().deleteAll();		
	}
	
	private void crearUsuarios() throws BusinessException {
		UserService uService = Services.getUserService();
		for (int i = 1; i <= NUMERO_USUARIOS; i++) {
			User u = new User();
			u.setEmail("user"+i+"@email.com");
			u.setIsAdmin(false);
			u.setLogin("user"+i);
			u.setPassword("user"+i);
			u.setStatus(UserStatus.ENABLED);
			
			uService.save(u);
		}
		
		users = (User[]) uService.findAll().toArray(new User[0]);
	}
	
	private void crearCategorias() throws BusinessException {
		CategoryService cService = Services.getCategoryService();
		for(User u: users){
			if(!u.getIsAdmin()) {
				for (int i = 1; i <= NUMERO_CATEGORIAS_POR_USUARIO; i++){
					Category c = new Category();
					c.setName("categoria"+i);
					c.setUserId(u.getId());
					
					cService.save(c);
				}
			}			
		}
	}
	
	private void crearTareasHoy() throws BusinessException {
		TaskService tService = Services.getTaskService();
		for(User u: users) {
			if (!u.getIsAdmin()) {
				for(int i = 1; i <= NUMERO_TAREAS_HOY; i++){
					Task t = new Task();
					t.setTitle("tarea"+i);
					t.setComments("comentario: "+t.getTitle()+"_"+u.getLogin());
					t.setCreated(new Date());
					t.setPlanned(new Date());
					t.setCategoryId(null);
					t.setUserId(u.getId());
					
					tService.save(t);
				}
			}
		}
	}
	
	private void crearTareasSemana() throws BusinessException {
		TaskService tService = Services.getTaskService();
		Calendar c = Calendar.getInstance();
		for(User u: users) {
			if (!u.getIsAdmin()) {
				for(int i = NUMERO_TAREAS_HOY + 1; i <= NUMERO_TAREAS_SEMANA + NUMERO_TAREAS_HOY; i++){
					c.setTime(new Date());
					c.add(Calendar.DAY_OF_MONTH, (int) Math.floor(Math.random()*(6-1+1)+1));
					Task t = new Task();
					t.setTitle("tarea"+i);
					t.setComments("comentario: "+t.getTitle()+"_"+u.getLogin());
					t.setCreated(new Date());
					t.setPlanned(c.getTime());
					t.setCategoryId(null);
					t.setUserId(u.getId());
					
					tService.save(t);
				}
			}
		}
	}
	
	private void crearTareasRetrasadas() throws BusinessException {
		TaskService tService = Services.getTaskService();
		CategoryService cService = Services.getCategoryService();
		Calendar c = Calendar.getInstance();
		
		for(User u: users) {
			Category c1 = cService.findByUserIdAndName(u.getId(), "categoria1");
			Category c2 = cService.findByUserIdAndName(u.getId(), "categoria2");
			Category c3 = cService.findByUserIdAndName(u.getId(), "categoria3");
			if (!u.getIsAdmin()) {
				for(int i = NUMERO_TAREAS_HOY + NUMERO_TAREAS_SEMANA + 1; i <= NUMERO_TAREAS_RETRASADAS + NUMERO_TAREAS_SEMANA + NUMERO_TAREAS_HOY; i++){
					c.setTime(new Date());
					c.add(Calendar.DAY_OF_MONTH, - (int) Math.floor(Math.random()*(6-1+1)+1));
					Task t = new Task();
					t.setTitle("tarea"+i);
					t.setComments("comentario: "+t.getTitle()+"_"+u.getLogin());
					t.setCreated(new Date());
					t.setPlanned(c.getTime());					
					t.setUserId(u.getId());
					
					if( i <= NUMERO_TAREAS_SEMANA + NUMERO_TAREAS_HOY + 3){
						t.setCategoryId(c1.getId());
					}
					else if (i > NUMERO_TAREAS_SEMANA + NUMERO_TAREAS_HOY + 3 && i <= NUMERO_TAREAS_SEMANA + NUMERO_TAREAS_HOY + 6) {
						t.setCategoryId(c2.getId());
					}
					else {
						t.setCategoryId(c3.getId());
					}
					
					tService.save(t);
				}
			}
		}
	}
	
	public void cambiarEstado(User user) {		
		UserStatus status = user.getStatus();
		
		try {
			AdminService aService = Services.getAdminService();
			UserService uService = Services.getUserService();
			
			if(status.equals(UserStatus.ENABLED)) {
				aService.disableUser(user.getId());
			}
			else{
				aService.enableUser(user.getId());
			}
			
			List<User> usersConAdmin = uService.findAll();
			List<User> usersSinAdmin = new ArrayList<User>();
			for(User u: usersConAdmin){
				if(!u.getIsAdmin()) {
					usersSinAdmin.add(u);
				}
			}
			users = (User[]) usersSinAdmin.toArray(new User[0]);
			
			status = status.equals(UserStatus.ENABLED) ? UserStatus.DISABLED:UserStatus.ENABLED;
			
			Log.debug("Se ha actualizado el estado del usuario "+user.getLogin().toUpperCase() +" a "+ status );
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito: ", "Se ha actualizado el estado del usuario "+user.getLogin().toUpperCase() +" a "+ status));
			
		} catch (BusinessException e) {
			user.setStatus(status);
			Log.debug("No se ha podido actualizar el estado del usuario "+user.getLogin());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "No se ha podido actualizar el estado del usuario "+user.getLogin()));
		}		
	}
}