package com.sdi.presentation;

import java.io.Serializable;
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
import com.sdi.business.TaskService;
import com.sdi.business.UserService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.dto.User;
import com.sdi.dto.types.UserStatus;
import com.sdi.infrastructure.Factories;
import com.sdi.presentation.util.Internacionalizar;

/**
 * Se encarga de: - Iniciar base de datos - Listar usuarios normales - Cambiar
 * el estado de un usuario (de habilitado a deshabilitado y viceversa)
 * 
 * @author Alberto Cortina
 * 
 */
@ManagedBean(name = "beanAdmin")
@SessionScoped
public class BeanAdmin implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final static int NUMERO_USUARIOS = 10;
	private final static int NUMERO_CATEGORIAS_POR_USUARIO = 3;
	private final static int NUMERO_TAREAS_HOY = 10;
	private final static int NUMERO_TAREAS_SEMANA = 10;
	private final static int NUMERO_TAREAS_RETRASADAS = 10;

	private List<User> users = null;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
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

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Internacionalizar.mensajes().getString(
									"tituloExito")
									+ " ", Internacionalizar.mensajes()
									.getString("tituloMensajeExitoBase")));
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.setKeepMessages(true);

			Log.debug("Base de datos iniciada correctamente");
		} catch (BusinessException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Internacionalizar.mensajes().getString(
									"tituloError")
									+ " ", Internacionalizar.mensajes()
									.getString("tituloMensajeErrorBase")));
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.setKeepMessages(true);

			Log.debug("El inicio de la base de datos falló");
		}
	}

	public String listarUsuarios() {
		String resultado = "";
		UserService uService = Factories.services.getUserService();

		try {
			List<User> usersConAdmin = uService.findAll();
			List<User> usersSinAdmin = new ArrayList<User>();
			for (User u : usersConAdmin) {
				if (!u.getIsAdmin()) {
					usersSinAdmin.add(u);
				}
			}
			users = usersSinAdmin;

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Internacionalizar.mensajes().getString(
									"tituloExito")
									+ " ", Internacionalizar.mensajes()
									.getString("tituloMensajeExitoListar")));
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.setKeepMessages(true);
			resultado = "EXITO";
			Log.debug("La carga de la lista de usuarios se realizo correctamente");
		} catch (BusinessException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Internacionalizar.mensajes().getString(
									"tituloErrorListar")
									+ " ", Internacionalizar.mensajes()
									.getString("tituloMensajeErrorListar")));
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.setKeepMessages(true);
			resultado = "ERROR";
			Log.debug("La carga de la lista de usuarios falló");
		}
		return resultado;
	}

	private void borrarDatos() throws BusinessException {
		Factories.services.getTaskService().deleteAll();
		Factories.services.getCategoryService().deleteAll();
		Factories.services.getUserService().deleteAll();
	}

	private void crearUsuarios() throws BusinessException {
		UserService uService = Factories.services.getUserService();
		for (int i = 1; i <= NUMERO_USUARIOS; i++) {
			User u = new User();
			u.setEmail("user" + i + "@email.com");
			u.setIsAdmin(false);
			u.setLogin("user" + i);
			u.setPassword("user" + i);
			u.setStatus(UserStatus.ENABLED);

			uService.save(u);
		}

		users = uService.findAll();
	}

	private void crearCategorias() throws BusinessException {
		CategoryService cService = Factories.services.getCategoryService();
		for (User u : users) {
			if (!u.getIsAdmin()) {
				for (int i = 1; i <= NUMERO_CATEGORIAS_POR_USUARIO; i++) {
					Category c = new Category();
					c.setName("categoria" + i);
					c.setUserId(u.getId());

					cService.save(c);
				}
			}
		}
	}

	private void crearTareasHoy() throws BusinessException {
		TaskService tService = Factories.services.getTaskService();
		for (User u : users) {
			if (!u.getIsAdmin()) {
				for (int i = 1; i <= NUMERO_TAREAS_HOY; i++) {
					Task t = new Task();
					
					if(String.valueOf(i).length() == 1) {
						t.setTitle("tarea0" + i);
					}
					else {
						t.setTitle("tarea" + i);
					}
					
					t.setComments("comentario_ " + t.getTitle() + "_"
							+ u.getLogin());
					t.setCreated(new Date());
					t.setPlanned(new Date());
					t.setCategoryId(null);
					t.setUserId(u.getId());

					// OJO BORRAR ESTOs
					// t.setFinished(new Date());

					tService.save(t);
				}
			}
		}
	}

	private void crearTareasSemana() throws BusinessException {
		TaskService tService = Factories.services.getTaskService();
		Calendar c = Calendar.getInstance();
		for (User u : users) {
			if (!u.getIsAdmin()) {
				for (int i = NUMERO_TAREAS_HOY + 1; i <= NUMERO_TAREAS_SEMANA
						+ NUMERO_TAREAS_HOY; i++) {
					c.setTime(new Date());
					c.add(Calendar.DAY_OF_MONTH,
							(int) Math.floor(Math.random() * (6 - 1 + 1) + 1));
					Task t = new Task();
					
					if(String.valueOf(i).length() == 1) {
						t.setTitle("tarea0" + i);
					}
					else {
						t.setTitle("tarea" + i);
					}
					t.setComments("comentario_ " + t.getTitle() + "_"
							+ u.getLogin());
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
		TaskService tService = Factories.services.getTaskService();
		CategoryService cService = Factories.services.getCategoryService();
		Calendar c = Calendar.getInstance();

		for (User u : users) {
			Category c1 = cService.findByUserIdAndName(u.getId(), "categoria1");
			Category c2 = cService.findByUserIdAndName(u.getId(), "categoria2");
			Category c3 = cService.findByUserIdAndName(u.getId(), "categoria3");
			if (!u.getIsAdmin()) {
				for (int i = NUMERO_TAREAS_HOY + NUMERO_TAREAS_SEMANA + 1; i <= NUMERO_TAREAS_RETRASADAS
						+ NUMERO_TAREAS_SEMANA + NUMERO_TAREAS_HOY; i++) {
					c.setTime(new Date());
					c.add(Calendar.DAY_OF_MONTH,
							-(int) Math.floor(Math.random() * (6 - 1 + 1) + 1));
					Task t = new Task();
					
					if(String.valueOf(i).length() == 1) {
						t.setTitle("tarea0" + i);
					}
					else {
						t.setTitle("tarea" + i);
					}
					
					t.setComments("comentario_ " + t.getTitle() + "_"
							+ u.getLogin());
					t.setCreated(new Date());
					t.setPlanned(c.getTime());
					t.setUserId(u.getId());

					if (i <= NUMERO_TAREAS_SEMANA + NUMERO_TAREAS_HOY + 3) {
						t.setCategoryId(c1.getId());
					} else if (i > NUMERO_TAREAS_SEMANA + NUMERO_TAREAS_HOY + 3
							&& i <= NUMERO_TAREAS_SEMANA + NUMERO_TAREAS_HOY
									+ 6) {
						t.setCategoryId(c2.getId());
					} else {
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
			AdminService aService = Factories.services.getAdminService();
			UserService uService = Factories.services.getUserService();

			if (status.equals(UserStatus.ENABLED)) {
				aService.disableUser(user.getId());
			} else {
				aService.enableUser(user.getId());
			}

			List<User> usersConAdmin = uService.findAll();
			List<User> usersSinAdmin = new ArrayList<User>();
			for (User u : usersConAdmin) {
				if (!u.getIsAdmin()) {
					usersSinAdmin.add(u);
				}
			}
			users = usersSinAdmin;

			status = status.equals(UserStatus.ENABLED) ? UserStatus.DISABLED
					: UserStatus.ENABLED;

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Internacionalizar.mensajes().getString(
									"tituloExito") + " ", Internacionalizar
									.mensajes().getString(
											"tituloMensajeExitoEstado")
									+ " "
									+ user.getLogin().toUpperCase()
									+ " "
									+ Internacionalizar.mensajes().getString(
											"preposicion") + " " + status));

			Log.debug("Se ha actualizado el estado del usuario "
					+ user.getLogin().toUpperCase() + " a " + status);
		} catch (BusinessException e) {
			user.setStatus(status);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Internacionalizar.mensajes().getString(
									"tituloError") +" ", Internacionalizar
									.mensajes().getString(
											"tituloMensajeErrorEstado")
									+ " " + user.getLogin().toUpperCase()));

			Log.debug("No se ha podido actualizar el estado del usuario "
					+ user.getLogin().toUpperCase());
		}
	}

	public void eliminarUsuario(User user) {
		AdminService aService = Factories.services.getAdminService();
		UserService uService = Factories.services.getUserService();

		try {
			aService.deepDeleteUser(user.getId());

			List<User> usersConAdmin = uService.findAll();
			List<User> usersSinAdmin = new ArrayList<User>();
			for (User u : usersConAdmin) {
				if (!u.getIsAdmin()) {
					usersSinAdmin.add(u);
				}
			}
			users = usersSinAdmin;

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Internacionalizar.mensajes().getString(
									"tituloExito")
									+ " ", Internacionalizar.mensajes()
									.getString("tituloMensajeExitoEliminar")
									+ " " + user.getLogin().toUpperCase()));

			Log.debug("Se ha eliminado el usuario "
					+ user.getLogin().toUpperCase());
		} catch (BusinessException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Internacionalizar.mensajes().getString(
									"tituloError")
									+ " ", Internacionalizar.mensajes()
									.getString("tituloMensajeErrorEliminar")
									+ " " + user.getLogin().toUpperCase()));

			Log.debug("No se ha podido eliminar el usuario "
					+ user.getLogin().toUpperCase());
		}
	}
}