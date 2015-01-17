package com.bcnx.web.app.service.user;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.BcnxTxnService;
import com.bcnx.web.app.service.entity.BcnxTxn;
import com.bcnx.web.app.service.entity.User;

@Path("/bcnx")
public class BcnxTxnController {
	@GET
	@Path("/get/{first}/{max}")
	@Consumes("application/json")
	public Response getBcnxTxns(BcnxTxn bcnxTxn) {
		BcnxTxnService service = (BcnxTxnService) BcnxApplicationContext
				.getBean("bcnxTxnService");
		List<BcnxTxn> list = service.getBcnxTxns(bcnxTxn,
				bcnxTxn.getFirst(), bcnxTxn.getMax());
		return Response.status(200).entity(list).build();
	}
}
