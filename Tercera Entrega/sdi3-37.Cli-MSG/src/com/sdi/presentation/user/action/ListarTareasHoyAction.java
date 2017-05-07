package com.sdi.presentation.user.action;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;

import com.sdi.dto.Task;
import com.sdi.presentation.user.Sesion;

import alb.util.console.Console;
import alb.util.menu.Action;

public class ListarTareasHoyAction implements Action {

	public ListarTareasHoyAction() {}	
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception {
		try {
			Sesion.getInstance().initialize();
			
			MapMessage map = Sesion.getInstance().getSession().createMapMessage();
			map.setString("accion", "listarTareasHoy");
			map.setLong("userId", Sesion.getInstance().getUser().getId());
			map.setString("username", Sesion.getInstance().getUser().getLogin());
            map.setString("password", Sesion.getInstance().getUser().getPassword());
			
			Destination destination = Sesion.getInstance().getSession().createTemporaryQueue();
            map.setJMSReplyTo(destination);
            
            Sesion.getInstance().getSession().createProducer(Sesion.getInstance().getQueue()).send(map);
            
            ObjectMessage tareas = (ObjectMessage) Sesion.getInstance().getSession().createConsumer(map.getJMSReplyTo())
                    .receive();
            
            print((LinkedList<Task>) tareas.getObject());
            
            Sesion.getInstance().getConection().close();
			
		} catch (JMSException e) {
            throw new RuntimeException(e);
        } finally {
            try {
            	 Sesion.getInstance().getConection().close();
            } catch (JMSException e) {
            	throw new RuntimeException(e);
            }
        }
	}
	
	private void print(List<Task> tareas) {
		Console.printf("%-20s%-20s%-20s%-25s%-20s\n", "ID", "TITULO", "FECHA_CREACION", "FECHA_FINALIZACION", "FECHA_PLANEADA");
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		for(Task t: tareas) {
			Console.printf("%-20s", t.getId());
			Console.printf("%-20s", t.getTitle());
			Console.printf("%-20s", formateador.format(t.getCreated()));
			Console.printf("%-25s", "");
			Console.printf("%-20s\n", formateador.format(t.getPlanned()));			
		}
	}
}