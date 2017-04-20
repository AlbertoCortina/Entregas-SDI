package com.sdi.dto;

import java.io.Serializable;

public class UserInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	private int tareasCompletadas;
	private int tareasCompletadasRetrasadas;
	private int tareasPlanificadas;
	private int tareasSinPlanificar;
	
	public UserInfo() {}
	
	public UserInfo(User user, int tareasCompletadas,
			int tareasCompletadasRetrasadas, int tareasPlanificadas,
			int tareasSinPlanificar) {
		super();
		this.user = user;
		this.tareasCompletadas = tareasCompletadas;
		this.tareasCompletadasRetrasadas = tareasCompletadasRetrasadas;
		this.tareasPlanificadas = tareasPlanificadas;
		this.tareasSinPlanificar = tareasSinPlanificar;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getTareasCompletadas() {
		return tareasCompletadas;
	}

	public void setTareasCompletadas(int tareasCompletadas) {
		this.tareasCompletadas = tareasCompletadas;
	}

	public int getTareasCompletadasRetrasadas() {
		return tareasCompletadasRetrasadas;
	}

	public void setTareasCompletadasRetrasadas(int tareasCompletadasRetrasadas) {
		this.tareasCompletadasRetrasadas = tareasCompletadasRetrasadas;
	}

	public int getTareasPlanificadas() {
		return tareasPlanificadas;
	}

	public void setTareasPlanificadas(int tareasPlanificadas) {
		this.tareasPlanificadas = tareasPlanificadas;
	}

	public int getTareasSinPlanificar() {
		return tareasSinPlanificar;
	}

	public void setTareasSinPlanificar(int tareasSinPlanificar) {
		this.tareasSinPlanificar = tareasSinPlanificar;
	}
}