package com.bcnx.web.app.service.user;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.service.entity.DisputeTxn;
import com.bcnx.web.app.service.entity.User;
import com.bcnx.web.app.service.entity.Wrapper;
@Path("/incoming")
public class IncomingController extends DisputeTemplate {
	@GET
	@Path("/get")
	@Produces("application/json")
	public Response getIncoming(@QueryParam("usrId") String usrId,
			@QueryParam("page") int page, @QueryParam("rows") int rows) {
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
	@Path("/get/{proc}")
	@Produces("application/json")
	public Response getIncoming(@QueryParam("usrId") String usrId,
			@PathParam("proc") String proc, @QueryParam("page") int page,
			@QueryParam("rows") int rows) {
		User usr = new User();
		usr.setUserId(usrId);
		usr = userService.getUser(usr);
		String id = usr.getMember().getIin();
		int first = (page-1)*rows;
		List<DisputeTxn> list = disputeTxnService.getIncoming(id, proc, first, rows);
		int records = disputeTxnService.getInRecords(id, proc);
		int total = (records+rows-1)/rows;
		Wrapper wrapper = new Wrapper();
		wrapper.setPage(page);
		wrapper.setTotal(total);
		wrapper.setRecords(records);
		wrapper.setRows(list);
		return Response.ok(wrapper).build();
	}
}
