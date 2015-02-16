package com.bcnx.web.app.service.user;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.AdjustmentService;
import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.DisputeTxn;
import com.bcnx.web.app.service.entity.ErrMsg;
import com.bcnx.web.app.service.entity.ReasonCode;
import com.bcnx.web.app.service.entity.User;
@Path("/adjustment")
public class AdjustmentController extends DisputeTemplate{
	private static AdjustmentService adjustementService = (AdjustmentService) BcnxApplicationContext.getBean("adjustmentService");
	// save adjustment
	@POST
	@Path("/save")
	@Produces("application/json")
	public Response save(@FormParam("mti") String mti,
			@FormParam("rrn") String rrn, @FormParam("slot") String slot,
			@FormParam("stan") String stan, @FormParam("proc") String proc,
			@FormParam("rea") String rea, @FormParam("remark") String remark,
			@FormParam("part") String part, @FormParam("amount") double amount,
			@FormParam("fee") double fee, @FormParam("iss") String iss,
			@FormParam("acq") String acq, @FormParam("usrId") String userId) {
		
		DisputeTxn disputeTxn = new DisputeTxn();
		disputeTxn.setProcc(proc);
		disputeTxn.setRemark(remark);
		disputeTxn.setAmount(amount);
		disputeTxn.setFee(fee);
		disputeTxn.setAcq(acq);
		disputeTxn.setIss(iss);
		disputeTxn.setDate(getDate());
		disputeTxn.setTime(getTime());
		
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
		// copy request
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
		adjustementService.save(disputeTxn);
		return Response
				.status(200)
				.entity(new ErrMsg("200", "Copy request has sent successfully"))
				.build();
	}
}
