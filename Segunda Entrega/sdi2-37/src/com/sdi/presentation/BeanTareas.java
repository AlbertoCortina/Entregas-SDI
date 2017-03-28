package com.sdi.presentation;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import alb.util.date.DateUtil;
import alb.util.log.Log;

import com.sdi.business.Services;
import com.sdi.business.TaskService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Task;
import com.sdi.presentation.util.Internacionalizar;

@ManagedBean(name="beanTareas")
@SessionScoped
public class BeanTareas {

	private List<Task> tareas = null;
	private List<Task> filtradas = null;
	private Date fechaHoy = DateUtil.today();
	private boolean finalizadas;

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
	
	public String listarTareasInbox() {
		String resultado = "";
		try {
			BeanUser u =  (BeanUser) FacesContext.getCurrentInstance().getExternalContext()
			.getSessionMap().get("userSession");			
			
			tareas = Services.getTaskService().findInboxTasksByUserId(u.getId());			
			
			Collections.sort(tareas, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return ((Task) o1).getPlanned().compareTo(((Task)o2).getPlanned());
				}
			});		
			filtradas = tareas;
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Internacionalizar.mensajes().getString("tituloExito") +" ", Internacionalizar.mensajes().getString("tituloMensajeExitoInbox")));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			
			resultado = "EXITO";
			Log.debug("Se listan tareas inbox");
		}
		catch(BusinessException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Internacionalizar.mensajes().getString("tituloError") +" ", "No se ha podido cargar la lista de tareas Inbox"));
			
			resultado = "ERROR";
			Log.debug("Error listando tareas inbox");
		}
		
		return resultado;
	}
	
	public String listarTareasHoy() {
		String resultado = "";
		
		try {
			BeanUser u =  (BeanUser) FacesContext.getCurrentInstance().getExternalContext()
			.getSessionMap().get("userSession");			
			
			tareas = Services.getTaskService().findTodayTasksByUserId(u.getId());	
			
			Collections.sort(tareas, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return ((Task) o1).getPlanned().compareTo(((Task)o2).getPlanned());
				}
			});	
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Internacionalizar.mensajes().getString("tituloExito") +" ", "Se ha cargado la lista de tareas Hoy"));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			
			resultado = "EXITO";
			Log.debug("Se listan tareas hoy");
		}
		catch(BusinessException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Internacionalizar.mensajes().getString("tituloError") +" ", "No se ha cargado la lista de tareas Hoy"));
			
			resultado = "ERROR";
			Log.debug("Error listando tareas hoy");
		}
		return resultado;
	}
	
	public String listarTareasSemana() {
		String resultado = "";
		try {
			BeanUser u =  (BeanUser) FacesContext.getCurrentInstance().getExternalContext()
			.getSessionMap().get("userSession");			
			
			tareas = Services.getTaskService().findWeekTasksByUserId(u.getId());			
			
			Collections.sort(tareas, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return ((Task) o1).getPlanned().compareTo(((Task)o2).getPlanned());
				}
			});					
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Internacionalizar.mensajes().getString("tituloExito") +" ", "Se ha cargado la lista de tareas Semana"));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			
			resultado = "EXITO";
			Log.debug("Se listan tareas semana");
		}
		catch(BusinessException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Internacionalizar.mensajes().getString("tituloError") +" ", "No se ha cargado la lista de tareas Semana"));
			
			resultado = "ERROR";
			Log.debug("Error listando tareas semana");
		}
		
		return resultado;
	}
	
	public String nombreCategoria(Long id) {
		String categoria = "";
		try {
			if(id == null) {
				return null;
			}
			categoria = Services.getCategoryService().findCategoryById(id).getName();
		} catch (BusinessException e) { }
		return categoria;
	}
	
	public void mostrarFinalizadas(){	
		if(finalizadas)
			finalizadas = false;
		else
			finalizadas = true;
		
		BeanUser u = (BeanUser) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("userSession");
		List<Task> tareasFinalizadas = null;
		
		try {
			tareasFinalizadas = Services.getTaskService().findFinishedInboxTasksByUserId(u.getId());
			
			Collections.sort(tareasFinalizadas, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return ((Task) o1).getPlanned().compareTo(((Task)o2).getPlanned());
				}
			});	
			
			if (finalizadas) {
				for(Task t: tareasFinalizadas) {
					tareas.add(t);
				}					
				filtradas = tareas;				
			}
			else {	
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
	
	public void finalizarTarea (Task tarea) {		
		try {			
			TaskService tService = Services.getTaskService();
			tarea.setFinished(DateUtil.today());
			tService.updateTask(tarea);			
			
			tareas.remove(tarea);
			filtradas = tareas;				
		
			if (finalizadas) {				
				tareas.add(tarea);								
				filtradas = tareas;				
			}			
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Internacionalizar.mensajes().getString("tituloExito") +" ", "Se ha marcado como finalizada la tarea "+tarea.getTitle().toUpperCase()));
			Log.debug("Tarea actualizada");
		} catch (BusinessException e) {			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Internacionalizar.mensajes().getString("tituloError") +" ", "No se ha podido marcar como finalizada la tarea "+tarea.getTitle().toUpperCase()));
			Log.debug("Error actualizando tarea");
		}
	}
}