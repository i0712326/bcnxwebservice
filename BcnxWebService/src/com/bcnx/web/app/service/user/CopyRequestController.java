package com.bcnx.web.app.service.user;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.spi.ApplicationException;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.CopyRequestService;
import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.DisputeTxn;
import com.bcnx.web.app.service.entity.ErrMsg;
import com.bcnx.web.app.service.entity.ReasonCode;
import com.bcnx.web.app.service.entity.User;
@Path("/copyrequest")
public class CopyRequestController extends DisputeTemplate{
	private static CopyRequestService copyRequestService = (CopyRequestService) BcnxApplicationContext.getBean("copyRequestService");
	@POST
	@Path("/save")
	@Produces("application/json")
	public Response save(@FormParam("mti") String mti,
			@FormParam("rrn") String rrn, @FormParam("slot") String slot,
			@FormParam("stan") String stan, @FormParam("proc") String proc,
			@FormParam("rea") String rea, @FormParam("remark") String remark,
			@FormParam("part") String part, @FormParam("amount") double amount,
			@FormParam("fee") double fee, @FormParam("usrId") String userId) throws ApplicationException {
		DisputeTxn disputeTxn = new DisputeTxn();
		disputeTxn.setProcc(proc);
		disputeTxn.setRemark(remark);
		disputeTxn.setAmount(amount);
		disputeTxn.setFee(fee);
		disputeTxn.setDate(getDate());
		disputeTxn.setTime(getTime());
		ReasonCode rc = reasonCodeService.getReasonCode(rea);
		User user = new User();
		user.setUserId(userId);
		user = userService.getUser(user);
		disputeTxn.setRc(rc);
		disputeTxn.setUser(user);
		BcnxSettle settle = new BcnxSettle();
		settle.setSlot(slot);
		settle.setRrn(rrn);
		settle.setStan(stan);
		settle.setMti(mti);
		settle = bcnxSettleService.getBcnxSettle(settle);
		if(settle==null)
			return Response.status(500)
					.entity(new ErrMsg("412", "invalid transactions")).build();
		disputeTxn.setBcnxSettle(settle);
		boolean chk = checkIssuer(disputeTxn, user);
		if (!chk)
			return Response
					.status(500)
					.entity(new ErrMsg("410",
							"User is not allowed to perform function")).build();
		boolean valid = checkValidDate(settle.getDate(),proc);
		if(!valid)
			return Response.status(500)
					.entity(new ErrMsg("414", "Exceed valid date request")).build();
		DisputeTxn txn = disputeTxnService.getDisputeTxn(disputeTxn);
		if (txn != null)
			return Response.status(500)
					.entity(new ErrMsg("411", "Duplicated request")).build();
		copyRequestService.save(disputeTxn);
		return Response
				.status(200)
				.entity(new ErrMsg("200", "Copy request has sent successfully"))
				.build();
	}
	@PUT
	@Path("/response")
	@Produces("application/json")
	@Consumes("multipart/form-data")
	public Response resCpRq(MultipartFormDataInput input, @Context ServletContext context) throws ApplicationException {
		String path = context.getInitParameter("uploadFolder");
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		String fileName = getFileData(uploadForm.get("file"),path);
		String rrn = getDataForm(uploadForm.get("rrn"));
		String slot = getDataForm(uploadForm.get("slot"));
		String stan = getDataForm(uploadForm.get("stan"));
		String mti = getDataForm(uploadForm.get("mti"));
		String proc = getDataForm(uploadForm.get("procc"));
		String rea = getDataForm(uploadForm.get("rea"));
		String userId = getDataForm(uploadForm.get("usrId"));
		
		DisputeTxn disputeTxn = new DisputeTxn();
		disputeTxn.setDate(getDate());
		disputeTxn.setTime(getTime());
		disputeTxn.setProcc(proc);
				
		User user = new User();
		user.setUserId(userId);
		user = userService.getUser(user);
		disputeTxn.setUser(user);
		// check file name
		boolean chkName = checkName(fileName, user,rrn, stan);
		if (!chkName)
			return Response.status(500)
					.entity(new ErrMsg("409", "Invalid Attachment Name"))
					.build();
		ReasonCode rc = reasonCodeService.getReasonCode(rea);
		disputeTxn.setRc(rc);
		BcnxSettle settle = new BcnxSettle();
		settle.setRrn(rrn);
		settle.setSlot(slot);
		settle.setStan(stan);
		settle.setMti(mti);
		
		settle = bcnxSettleService.getBcnxSettle(settle);
		if(settle==null)
			return Response.status(500)
					.entity(new ErrMsg("412", "Invalid transactions")).build();
		disputeTxn.setBcnxSettle(settle);
		
		DisputeTxn txn = disputeTxnService.getDisputeTxn(disputeTxn);
		if (txn != null)
			return Response.status(500)
					.entity(new ErrMsg("411", "Duplicated request")).build();
		boolean chk = checkAcquirer(disputeTxn, user);
		if (!chk)
			return Response
					.status(500)
					.entity(new ErrMsg("410",
							"User is not allowed to perform function")).build();
		DisputeTxn disp = new DisputeTxn();
		disp.setProcc("500001");
		disp.setBcnxSettle(settle);
		disp = disputeTxnService.getDisputeTxn(disp);
		if(disp==null)
			return Response
					.status(500)
					.entity(new ErrMsg("413",
							"Invalid copy request")).build();
		boolean valid = checkValidDate(disp.getDate(),proc);
		if(valid)
			return Response
					.status(500)
					.entity(new ErrMsg("414",
							"Exceed valid date request")).build();
		disp.setStatus("Y");
		copyRequestService.update(disp);
		disputeTxn.setAmount(disp.getAmount());
		disputeTxn.setFee(disp.getFee());
		disputeTxn.setFileName(fileName);
		copyRequestService.save(disputeTxn);
		return Response.ok(new ErrMsg("200","update successful")).build();
	}
	@Path("download/{fileName}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response download(@PathParam("fileName")String fileName, @Context ServletContext context) throws ApplicationException {
		String path = context.getInitParameter("uploadFolder");
		String target = path+"/"+fileName;
		File file = new File(target);
	    ResponseBuilder response = Response.ok((Object) file);
	    response.header("Content-Disposition","attachment; filename="+fileName);
	    return response.build();
	}
}
