package com.sdi.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.dto.User;

@Path("/UsersServiceRS")
public interface UserServiceRest {
	
	@GET
	@Path("login/username={username}&&password={password}")
	@Produces({MediaType.APPLICATION_JSON})
	User login(@PathParam("username") String username, @PathParam("password") String password) throws BusinessException;
	
	@GET
	@Path("categorias/{userId}")
	@Produces({MediaType.APPLICATION_JSON})
	List<Category> getCategories(@PathParam("userId") Long userId) throws BusinessException;
	
	@GET
	@Path("buscarCategoria/id={userId}&&categoria={categoryName}")
	@Produces({MediaType.APPLICATION_JSON})
	Category findByUserIdAndName(@PathParam("userId") Long userId, @PathParam("categoryName") String categoryName) throws BusinessException;
	
	@GET
	@Path("tareas/id={userId}&&categoria={categoryName}")
	@Produces({MediaType.APPLICATION_JSON})
	List<Task> findPendingAndDelayed(@PathParam("userId") Long userId, @PathParam("categoryName") String categoryName) throws BusinessException;
	
	@GET
	@Path("buscarTarea/id={tareaId}")
	@Produces({MediaType.APPLICATION_JSON})
	Task findTaskById(@PathParam("tareaId") Long tareaId) throws BusinessException;
	
	@PUT
	@Path("finalizarTarea")
	@Produces({MediaType.APPLICATION_JSON})
	void markTaskAsFinished(Task task) throws BusinessException;
	
	@POST
	@Path("crearTarea")
	@Produces({MediaType.APPLICATION_JSON})
	void newTask(Task task) throws BusinessException;
}