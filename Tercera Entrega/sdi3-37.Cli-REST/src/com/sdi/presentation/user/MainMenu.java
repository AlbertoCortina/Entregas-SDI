package com.sdi.presentation.user;

import alb.util.menu.BaseMenu;

import com.sdi.presentation.user.action.CrearTareaAction;
import com.sdi.presentation.user.action.ListarCategoriasAction;
import com.sdi.presentation.user.action.MarcarTareaFinalizadaAction;
import com.sdi.presentation.user.action.TareasCategoriaAction;

public class MainMenu extends BaseMenu {
	
	public MainMenu() {			
		menuOptions = new Object[][] { 	
			{"USUARIO "+ Sesion.getInstance().getUser().getLogin(), null},
			{ "Listar categorías", 				ListarCategoriasAction.class}, 
			{ "Ver tareas de una categoría", 	TareasCategoriaAction.class},
			{ "Marcar tarea como finalizada", 	MarcarTareaFinalizadaAction.class },
			{ "Crear tarea",					CrearTareaAction.class}
		};
	}
}