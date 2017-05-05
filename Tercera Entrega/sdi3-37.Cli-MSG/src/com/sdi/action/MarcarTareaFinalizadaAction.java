package com.sdi.action;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import alb.util.console.Console;

public class MarcarTareaFinalizadaAction extends AbstractAction {

	@Override
	public void execute() {
		Long taskId = Console.readLong();

		try {
			MapMessage msg = session.createMapMessage();

			msg.setString("command", "finalizar");
			msg.setLong("id", LoguearUsuario.getInstance().getId());
			msg.setString("login", LoguearUsuario.getInstance().getLogin());
			msg.setString("password", LoguearUsuario.getInstance().getPassword());
			msg.setLong("taskId", taskId);

			Destination destination = session.createTemporaryTopic();

			msg.setJMSReplyTo(destination);

			sender.send(msg);

			session.createConsumer(destination).setMessageListener(
					new FinishingTaskListener());
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
	
	private class FinishingTaskListener implements MessageListener{

		@Override
		public void onMessage(Message msg) {
			try {
				if(msg instanceof TextMessage)
					process((TextMessage) msg);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
		private void process(TextMessage msg) throws JMSException{
			System.out.println(msg.getText());
		}
		
	}

}
