package com.sdi.action;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.sdi.dto.Task;

public class ListarTareasHoyAction extends AbstractAction{

	
	
	@Override
	public void execute() {
		MapMessage msg;
		try {
			msg = session.createMapMessage();
		
		
		msg.setString("command", "listar");
		msg.setLong("id", LoguearUsuario.getInstance().getId());
		msg.setString("login", LoguearUsuario.getInstance().getLogin());
		msg.setString("password", LoguearUsuario.getInstance().getPassword());
		
		Destination destination = session.createTemporaryTopic();
		
		msg.setJMSReplyTo(destination);
		
		sender.send(msg);
		
		session.createConsumer(destination).setMessageListener(new ListTasksListener());
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
	
	private class ListTasksListener implements MessageListener{

		@Override
		public void onMessage(Message msg) {
			try {
				if(msg instanceof ObjectMessage)
					process((ObjectMessage) msg);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
		private void process(ObjectMessage msg) throws JMSException{
			@SuppressWarnings("unchecked")
			List<Task> response = (ArrayList<Task>) msg.getObject();
			printHeader();
			for(Task task : response)
				printTask(task);
		}
		
		private void printHeader(){
			
		}
		
		private void printTask(Task t){
			
		}
		
	}
	
}
