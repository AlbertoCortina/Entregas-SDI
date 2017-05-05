package com.sdi.action;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import com.sdi.util.Jndi;

import alb.util.menu.Action;

public abstract class AbstractAction implements Action{

	private static final String JMS_CONNECTION_FACTORY =
			"jms/RemoteConnectionFactory";
	private static final String CLIENT_QUEUE = "jms/queue/ClientQueue";
	
	static Connection con;
	static Session session;
	static MessageProducer sender;
	
	public AbstractAction() {
		try {
			ConnectionFactory factory = (ConnectionFactory) Jndi
					.find(JMS_CONNECTION_FACTORY);
			Destination queue = (Destination) Jndi.find(CLIENT_QUEUE);
			con = factory.createConnection("sdi", "password");
			session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			sender = session.createProducer(queue);
			con.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
