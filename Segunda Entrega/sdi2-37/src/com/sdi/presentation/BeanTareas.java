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

import com.sdi.business.Services;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Task;
import com.sdi.dto.User;

@ManagedBean(name="beanTareas")
@SessionScoped
public class BeanTareas {

	private List<Task> tareas = null;
	private List<Task> filtradas = null;
	private Date fechaHoy = DateUtil.today();

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
			filtradas = tareas;
			Collections.sort(filtradas, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return ((Task) o1).getPlanned().compareTo(((Task)o2).getPlanned());
				}
			});
			
			resultado = "EXITO";
		}
		catch(BusinessException e) {
			resultado = "ERROR";
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito: ", "Se ha cargado la lista de tareas Inbox"));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return resultado;
	}
}
