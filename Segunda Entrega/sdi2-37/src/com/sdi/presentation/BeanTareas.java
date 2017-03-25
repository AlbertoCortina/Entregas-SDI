package com.sdi.presentation;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.context.FacesContext;

import alb.util.date.DateUtil;
import alb.util.log.Log;

import com.sdi.business.Services;
import com.sdi.business.TaskService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Task;
import com.sdi.dto.User;

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
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito: ", "Se ha cargado la lista de tareas Inbox"));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			
			resultado = "EXITO";
			Log.debug("Se listan tareas inbox");
		}
		catch(BusinessException e) {
			resultado = "ERROR";
			Log.debug("Error listando tareas inbox");
		}
		
		return resultado;
	}
	
	public String listarTareasHoy() {
		String resultado = "";
		return null;
	}
	
	public String listarTareasSemana() {
		String resultado = "";
		return null;
	}
	
	public String nombreCategoria(Long id) {
		try {
			if(id != null) {
				return Services.getCategoryService().findCategoryById(id).getName();
			}			
		} catch (BusinessException e) { }
		return null;
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
			
			Log.debug("Tarea actualizada");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito: ", "Se ha marcado como finalizada la tarea "+tarea.getTitle()));
			
		} catch (BusinessException e) {
			Log.debug("Error actualizando tarea");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error: ", "No se ha podido marcar como finalizada la tarea "+tarea.getTitle()));
		}
	}
}
