package com.sdi.presentation.user.action;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;

import com.sdi.presentation.user.Sesion;

import alb.util.console.Console;
import alb.util.menu.Action;

public class MarcarTareaFinalizadaAction implements Action {

	public MarcarTareaFinalizadaAction() {}
	
	@Override
	public void execute() throws Exception {
		//Pedir datos
		Long tareaId = Console.readLong("Id de la tarea a finalizar");
		
		try {
			Sesion.getInstance().initialize();
			
			MapMessage map = Sesion.getInstance().getSession().createMapMessage();
			map.setString("accion", "finalizarTarea");
			map.setLong("userId", Sesion.getInstance().getUser().getId());
			map.setString("username", Sesion.getInstance().getUser().getLogin());
            map.setString("password", Sesion.getInstance().getUser().getPassword());
            map.setLong("tareaId", tareaId);
            
            Destination destination = Sesion.getInstance().getSession().createTemporaryQueue();
            map.setJMSReplyTo(destination);
            
            Sesion.getInstance().getSession().createProducer(Sesion.getInstance().getQueue()).send(map);
            
            map = (MapMessage) Sesion.getInstance().getSession().createConsumer(map.getJMSReplyTo())
                    .receive();
            
            Console.println(map.getString("msg"));					
			
			Sesion.getInstance().getSession().close();
			
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
}