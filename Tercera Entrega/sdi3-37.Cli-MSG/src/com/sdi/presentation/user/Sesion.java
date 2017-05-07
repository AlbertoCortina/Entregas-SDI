package com.sdi.presentation.user;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import com.sdi.dto.User;
import com.sdi.util.Jndi;

public class Sesion {

	private static Sesion INSTANCE = null;	
	private User user;
	
	private final static String JMS_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
	private final static String CLIENT_QUEUE = "jms/queue/ClientQueue";

	private static ConnectionFactory factory;
	private static Connection conection;
	private static Session session;
	private static Destination queue;
	
	private Sesion() {}
	
	public static Sesion getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Sesion();
		}
		return INSTANCE;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public ConnectionFactory getFactory() {
		return factory;
	}

	public Connection getConection() {
		return conection;
	}

	public Session getSession() {
		return session;
	}

	public Destination getQueue() {
		return queue;
	}

	public void initialize() {
		try {
		    factory = (ConnectionFactory) Jndi.find(JMS_CONNECTION_FACTORY);
		    conection = factory.createConnection("sdi", "password");
		    session = conection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		    queue = (Destination) Jndi.find(CLIENT_QUEUE);
		    conection.start();
		} 
		catch (JMSException e) {
		    throw new RuntimeException(e);
		}
	}	
}