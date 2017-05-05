package com.sdi.business.integration;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.sdi.business.TaskService;
import com.sdi.business.UserService;
import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.task.LocalTaskService;
import com.sdi.business.impl.user.LocalUserService;
import com.sdi.dto.Task;
import com.sdi.dto.User;

@MessageDriven(activationConfig = 
	{ @ActivationConfigProperty(
			propertyName = "destination", 
			propertyValue = "jms/queue/ClientQueue") 
	})
public class ClientListener implements MessageListener{
 
    private Connection connection;
    private Session session;
    private MessageProducer replyProducer;
	
	@EJB(beanInterface = LocalTaskService.class)
	private TaskService taskService;
	
	@EJB(beanInterface = LocalUserService.class)
	private UserService userService;
	
	@Resource(mappedName = " java:/ConnectionFactory")
	private ConnectionFactory factory;
	
	
	@Override
	public void onMessage(Message msg) {
		try {
				connection = factory.createConnection("sdi","password");
	            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	            replyProducer = session.createProducer(null);
	            
	            if(msg instanceof MapMessage)
	            	process((MapMessage) msg);
				
		} catch (BusinessException e) {
			
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}finally{
            try {
				session.close();
				connection.close();
			} catch (JMSException e) {
				throw new RuntimeException(e);
			}
            
		}
	}
	
	private void process(MapMessage msg) throws JMSException, BusinessException{
		if (autenticarUser(msg)) {
			String cmd = msg.getString("command");
			if (cmd.equals("login"))
				doLogin(msg);
			else if (cmd.equals("lista"))
				doGetList(msg);
			else if (cmd.equals("finalizar"))
				doFinalizarTarea(msg);
			else if (cmd.equals("new"))
				doCrearTarea(msg);
		}else{
			sendFalloAutenticar(msg);
		}
	}
	
	private boolean autenticarUser(MapMessage msg) throws JMSException, BusinessException{		
		return userService.findLoggableUser(msg.getString("login"),
				msg.getString("password")) != null;
	}
	
	private void sendFalloAutenticar(MapMessage msg) throws JMSException{
		MapMessage response = session.createMapMessage();
		
		response.setLong("id", -1);
		response.setString("message", "Fallo en la autenticación de usuario");
		
        replyProducer.send(msg.getJMSReplyTo(), response);
        
//        Destination log = (Destination) Jndi.find(LOG_QUEUE);
//		session.createProducer(log).send(incorrectCredentials);
	}
	
	private void doLogin(MapMessage msg) throws JMSException, BusinessException{
		MapMessage response = session.createMapMessage();
		User u = userService.findLoggableUser(msg.getString("login"),
				msg.getString("password"));
		
		if(u != null){
			response.setLong("id", u.getId());
			response.setString("login", u.getLogin());
			response.setString("password", u.getPassword());
			response.setString("message", "Usuario: " + u.getLogin() + " logueado correctamente.");
		}{
			response.setLong("id", -1);
			response.setString("message", "Usuario o contraseña incorrectos");
		}
	}
	
	private void doGetList(MapMessage msg) throws BusinessException, JMSException{
		ObjectMessage response = session.createObjectMessage();
		response.setObject((ArrayList<Task>)taskService.findTodayTasksByUserId(msg.getLong("id")));
		 
        replyProducer.send(msg.getJMSReplyTo(), response);
	}

	private void doFinalizarTarea(MapMessage msg) throws BusinessException, JMSException{
		TextMessage response = session.createTextMessage();
		taskService.markTaskAsFinished(msg.getLong("taskId"));
		response.setText("Se ha marcado la tarea como finalizada correctamente");
		
        replyProducer.send(msg.getJMSReplyTo(), response);
	}
	
	private void doCrearTarea(MapMessage msg) throws BusinessException, JMSException{
		TextMessage response = session.createTextMessage();
		Task t = (Task) msg.getObject("task");
		taskService.createTask(t);
		response.setText("Se creado la tarea :" + t.getTitle() + " correctamente");
		
        replyProducer.send(msg.getJMSReplyTo(), response);
	}

}
