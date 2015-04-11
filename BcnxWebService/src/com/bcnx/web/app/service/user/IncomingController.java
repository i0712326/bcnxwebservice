package com.bcnx.web.app.service.user;

import java.sql.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.spi.ApplicationException;

import com.bcnx.web.app.service.entity.DisputeTxn;
import com.bcnx.web.app.service.entity.User;
import com.bcnx.web.app.service.entity.Wrapper;
import com.bcnx.web.app.utility.UtilityService;
@Path("/incoming")
public class IncomingController extends DisputeTemplate {
	@GET
	@Path("/get")
	@Produces("application/json")
	public Response getIncoming(@QueryParam("usrId") String usrId,
			@QueryParam("page") int page, @QueryParam("rows") int rows) throws ApplicationException {
		User usr = new User();
		usr.setUserId(usrId);
		usr = userService.getUser(usr);
		String id = usr.getMember().getIin();
		int first = (page-1)*rows;
		List<DisputeTxn> list = disputeTxnService.getIncoming(id, first, rows);
		int records = disputeTxnService.getInRecords(id);
		int total = (records+rows-1)/rows;
		Wrapper wrapper = new Wrapper();
		wrapper.setPage(page);
		wrapper.setTotal(total);
		wrapper.setRecords(records);
		wrapper.setRows(list);
		return Response.ok(wrapper).build();
	}
	@GET
	@Path("/get")
	@Produces("application/json")
	public Response getIncoming(@QueryParam("usrId") String usrId,
			@QueryParam("card") String card, @QueryParam("rrn") String rrn,
			@QueryParam("stan") String stan, @QueryParam("proc") String proc,
			@QueryParam("start") String start, @QueryParam("end") String end,
			@QueryParam("page") int page, @QueryParam("rows") int rows) throws ApplicationException {
		User usr = new User();
		usr.setUserId(usrId);
		usr = userService.getUser(usr);
		String id = usr.getMember().getIin();
		int first = (page-1)*rows;
		Date from = UtilityService.str2Date(start);
		Date to = UtilityService.str2Date(end);
		List<DisputeTxn> list = disputeTxnService.getIncoming(id, card, rrn, stan, proc, from, to,first, rows);
		int records = disputeTxnService.getInRecords(id,  card, rrn, stan, proc, from, to);
		int total = (records+rows-1)/rows;
		Wrapper wrapper = new Wrapper();
		wrapper.setPage(page);
		wrapper.setTotal(total);
		wrapper.setRecords(records);
		wrapper.setRows(list);
		return Response.ok(wrapper).build();
	}
}
