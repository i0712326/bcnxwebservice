package com.bcnx.web.app.service.user;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.DisputeTxnService;
import com.bcnx.web.app.service.entity.DisputeTxn;
@Path("/related")
public class RelatedDisputeController {
	private static DisputeTxnService disputeTxnService = (DisputeTxnService) BcnxApplicationContext.getBean("disputeTxnService");
	@GET
	@Path("/get")
	@Produces("application/json")
	public Response getRelated(@QueryParam("mti") String mti,
			@QueryParam("rrn") String rrn, @QueryParam("slot") String slot,
			@QueryParam("stan") String stan, @QueryParam("page") int page,
			@QueryParam("rows") int rows) {
		DisputeTxn disp = new DisputeTxn();
		disp.setMti(mti);
		disp.setRrn(rrn);
		disp.setSlot(slot);
		disp.setStan(stan);
		int first = (page - 1) * rows;
		List<DisputeTxn> list = disputeTxnService.getDisputeTxns(disp, first,
				rows);
		return Response.ok(list).build();
	}
}
