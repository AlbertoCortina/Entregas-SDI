package com.sdi.presentation.admin;

import com.sdi.presentation.admin.action.DeshabilitarUsuarioAction;
import com.sdi.presentation.admin.action.EliminarUsuarioAction;
import com.sdi.presentation.admin.action.ListarUsuariosAction;

import alb.util.menu.BaseMenu;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] { 
			{ "Administrador", null },
			{ "Listar usuarios",		ListarUsuariosAction.class }, 
			{ "Deshabilitar usuario", 	DeshabilitarUsuarioAction.class },
			{ "Eliminar usuario", 		EliminarUsuarioAction.class },
		};
	}

	public static void main(String[] args) {
		new MainMenu().execute();
	}
}