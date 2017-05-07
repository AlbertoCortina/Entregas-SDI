package com.sdi.presentation.user;

import com.sdi.presentation.user.action.CrearTareaAction;
import com.sdi.presentation.user.action.ListarTareasHoyAction;
import com.sdi.presentation.user.action.MarcarTareaFinalizadaAction;

import alb.util.menu.BaseMenu;

public class MainMenu extends BaseMenu {
	
	public MainMenu() {			
		menuOptions = new Object[][] { 	
			{"USUARIO "+ Sesion.getInstance().getUser().getLogin(), null},
			{ "Ver tareas Hoy y retrasadas", 	ListarTareasHoyAction.class}, 			
			{ "Marcar tarea como finalizada", 	MarcarTareaFinalizadaAction.class},
			{ "Crear tarea",					CrearTareaAction.class}
		};
	}
}