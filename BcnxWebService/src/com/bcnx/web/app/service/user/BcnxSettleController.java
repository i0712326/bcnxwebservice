package com.bcnx.web.app.service.user;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.BcnxSettleService;
import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.Wrapper;
import com.bcnx.web.app.utility.UtilityService;
@Path("/settle")
public class BcnxSettleController {
	private static BcnxSettleService bcnxSettleService = (BcnxSettleService) BcnxApplicationContext.getBean("bcnxSettleService");
	@GET
	@Path("/get")
	@Produces("application/json")
	public Response getBcnxSettles(@QueryParam("card") String card,
			@QueryParam("rrn") String rrn, @QueryParam("stan") String stan,
			@QueryParam("from") String from, @QueryParam("to") String to,
			@QueryParam("page") int page, @QueryParam("rows") int rows)
			throws Exception {
		BcnxSettle bs = new BcnxSettle();
		bs.setCard(card);
		bs.setRrn(rrn);
		bs.setStan(stan);
		java.sql.Date start = UtilityService.str2Date(from);
		java.sql.Date end = UtilityService.str2Date(to);
		int first = (page-1)*rows;
		List<BcnxSettle> list = bcnxSettleService.getBcnxSettles(bs, start, end, first, rows);
		int records = bcnxSettleService.getRecords(bs,start,end);
		int total = (records+rows-1)/rows;
		Wrapper wrapper = new Wrapper();
		wrapper.setPage(page);
		wrapper.setTotal(total);
		wrapper.setRecords(records);
		wrapper.setRows(list);
		return Response.ok(wrapper).build();
	}
}
