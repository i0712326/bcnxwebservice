package com.bcnx.web.app.controller.member;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.MemberService;
import com.bcnx.web.app.service.entity.Member;

@Path("/member")
public class MemberController {
	@POST
	@Path("/save")
	@Produces("application/json")
	public Response save(@FormParam("iin")String iin, @FormParam("memId")String memId, @FormParam("entry")String entry, @FormParam("tel")String tel, @FormParam("fax")String fax, @FormParam("add")String add){
		MemberService service = (MemberService) BcnxApplicationContext.getBean("memberService");
		Member member = new Member();
		member.setIin(iin);
		member.setMemId(memId);
		member.setEntry(entry);
		member.setTel(tel);
		member.setFax(fax);
		member.setAddress(add);
		service.save(member);
		return Response.status(200).entity(member).build();
	}
}
