package com.bcnx.web.app.controller.bulletin;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.BulletinService;
import com.bcnx.web.app.service.entity.Bulletin;
@Path("/bulletin")
public class BulletinController {
	@POST
	@Path("/save")
	@Consumes("application/json")
	@Produces("application/json")
	public Response save(Bulletin bulletin){
		BulletinService service = (BulletinService) BcnxApplicationContext.getBean("bulletinService");
		service.save(bulletin);
		return Response.status(200).entity(bulletin).build();
	}
	@GET
	@Path("/get/{first}/{max}")
	public Response getBulletns(String first, String max){
		BulletinService service = (BulletinService) BcnxApplicationContext.getBean("bulletinService");
		List<Bulletin> bulletins = service.getBulletin(Integer.parseInt(first), Integer.parseInt(max));
		return Response.status(200).entity(bulletins).build();
	}
	@GET
	@Path("/get/{date}/{first}/{max}")
	public Response getBulletins(String date, String first, String max){
		BulletinService service = (BulletinService) BcnxApplicationContext.getBean("bulletinService");
		Timestamp start  = toTimestamp(date.replaceAll("/", "")+"00:00:00");
		Timestamp end = toTimestamp(date.replaceAll("/", "")+"59:59:59");
		List<Bulletin> bulletins = service.getBulletin(start, end, Integer.parseInt(first), Integer.parseInt(max));
		return Response.status(200).entity(bulletins).build();
	}
	
	private Timestamp toTimestamp(String date){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			java.util.Date d = format.parse(date);
			Timestamp dt = new Timestamp(d.getTime());
			return dt;
		} catch (ParseException e) {
			return null;
		}
	}
}
