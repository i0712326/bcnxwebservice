package com.bcnx.web.app.service.user;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.spi.ApplicationException;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.ReasonCodeService;
import com.bcnx.web.app.service.entity.ReasonCode;
@Path("/reason")
public class ReasonCodeController {
	private static ReasonCodeService reasonCodeService = (ReasonCodeService) BcnxApplicationContext.getBean("reasonCodeService");
	@GET
	@Path("/get/{proc}")
	@Produces("application/json")
	public Response getReasonCode(@PathParam("proc") String proc) throws ApplicationException {
		List<ReasonCode> list = reasonCodeService.getReasonCodes(proc);
		return Response.ok(list).build();
	}
	
}
