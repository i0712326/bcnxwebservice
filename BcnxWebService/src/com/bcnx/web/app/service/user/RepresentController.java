package com.bcnx.web.app.service.user;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
public class RepresentController extends DisputeTemplate{
	private static RepresentService representService = (RepresentService) BcnxApplicationContext
			.getBean("representService");
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
		String amount = getDataForm(uploadForm.get("amount"));
		String fee = getDataForm(uploadForm.get("fee"));
		String userId = getDataForm(uploadForm.get("usrId"));
		
		DisputeTxn disputeTxn = new DisputeTxn();
		
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
		boolean chk = checkAcquirer(disputeTxn, user);
		if (!chk) return Response
					.status(500)
					.entity(new ErrMsg("410",
							"User is not allowed to perform function")).build();
		DisputeTxn disp = new DisputeTxn();
		disp.setProcc("600001");
		disp.setBcnxSettle(settle);
		disp = disputeTxnService.getDisputeTxn(disp);
		if(disp==null)
			return Response
					.status(500)
					.entity(new ErrMsg("413",
							"Invalid Representment request")).build();
		DisputeTxn txn = disputeTxnService.getDisputeTxn(disputeTxn);
		if (txn != null)
			return Response.status(500)
					.entity(new ErrMsg("411", "Duplicated request")).build();
		disputeTxn.setProcc(proc);
		disputeTxn.setDate(getDate());
		disputeTxn.setTime(getTime());
		disputeTxn.setFileName(fileName);
		disputeTxn.setAmount(Double.parseDouble(amount));
		disputeTxn.setFee(Double.parseDouble(fee));
		disputeTxn.setRc(rc);
		disputeTxn.setUser(user);
		disputeTxn.setBcnxSettle(settle);
		representService.save(disputeTxn);
		return Response
				.ok(new ErrMsg("200", "Copy request has sent successfully"))
				.build();
	}
}
