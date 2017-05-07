package com.sdi.presentation.user;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import com.sdi.dto.User;

import alb.util.console.Console;

public class Login {

	public static void main(String[] args) {
		//Pedir datos
		String username = Console.readString("Usuario");
		String password = Console.readString("Contraseña");
		
		login(username, password);
		
		if(Sesion.getInstance().getUser() != null) {
			new MainMenu().execute();
		}		
	}
	
	private static void login(String username, String password) {
		try {
			Sesion.getInstance().initialize();
			
            MapMessage map = Sesion.getInstance().getSession().createMapMessage();
            map.setString("accion", "login");
            map.setString("username", username);
            map.setString("password", password);
          
            Destination destination = Sesion.getInstance().getSession().createTemporaryQueue();
            map.setJMSReplyTo(destination);
 
            Sesion.getInstance().getSession().createProducer(Sesion.getInstance().getQueue()).send(map);
 
            map = (MapMessage) Sesion.getInstance().getSession().createConsumer(map.getJMSReplyTo())
                    .receive();              
 
            User user = null;
            if(!map.getString("userId").equals("-1")) {
            	user = new User();
                user.setId(Long.valueOf(map.getString("userId")));
                user.setLogin(map.getString("username"));
                user.setPassword(map.getString("password"));                
            }  
            else {
            	Console.println(map.getString("msg"));
            }
            
            Sesion.getInstance().setUser(user);
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
}