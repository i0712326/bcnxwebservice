package com.bcnx.web.app.service.user;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.BcnxSettleService;
import com.bcnx.web.app.service.entity.BcnxSettle;
@Path("/bcnxsettle")
public class bcnxSettleController {
	private static BcnxSettleService bcnxSettleService = (BcnxSettleService) BcnxApplicationContext.getBean("bcnxSettleService");
	@GET
	@Path("/get/settle")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getBcnxSettle(BcnxSettle bcnxSettle){
		BcnxSettle bcnxSetl = bcnxSettleService.getBcnxSettle(bcnxSettle);
		return Response.ok(bcnxSetl).build();
	}
	@GET
	@Path("get/settles")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getBcnxSettles(BcnxSettle bcnxSettle){
		List<BcnxSettle> list = bcnxSettleService.getBcnxSettles(bcnxSettle, bcnxSettle.getFirst(), bcnxSettle.getMax());
		return Response.ok(list).build();
	}
}
