package com.sdi.business.integration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.*;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import com.sdi.business.TaskService;
import com.sdi.business.UserService;
import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.task.LocalTaskService;
import com.sdi.business.impl.user.LocalUserService;
import com.sdi.business.integration.Jndi;
import com.sdi.dto.Task;
import com.sdi.dto.User;

@MessageDriven(
		activationConfig = {
					@ActivationConfigProperty(
							propertyName = "destination",
							propertyValue = "queue/ClientQueue")
})
public class Listener implements MessageListener {

	private final static String JMS_CONNECTION_FACTORY = 
			"java:jboss/exported/jms/RemoteConnectionFactory";
	
	private final static String LOG_QUEUE = "queue/LogQueue";
	
	private ConnectionFactory factory;
	private Connection conection;
	private Session session;
	
	@EJB(beanInterface = LocalUserService.class)
	private UserService uService;
	
	@EJB(beanInterface = LocalTaskService.class)
	private TaskService tService;
	
	public Listener() {
		try {
			factory = (ConnectionFactory) Jndi.find(JMS_CONNECTION_FACTORY);
			conection = factory.createConnection("sdi", "password");
			session = conection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void onMessage(Message msg) {
		try {
			factory = (ConnectionFactory) Jndi.find(JMS_CONNECTION_FACTORY);
			conection = factory.createConnection("sdi", "password");
			session = conection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
			process(msg);
			
			session.close();
			conection.close();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		} catch (BusinessException e){
			
		} finally {
			try {
				session.close();
				conection.close();
           } catch (JMSException e) {
           	throw new RuntimeException(e);
           }
		}
	}

	private void process(Message msg) throws JMSException, BusinessException {
		MapMessage map = null;
		if(msg instanceof MapMessage) {
			map = (MapMessage) msg;
		}
		
		//Comprobamos tipo de mensaje, tiene que ser MapMessage
		if(! (msg instanceof MapMessage)) {
			MapMessage mapVuelta = session.createMapMessage();			
			mapVuelta.setString("msg", "Tipo de mensaje incorrecto, use MapMessage");
			session.createProducer(msg.getJMSReplyTo()).send(mapVuelta);
			
			Destination destination = (Destination) Jndi.find(LOG_QUEUE);
			session.createProducer(destination).send(msg);
			return;
		}		
		
		//Comprobamos credenciales
		if(!validateUser(map)) {
			MapMessage mapVuelta = session.createMapMessage();
			mapVuelta.setString("accion", "login");
			mapVuelta.setLong("userId", -1);
			mapVuelta.setString("msg", "Usuario o contraseña incorrecta");			
			
			session.createProducer(map.getJMSReplyTo()).send(mapVuelta);
			
			Destination destination = (Destination) Jndi.find(LOG_QUEUE);
			session.createProducer(destination).send(mapVuelta);			
			return;
		}
		else{
			String accion = map.getString("accion");
			
			switch(accion){
				case "login":
					login(map);
					break;
				case "listarTareasHoy":
					listarTareasHoy(map);
					break;
				case "finalizarTarea":
					finalizarTarea(map);
					break;	
				case "crearTarea":
					crearTarea(map);
					break;
			}
		}		
	}
	
	/**
	 * Validar usuario
	 * @param map
	 * @return
	 * @throws JMSException
	 * @throws BusinessException
	 */
	private boolean validateUser(MapMessage map) throws JMSException, BusinessException {
		String username = map.getString("username");
		String password = map.getString("password");
		
		User user = uService.findLoggableUser(username, password);
		
		return user != null;
	}
	
	/**
	 * Metodo para hacer el login, si existe el usuario nos devuelve un map
	 * con todos los datos que necesitaremos
	 * @param map
	 * @throws JMSException
	 * @throws BusinessException
	 */
	private void login(MapMessage map) throws JMSException, BusinessException {
		String username = map.getString("username");
		String password = map.getString("password");
		
		User user = uService.findLoggableUser(username, password);
		
		MapMessage mapVuelta = session.createMapMessage();
		mapVuelta.setString("accion", "login");		
			
		mapVuelta.setLong("userId", user.getId());
		mapVuelta.setString("username", user.getLogin());
		mapVuelta.setString("password", user.getPassword());
		mapVuelta.setString("msg", "Usuario recuperado bien");					
		
		session.createProducer(map.getJMSReplyTo()).send(mapVuelta);		
	}
	
	/**
	 * Método para listar las tareas de hoy
	 * @param map
	 * @throws JMSException
	 * @throws BusinessException
	 */
	private void listarTareasHoy(MapMessage map) throws JMSException, BusinessException {
		Long userId = map.getLong("userId");
		
		List<Task> tareas = tService.findTodayTasksByUserId(userId);
		
		ObjectMessage objetoVuelta = session.createObjectMessage();
		objetoVuelta.setObject((LinkedList<Task>) tareas);
		
		session.createProducer(map.getJMSReplyTo()).send(objetoVuelta);
	}
	
	/**
	 * Método para finalizar tarea
	 * @param map
	 * @throws JMSException
	 * @throws BusinessException
	 */
	private void finalizarTarea(MapMessage map) throws JMSException, BusinessException{
		Long tareaId = map.getLong("tareaId");
		Long userId = map.getLong("userId");
		
		Task tarea = tService.findTaskById(tareaId);
		
		MapMessage mapVuelta = session.createMapMessage();
		mapVuelta.setString("accion", "finalizarTarea");		
		
		if(tarea != null && (long) tarea.getUserId() == userId) {
			if(tarea.getFinished() == null) {
				tService.markTaskAsFinished(tareaId);
				mapVuelta.setString("msg", "\tTarea con id "+tareaId+" marcada como finalizada correctamente");
			}
			else {
				mapVuelta.setString("msg", "\tLa tarea con id "+tareaId+ " ya estaba finalizada");
			}
		}
		else {
			mapVuelta.setString("msg", "\tNo existe la tarea con id "+tareaId);
		}
		
		session.createProducer(map.getJMSReplyTo()).send(mapVuelta);
	}
	
	/**
	 * Método para crear una tarea
	 * @param map
	 * @throws JMSException
	 * @throws BusinessException
	 */
	private void crearTarea(MapMessage map) throws JMSException, BusinessException {
		Long userId = map.getLong("userId");
		String nombre = map.getString("nombre");
		String comentario = map.getString("comentario");
		String fecha = map.getString("fecha");
		
		MapMessage mapVuelta = session.createMapMessage();
		mapVuelta.setString("accion", "crearTarea");
		
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		//Creamos tarea con los datos
		Task t = new Task();
		t.setUserId(userId);
		t.setTitle(nombre);
		t.setComments(comentario);		
		t.setFinished(null);
		try {
			t.setCreated(formateador.parse(formateador.format(new Date())));
			t.setPlanned(formateador.parse(fecha));
			if(t.getPlanned().compareTo(t.getCreated()) >= 0) {
				tService.createTask(t);
				mapVuelta.setString("msg", "\tSe ha creada la tarea correctamente");
			}
			else {
				mapVuelta.setString("msg", "\tFecha planeada menor que fecha actual, no se crea la tarea");
			}
		} catch(ParseException e){
			mapVuelta.setString("msg", "\tFormato de fecha introducido incorrecto");
		}
		
		session.createProducer(map.getJMSReplyTo()).send(mapVuelta);
	}
}