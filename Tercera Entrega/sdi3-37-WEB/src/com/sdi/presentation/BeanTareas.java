package com.sdi.presentation;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import alb.util.date.DateUtil;
import alb.util.log.Log;

import com.sdi.business.TaskService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.infrastructure.Factories;
import com.sdi.presentation.util.Internacionalizar;

@ManagedBean(name = "beanTareas")
@SessionScoped
public class BeanTareas implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Task> tareas = null;
	private List<Task> filtradas = null;
	private Date fechaHoy;
	private boolean finalizadas;
	private String listado;

	private String nombreCategoria;
	
	// Para añadir tarea
	private String nombre;
	private String comentario;
	private Date fechaPlaneada;
	private Long idCategoria;
	private List<Category> categorias = null;

	@PostConstruct
	public void init() throws BusinessException {
		fechaHoy = DateUtil.today();

		BeanUser u = (BeanUser) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("userSession");

		categorias = Factories.services.getCategoryService().findCategoriesByUserId(
				u.getId());
	}

	public List<Task> getTareas() {
		return tareas;
	}

	public void setTareas(List<Task> filtradas) {
		this.filtradas = filtradas;
	}

	public List<Task> getFiltradas() {
		return filtradas;
	}

	public void setFiltradas(List<Task> filtradas) {
		this.filtradas = filtradas;
	}

	public Date getFechaHoy() {
		return fechaHoy;
	}

	public void setFechaHoy(Date fechaHoy) {
		this.fechaHoy = fechaHoy;
	}
	
	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombre) {
		this.nombreCategoria = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFechaPlaneada() {
		return fechaPlaneada;
	}

	public void setFechaPlaneada(Date fechaPlaneada) {
		this.fechaPlaneada = fechaPlaneada;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public List<Category> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Category> categorias) {
		this.categorias = categorias;
	}

	public String listarTareasInbox() {
		String resultado = "";
		try {
			BeanUser u = (BeanUser) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("userSession");

			tareas = Factories.services.getTaskService()
					.findInboxTasksByUserId(u.getId());

			Collections.sort(tareas, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return ((Task) o1).getPlanned().compareTo(
							((Task) o2).getPlanned());
				}
			});
			filtradas = tareas;

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Internacionalizar.mensajes().getString(
									"tituloExito")
									+ " ", Internacionalizar.mensajes()
									.getString("tituloMensajeExitoInbox")));
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.setKeepMessages(true);

			resultado = "EXITO";
			listado = "inbox";
			Log.debug("Se listan tareas inbox");
		} catch (BusinessException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Internacionalizar.mensajes().getString(
									"tituloError")
									+ " ",
							Internacionalizar.mensajes().getString("tituloMensajeErrorInbox")));

			resultado = "ERROR";
			Log.debug("Error listando tareas inbox");
		}

		return resultado;
	}

	public String listarTareasHoy() {
		String resultado = "";

		try {
			BeanUser u = (BeanUser) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("userSession");

			tareas = Factories.services.getTaskService()
					.findTodayTasksByUserId(u.getId());

			Collections.sort(tareas, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return ((Task) o1).getPlanned().compareTo(
							((Task) o2).getPlanned());
				}
			});

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Internacionalizar.mensajes().getString(
									"tituloExito")
									+ " ",
							Internacionalizar.mensajes().getString("tituloMensajeExitoHoy")));
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.setKeepMessages(true);

			resultado = "EXITO";
			listado = "hoy";
			Log.debug("Se listan tareas hoy");
		} catch (BusinessException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Internacionalizar.mensajes().getString(
									"tituloError")
									+ " ",
							Internacionalizar.mensajes().getString("tituloMensajeErrorHoy")));

			resultado = "ERROR";
			Log.debug("Error listando tareas hoy");
		}
		return resultado;
	}

	public String listarTareasSemana() {
		String resultado = "";
		try {
			BeanUser u = (BeanUser) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("userSession");

			tareas = Factories.services.getTaskService().findWeekTasksByUserId(u.getId());

			Collections.sort(tareas, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return ((Task) o1).getPlanned().compareTo(
							((Task) o2).getPlanned());
				}
			});

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Internacionalizar.mensajes().getString(
									"tituloExito")
									+ " ",
							Internacionalizar.mensajes().getString("tituloMensajeExitoSemana")));
			FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.setKeepMessages(true);

			resultado = "EXITO";
			listado = "semana";
			Log.debug("Se listan tareas semana");
		} catch (BusinessException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Internacionalizar.mensajes().getString(
									"tituloError")
									+ " ",
							Internacionalizar.mensajes().getString("tituloMensajeErrorSemana")));

			resultado = "ERROR";
			Log.debug("Error listando tareas semana");
		}

		return resultado;
	}

	public String nombreCategoria(Long id) {
		String categoria = "";
		try {
			if (id == null) {
				return null;
			}
			categoria = Factories.services.getCategoryService().findCategoryById(id)
					.getName();
		} catch (BusinessException e) { }
		
		return categoria;
	}
	
	public void mostrarFinalizadas() {
		if (finalizadas)
			finalizadas = false;
		else
			finalizadas = true;

		BeanUser u = (BeanUser) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("userSession");
		List<Task> tareasFinalizadas = null;

		try {
			tareasFinalizadas = Factories.services.getTaskService()
					.findFinishedInboxTasksByUserId(u.getId());

			Collections.sort(tareasFinalizadas, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return ((Task) o1).getPlanned().compareTo(
							((Task) o2).getPlanned());
				}
			});

			if (finalizadas) {
				for (Task t : tareasFinalizadas) {
					tareas.add(t);
				}
				filtradas = tareas;
			} else {
				for (Task t2 : tareasFinalizadas) {
					tareas.remove(t2);
				}
				filtradas = tareas;
			}

			Log.debug("Mostrar finalizadas correcto");
		} catch (BusinessException e) {
			Log.debug("Mostrar finalizadas incorrecto");
		}
	}

	public void finalizarTarea(Task tarea) {
		try {
			TaskService tService = Factories.services.getTaskService();
			tarea.setFinished(DateUtil.today());
			tService.updateTask(tarea);

			tareas.remove(tarea);
			filtradas = tareas;

			System.out.println(listado);
			
			if (finalizadas && listado.equals("inbox")) {
				tareas.add(tarea);
				filtradas = tareas;
			}

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Internacionalizar.mensajes().getString(
									"tituloExito")
									+ " ",
							Internacionalizar.mensajes().getString("tituloMensajeExitoMarcarFinalizada")+" "+
									 tarea.getTitle().toUpperCase()));
			Log.debug("Tarea actualizada");
		} catch (BusinessException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Internacionalizar.mensajes().getString(
									"tituloError")
									+ " ",
							Internacionalizar.mensajes().getString("tituloMensajeErrorMarcarFinalizada")+" "
									+ tarea.getTitle().toUpperCase()));
			Log.debug("Error actualizando tarea");
		}
	}

	public String añadirTarea() {
		String resultado = "";
		BeanUser u = (BeanUser) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("userSession");
		Task t = new Task();

		t.setTitle(nombre);
		t.setComments(comentario);
		t.setCreated(new Date());
		t.setFinished(null);
		t.setPlanned(fechaPlaneada);
		t.setUserId(u.getId());

		if (idCategoria != null) {
			t.setCategoryId(idCategoria);
		}

		try {
			Factories.services.getTaskService().createTask(t);

			if (t.getCategoryId() == null) {
				listarTareasInbox();
				
				resultado = "EXITO_INBOX";
			} else if (t.getCategoryId() != null
					&& t.getPlanned().compareTo(fechaHoy) == 0) {
				listarTareasHoy();
				resultado = "EXITO_HOY";
			} else if (t.getCategoryId() != null
					&& t.getPlanned().compareTo(new Date()) > 0) {
				listarTareasSemana();
				resultado = "EXITO_SEMANA";
			}

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Internacionalizar.mensajes().getString(
									"tituloExito")
									+ " ",
							Internacionalizar.mensajes().getString("tituloMensajeExitoAñadir")+" "
									+ t.getTitle().toUpperCase()));
			
			Log.debug("Todo bien añadiendo tarea");
		} catch (BusinessException e) {
			resultado = "ERROR";
			Log.debug("Error añadiendo tarea");
		}

		nombre = null;
		fechaPlaneada = null;
		comentario = null;
		idCategoria = null;

		return resultado;
	}

	public void editarTarea(Task t) {		
		try {		
			Factories.services.getTaskService().updateTask(t);
			Log.debug("Se actualiza la tarea");
		} catch(BusinessException e){
			Log.debug("No se actualiza la tarea");
		}
	}
}