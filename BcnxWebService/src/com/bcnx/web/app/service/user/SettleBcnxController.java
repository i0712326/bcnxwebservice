package com.bcnx.web.app.service.user;

import java.io.File;
import java.sql.Date;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.SettleBcnxService;
import com.bcnx.web.app.service.UserService;
import com.bcnx.web.app.service.entity.Member;
import com.bcnx.web.app.service.entity.SettleBcnx;
import com.bcnx.web.app.service.entity.User;
import com.bcnx.web.app.utility.UtilityService;
@Path("/summary")
public class SettleBcnxController {
	@GET
	@Path("/get")
	@Produces("application/json")
	public Response getSettleBcnx(@QueryParam("date") String date,
			@QueryParam("id") String id) {
		SettleBcnxService settleBean = (SettleBcnxService) BcnxApplicationContext.getBean("settleBcnxService");
		UserService userBean = (UserService) BcnxApplicationContext.getBean("userService");
		User user = new User();
		user.setUserId(id);
		user = userBean.getUser(user);
		Member member = user.getMember();
		String iin = member.getIin();
		SettleBcnx settleBcnx = settleBean.getSettleBcnx(UtilityService.str2Date(date), iin);
		return Response.ok(settleBcnx).build();
	}
	@GET
	@Path("/download")
	@Produces("text/plain")
	public Response download(@QueryParam("usrId") String usrId,
			@QueryParam("date") String date, @QueryParam("file") String file,
			@Context ServletContext servletContext) {
		String path = servletContext.getInitParameter("reportPath");
		UserService service = (UserService) BcnxApplicationContext.getBean("userService");
		User user = new User();
		user.setUserId(usrId);
		user = service.getUser(user);
		Member member = user.getMember();
		String iin = member.getIin();
		Date dat = UtilityService.str2Date(date);
		String dirName = UtilityService.date2Str(dat);
		String filePath = path+"/"+iin+"/"+dirName+"/"+file;
		File target = new File(filePath);
		ResponseBuilder response = Response.ok((Object) target);
		response.header("Content-Disposition",
			"attachment; filename="+file);
		return response.build();
	}
}
