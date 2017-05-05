package com.sdi.action;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import alb.util.console.Console;

public class LoguearUsuario extends AbstractAction{
	
	private Long id;
	private String login;
	private String password;
	
	private static LoguearUsuario instance;

	public LoguearUsuario(){}
	
	@Override
	public void execute() {
		String login = Console.readString("Introduzca login: ");
		String password = Console.readString("Introduzca contraseña");
		
		try {
			MapMessage msg = session.createMapMessage();
			msg.setString("command", "login");
			msg.setString("login", login);
			msg.setString("password", password);
			
			Destination destination = session.createTemporaryTopic();
			
			msg.setJMSReplyTo(destination);
			
			sender.send(msg);
			
			session.createConsumer(destination).setMessageListener(new LoginConsumerListener());
		} catch (JMSException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static LoguearUsuario getInstance(){
		if( instance == null)
			instance = new LoguearUsuario();
		return instance;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private class LoginConsumerListener implements MessageListener{

		@Override
		public void onMessage(Message msg) {
			try {
				if(msg instanceof MapMessage)
					process((MapMessage) msg);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
		private void process(MapMessage msg) throws JMSException{
			if(msg.getLong("id") != -1){
				LoguearUsuario lu = LoguearUsuario.getInstance();
				lu.setId(msg.getLong("id"));
				lu.setLogin(msg.getString("login"));
				lu.setPassword(msg.getString("password"));
			}
			
			System.out.println(msg.getString("message"));
		}
		
	}

}
