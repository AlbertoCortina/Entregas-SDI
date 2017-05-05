package com.sdi.action;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import alb.util.console.Console;

public class AñadirTareaNuevaAction extends AbstractAction {

	@Override
	public void execute() {
		String title = Console.readString("Títuo de la tarea: ");
		String comentary = Console.readString("Comentario de la tarea: ");
		Long categoryID = Console.readLong("Id de la categoria de la tarea: ");
		
		try {
			MapMessage msg = session.createMapMessage();
			msg.setString("command", "new");
			msg.setLong("id", LoguearUsuario.getInstance().getId());
			msg.setString("login", LoguearUsuario.getInstance().getLogin());
			msg.setString("password", LoguearUsuario.getInstance().getPassword());
			msg.setString("title", title);
			msg.setString("comentary", comentary);
			msg.setLong("categoryId", categoryID);
			
			Destination destination = session.createTemporaryTopic();
			
			msg.setJMSReplyTo(destination);
			
			sender.send(msg);
			
			session.createConsumer(destination).setMessageListener(new NewTaskListener());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private class NewTaskListener implements MessageListener{

		@Override
		public void onMessage(Message msg) {
			try {
				if(msg instanceof TextMessage)
					process((TextMessage)msg);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

		private void process(TextMessage msg) throws JMSException {
			System.out.println(msg.getText());
		}
		
	}

}
