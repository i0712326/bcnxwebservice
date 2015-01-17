package com.bcnx.web.app.service.user;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.DisputeTxnService;
import com.bcnx.web.app.service.entity.DisputeTxn;
@Path("/dispute")
public class DisputeController {
	private static DisputeTxnService disputeTxnService = (DisputeTxnService) BcnxApplicationContext.getBean("disputeTxnService");
	@POST
	@Path("/save")
	@Consumes("application/json")
	@Produces("application/json")
	public Response save(DisputeTxn disputeTxn){
		disputeTxnService.save(disputeTxn);
		return Response.ok(disputeTxn).build();
	}
	@GET
	@Path("/get")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getDispute(DisputeTxn disputeTxn,int first, int max){
		List<DisputeTxn> list = disputeTxnService.getDisputeTxns(disputeTxn, first, max);
		return Response.ok(list).build();
	}
}
