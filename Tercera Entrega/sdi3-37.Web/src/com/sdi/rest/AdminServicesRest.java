package com.sdi.rest;

import java.util.List;

import javax.ws.rs.Consumes;
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

@Path("/AdminServicesRs")
public interface AdminServicesRest {
	
	@GET
	@Path("categories/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<Category> getCategories(@PathParam("id") Long id) throws BusinessException;
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<Task> getTaksByCategoryId(@PathParam("id") Long id) throws BusinessException;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	void markTaskAsComplete(Task task) throws BusinessException;
	
	@PUT
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_XML})
	void newTask(Task task) throws BusinessException;
}
