package com.bcnx.web.app.service.admin;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.RoleService;
import com.bcnx.web.app.service.entity.Role;

@Path("/role")
public class RoleController {
	@RolesAllowed("ADM")
	@POST
	@Path("/save")
	@Produces("application/json")
	@Consumes("application/json")
	public Response save(Role role){
		RoleService service = (RoleService) BcnxApplicationContext.getBean("roleService");
		service.save(role);
		return Response.status(200).entity(role).build();
	}
	@RolesAllowed("ADM")
	@GET
	@Path("/get/{first}/{max}")
	public Response getRoles(@PathParam("first")String first, @PathParam("max")String max){
		RoleService service = (RoleService) BcnxApplicationContext.getBean("roleService");
		List<Role> roles = service.getRoles(Integer.parseInt(first), Integer.parseInt(max));
		return Response.status(200).entity(roles).build();
	}
}
