package com.bcnx.web.app.service.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.BulletinService;
import com.bcnx.web.app.service.entity.Bulletin;
@Path("/bulletin")
public class BulletinController {
	@POST
    @Path("/upload")
    @Consumes("multipart/form-data")
	public Response upload(@MultipartForm Bulletin bulletin) throws Exception {
		String title = bulletin.getTitle() == null ? "Unknown" : bulletin.getTitle();
        String completeFilePath = "D:/Server/Storage/" + title+"."+bulletin.getType(); 
        try
        {
            File file = new File(completeFilePath);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bulletin.getBytes());
            fos.flush();
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return Response.status(200).entity("file name : " + title).build();
	}
	@POST
	@Path("/save")
	@Produces("application/json")
	public Response save(Bulletin bulletin) throws Exception {
		BulletinService service = (BulletinService) BcnxApplicationContext.getBean("bulletinService");
		service.save(bulletin);
		return Response.status(200).entity(bulletin).build();
	}
	@GET
	@Path("/get/{first}/{max}")
	public Response getBulletns(@PathParam("first")int first, @PathParam("max")int max) throws Exception {
		BulletinService service = (BulletinService) BcnxApplicationContext.getBean("bulletinService");
		List<Bulletin> bulletins = service.getBulletin(first, max);
		return Response.status(200).entity(bulletins).build();
	}
	@GET
	@Path("/get/{date}/{first}/{max}")
	public Response getBulletins(@PathParam("date") String date,
			@PathParam("first") int first, @PathParam("max") int max) throws Exception {
		BulletinService service = (BulletinService) BcnxApplicationContext
				.getBean("bulletinService");
		Timestamp start = toTimestamp(date.replaceAll("-", "") + "000000");
		Timestamp end = toTimestamp(date.replaceAll("-", "") + "595959");
		List<Bulletin> bulletins = service.getBulletin(start, end, first, max);
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
