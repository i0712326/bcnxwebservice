package com.bcnx.web.app.controller.role;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.RoleService;
import com.bcnx.web.app.service.entity.Role;

@Path("/role")
public class RoleController {
	@POST
	@Path("/save")
	@Produces("application/json")
	public Response save(@FormParam("roleId")String roleId, @FormParam("name")String name){
		RoleService service = (RoleService) BcnxApplicationContext.getBean("roleService");
		Role role = new Role();
		role.setRoleId(roleId);
		role.setRoleName(name);
		service.save(role);
		return Response.status(200).entity(role).build();
	}
}
