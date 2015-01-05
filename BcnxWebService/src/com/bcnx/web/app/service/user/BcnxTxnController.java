package com.bcnx.web.app.service.user;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.BcnxTxnService;
import com.bcnx.web.app.service.entity.BcnxTxn;
import com.bcnx.web.app.service.entity.DisputeTxn;
import com.bcnx.web.app.service.entity.User;
@Path("/bcnx")
public class BcnxTxnController {
	@GET
	@Path("/get/{first}/{max}")
	@Consumes("application/json")
	public Response getBcnxTxns(@PathParam("first") String first,
			@PathParam("max") String max, BcnxTxn bcnxTxn, User user) {
		BcnxTxnService service = (BcnxTxnService) BcnxApplicationContext
				.getBean("bcnxTxnService");
		List<BcnxTxn> list = service.getBcnxTxns(bcnxTxn,
				Integer.parseInt(first), Integer.parseInt(max), user);
		return Response.status(200).entity(list).build();
	}
	@POST
	@Path("/save/chargeback")
	@Consumes("application/json")
	public Response saveCopyRequest(DisputeTxn disputeTxn){
		BcnxTxnService service = (BcnxTxnService) BcnxApplicationContext
				.getBean("bcnxTxnService");
		BcnxTxn bcnxTxn = disputeTxn;
		service.save(bcnxTxn);
		return Response.status(200).entity(disputeTxn).build();
	}
}
