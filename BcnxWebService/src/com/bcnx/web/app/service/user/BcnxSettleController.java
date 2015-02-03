package com.bcnx.web.app.service.user;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.BcnxSettleService;
import com.bcnx.web.app.service.entity.BcnxSettle;
@Path("/settle")
public class BcnxSettleController {
	private static BcnxSettleService bcnxSettleService = (BcnxSettleService) BcnxApplicationContext.getBean("bcnxSettleService");
	@GET
	@Path("/get/{first}/{max}")
	@Produces("application/json")
	public Response getBcnxSettles(@QueryParam("card") String card,
			@QueryParam("rrn") String rrn, @QueryParam("stan") String stan,
			@QueryParam("from") String from, @QueryParam("to") String to,
			@PathParam("first") int first, @PathParam("max") int max) {
		BcnxSettle bs = new BcnxSettle();
		bs.setCard(card);
		bs.setRrn(rrn);
		bs.setStan(stan);
		java.sql.Date start = str2Date(from);
		java.sql.Date end = str2Date(to);
		List<BcnxSettle> list = bcnxSettleService.getBcnxSettles(bs, start, end, first, max);
		return Response.ok(list).build();
	}
	private Date str2Date(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyMMdd");
		try {
			java.util.Date date = format.parse(str);
			return new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
