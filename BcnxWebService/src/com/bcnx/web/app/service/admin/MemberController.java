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
import com.bcnx.web.app.service.MemberService;
import com.bcnx.web.app.service.entity.Member;

@Path("/member")
public class MemberController {
	@RolesAllowed("ADM")
	@POST
	@Path("/save")
	@Produces("application/json")
	@Consumes("application/json")
	public Response save(Member member) throws Exception{
		MemberService service = (MemberService) BcnxApplicationContext.getBean("memberService");
		service.save(member);
		return Response.status(200).entity(member).build();
	}
	@RolesAllowed("ADM")
	@GET
	@Path("/get/{first}/{max}")
	public Response getMembes(@PathParam("first") int first,
			@PathParam("max") int max) throws Exception {
		MemberService service = (MemberService) BcnxApplicationContext
				.getBean("memberService");
		List<Member> members = service.getMembers(first, max);
		return Response.status(200).entity(members).build();
	}
}
