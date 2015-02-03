package com.bcnx.web.app.service.user;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.RepresentService;
import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.DisputeTxn;
import com.bcnx.web.app.service.entity.ErrMsg;
import com.bcnx.web.app.service.entity.ReasonCode;
import com.bcnx.web.app.service.entity.User;
@Path("/represent")
public class RepresentController extends DisputeController{
	private static RepresentService representService = (RepresentService) BcnxApplicationContext.getBean("representService");
	@POST
	@Path("/save")
	@Produces("application/json")
	@Consumes("multipart/form-data")
	public Response save(MultipartFormDataInput input) {
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		String fileName = getFileData(uploadForm.get("file"));
		String rrn = getDataForm(uploadForm.get("rrn"));
		String slot = getDataForm(uploadForm.get("slot"));
		String stan = getDataForm(uploadForm.get("stan"));
		String mti = getDataForm(uploadForm.get("mti"));
		String proc = getDataForm(uploadForm.get("procc"));
		String rea = getDataForm(uploadForm.get("rea"));
		String userId = getDataForm(uploadForm.get("usrId"));
		
		DisputeTxn disputeTxn = new DisputeTxn();
		disputeTxn.setProcc(proc);
		disputeTxn.setDate(getDate());
		disputeTxn.setTime(getTime());
		disputeTxn.setFileName(fileName);
		ReasonCode rc = reasonCodeService.getReasonCode(rea);
		User user = new User();
		user.setUserId(userId);
		user = userService.getUser(user);
		BcnxSettle settle = new BcnxSettle();
		settle.setSlot(slot);
		settle.setMti(mti);
		settle.setRrn(rrn);
		settle.setStan(stan);
		settle = bcnxSettleService.getBcnxSettle(settle);
		disputeTxn.setRc(rc);
		disputeTxn.setUser(user);
		disputeTxn.setBcnxSettle(settle);
		boolean chk = checkAcquirer(disputeTxn, user);
		if (!chk)
			return Response
					.status(500)
					.entity(new ErrMsg("410",
							"User is not allowed to perform function")).build();
		DisputeTxn txn = disputeTxnService.getDisputeTxn(disputeTxn);
		if (txn != null)
			return Response.status(500)
					.entity(new ErrMsg("411", "Duplicated request")).build();
		disputeTxn.setIss(settle.getIss());
		disputeTxn.setAcq(settle.getAcq());
		representService.save(disputeTxn);

		return Response
				.ok(new ErrMsg("200", "Copy request has sent successfully"))
				.build();
	}
	// check incoming
	@GET
	@Path("/incoming/{first}/{max}")
	@Produces("application/json")
	public Response chckInComing(@QueryParam("mti") String mti,
			@QueryParam("rrn") String rrn, @QueryParam("slot") String slot,
			@QueryParam("stan") String stan, @QueryParam("procc") String proc,
			@QueryParam("usrId") String userId, @PathParam("first") int first,
			@PathParam("max") int max) {
		DisputeTxn disputeTxn = new DisputeTxn();
		disputeTxn.setProcc(proc);
		disputeTxn.setDate(getDate());
		disputeTxn.setTime(getTime());
		User user = new User();
		user.setUserId(userId);
		user = userService.getUser(user);
		BcnxSettle settle = new BcnxSettle();
		settle.setSlot(slot);
		settle.setMti(mti);
		settle.setRrn(rrn);
		settle.setStan(stan);
		settle = bcnxSettleService.getBcnxSettle(settle);
		disputeTxn.setUser(user);
		disputeTxn.setBcnxSettle(settle);
		List<DisputeTxn> list = representService.getInRpm(disputeTxn, first, max);
		return Response.ok(list).build();
	}
	@GET
	@Path("/outgoing/{first}/{max}")
	@Produces("application/json")
	public Response chckOutGoing(@QueryParam("mti") String mti,
			@QueryParam("rrn") String rrn, @QueryParam("slot") String slot,
			@QueryParam("stan") String stan, @QueryParam("proc") String proc,
			@QueryParam("usrId") String userId, @PathParam("first") int first,
			@PathParam("max") int max) {
		DisputeTxn disputeTxn = new DisputeTxn();
		disputeTxn.setProcc(proc);
		disputeTxn.setDate(getDate());
		disputeTxn.setTime(getTime());
		User user = new User();
		user.setUserId(userId);
		user = userService.getUser(user);
		BcnxSettle settle = new BcnxSettle();
		settle.setSlot(slot);
		settle.setMti(mti);
		settle.setRrn(rrn);
		settle.setStan(stan);
		settle = bcnxSettleService.getBcnxSettle(settle);
		disputeTxn.setUser(user);
		disputeTxn.setBcnxSettle(settle);
		List<DisputeTxn> list = representService.getOutRpm(disputeTxn, first, max);
		return Response.ok(list).build();
	}
}
