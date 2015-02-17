package com.bcnx.web.app.service.user;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.service.entity.DisputeTxn;
import com.bcnx.web.app.service.entity.Wrapper;

@Path("/dispute")
public class DisputeController extends DisputeTemplate {
	@GET
	@Path("/get/related")
	@Produces("application/json")
	public Response getRelated(@QueryParam("rrn") String rrn,
			@QueryParam("slot") String slot, @QueryParam("stan") String stan,
			@QueryParam("mti") String mti, @QueryParam("page") int page,
			@QueryParam("rows") int rows) {
		DisputeTxn disp = new DisputeTxn();
		disp.setRrn(rrn);
		disp.setSlot(slot);
		disp.setStan(stan);
		disp.setMti(mti);
		int first = (page-1)*rows;
		List<DisputeTxn> list = disputeTxnService.getRelated(disp, first, rows);
		int records = disputeTxnService.getRelatedRecords(disp);
		int total = (records+rows-1)/rows;
		Wrapper wrapper = new Wrapper();
		wrapper.setRecords(records);
		wrapper.setTotal(total);
		wrapper.setPage(page);
		wrapper.setRows(list);
		return Response.ok(wrapper).build();
	}
	@GET
	@Path("/get/transaction")
	@Produces("application/json")
	public Response getDisputeTxn(@QueryParam("rrn") String rrn, @QueryParam("rrn") String proc,
			@QueryParam("slot") String slot, @QueryParam("stan") String stan,
			@QueryParam("mti") String mti){
		DisputeTxn disp = new DisputeTxn();
		disp.setProc(proc);
		disp.setRrn(rrn);
		disp.setSlot(slot);
		disp.setStan(stan);
		disp.setMti(mti);
		disp = disputeTxnService.getDisputeTxn(disp);
		return Response.ok(disp).build();
	}
}
