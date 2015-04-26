package com.bcnx.web.app.service.admin;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.RoleService;
import com.bcnx.web.app.service.entity.Role;

@Path("/role")
public class RoleController {
	@POST
	@Path("/save")
	@Produces("application/json")
	public Response save(@QueryParam("roleId") String roleId,
			@QueryParam("roleName") String roleName) throws Exception {
		RoleService service = (RoleService) BcnxApplicationContext
				.getBean("roleService");
		Role role = new Role();
		role.setRoleId(roleId);
		role.setRoleName(roleName);
		service.save(role);
		return Response.status(200).entity(role).build();
	}
	@GET
	@Path("/get")
	public Response getRoles(@QueryParam("first") int first,
			@QueryParam("max") int max) throws Exception {
		RoleService service = (RoleService) BcnxApplicationContext
				.getBean("roleService");
		List<Role> roles = service.getRoles(first, max);
		return Response.status(200).entity(roles).build();
	}
}
