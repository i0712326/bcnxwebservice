package com.bcnx.web.app.service.user;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.MemberService;
import com.bcnx.web.app.service.ReportService;
import com.bcnx.web.app.service.entity.Member;
import com.bcnx.web.app.service.entity.Report;
import com.bcnx.web.app.utility.UtilityService;
@Path("/report")
public class ReportController {
	private static ReportService reportService = (ReportService) BcnxApplicationContext.getBean("reportService");
	private static MemberService memberService = (MemberService) BcnxApplicationContext.getBean("memberSerivce");
	@GET
	@Path("/get")
	@Produces("application/json")
	public Response getReports(@QueryParam("memId") String memId,
			@QueryParam("date") String date) {
		Member member = new Member();
		member.setMemId(memId);
		member = memberService.getMember(member);
		List<Report> list = reportService.getReports(member, UtilityService.str2Date2(date), 0, 5);
		return Response.ok(list).build();
	}
	private static final String dir = "report";
	@GET
	@Path("/download")
	@Produces("text/plain")
	public Response downloadReport(@QueryParam("memId") String memId,
			@QueryParam("date") String date, @Context HttpServletRequest request) {
		String path = request.getServletContext().getRealPath(dir);
		String name = date+"-"+memId+".txt";
		String director = date.substring(0, 4)+"/"+name;
		String fileName = path+"/"+director;
		File file = new File(fileName);
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
			"attachment; filename=\""+name+"\"");
		return response.build();
	}
}
