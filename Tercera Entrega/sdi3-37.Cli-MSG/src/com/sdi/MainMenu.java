package com.sdi;

import com.sdi.action.AñadirTareaNuevaAction;
import com.sdi.action.ListarTareasHoyAction;
import com.sdi.action.LoguearUsuario;
import com.sdi.action.MarcarTareaFinalizadaAction;
import alb.util.menu.BaseMenu;

public class MainMenu extends BaseMenu{
	
	public MainMenu() {			
		menuOptions = new Object[][] { 	
			{"USUARIO ", null},
			{ "Listar categorías", 				ListarTareasHoyAction.class}, 
			{ "Ver tareas de una categoría", 	MarcarTareaFinalizadaAction.class},
			{ "Marcar como finalizada", 		AñadirTareaNuevaAction.class }
		};
	}

	public static void main(String[] args) {
		LoguearUsuario.getInstance().execute();
		new MainMenu().execute();
	}
	
}
