package com.bcnx.web.app.service.user;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.SettleBcnxService;
import com.bcnx.web.app.service.UserService;
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
		SettleBcnx settleBcnx = settleBean.getSettleBcnx(UtilityService.str2Date2(date), id);
		return Response.ok(settleBcnx).build();
	}
}
