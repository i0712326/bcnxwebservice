package com.bcnx.web.app.service.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.BcnxSettleService;
import com.bcnx.web.app.service.DisputeTxnService;
import com.bcnx.web.app.service.ReasonCodeService;
import com.bcnx.web.app.service.UserService;
import com.bcnx.web.app.service.entity.DisputeTxn;
import com.bcnx.web.app.service.entity.Member;
import com.bcnx.web.app.service.entity.User;

public class DisputeController {
	protected static UserService userService = (UserService) BcnxApplicationContext.getBean("userService");
	protected static BcnxSettleService bcnxSettleService = (BcnxSettleService) BcnxApplicationContext.getBean("bcnxSettleService");
	protected static ReasonCodeService reasonCodeService = (ReasonCodeService) BcnxApplicationContext.getBean("reasonCodeService");
	protected static DisputeTxnService disputeTxnService = (DisputeTxnService) BcnxApplicationContext.getBean("disputeTxnService");
	
	protected boolean checkIssuer(DisputeTxn txn, User user){
		String iss = txn.getBcnxSettle().getIss();
		Member mem = user.getMember();
		if(mem.getIin().equals(iss))
			return true;
		return false;
	}
	
	protected boolean checkAcquirer(DisputeTxn txn, User user){
		String acq = txn.getBcnxSettle().getAcq();
		Member mem = user.getMember();
		if(mem.getIin().equals(acq))
			return true;
		return false;
	}
	protected java.sql.Date getDate(){
		java.util.Date d = new java.util.Date();
		return new java.sql.Date(d.getTime());
	}
	protected String getTime(){
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		java.util.Date d = new java.util.Date();
		return format.format(d);
	}
	protected static final String UPLOADED_FILE_PATH = "D:\\Share\\tik\\Work"; 
	protected String getFileData(List<InputPart> inputParts){
		String fileName = "unkwonk";
		for (InputPart inputPart : inputParts) {
			 try {
				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);
				//convert the uploaded file to input stream
				InputStream inputStream = inputPart.getBody(InputStream.class,null);
				byte [] bytes = IOUtils.toByteArray(inputStream);
				//constructs upload file path
				String filePath = UPLOADED_FILE_PATH +"\\"+ fileName;
				writeFile(bytes,filePath);
				return fileName;
			  } catch (IOException e) {
				e.printStackTrace();
			  }
		}
		return null;
	}
	protected String getDataForm(List<InputPart> inputParts){
		for (InputPart inputPart : inputParts) {
			 try {
				//convert the uploaded file to input stream
				String data = inputPart.getBody(String.class,null);
				return data;
			  } catch (IOException e) {
				e.printStackTrace();
			  }
		}
		return null;
	}
	protected String getFileName(MultivaluedMap<String, String> header) {
		 
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
 
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
 
				String[] name = filename.split("=");
 
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}
	//save to somewhere
	protected void writeFile(byte[] content, String filename) throws IOException {
 
		File file = new File(filename);
 
		if (!file.exists()) {
			file.createNewFile();
		}
 
		FileOutputStream fop = new FileOutputStream(file);
 
		fop.write(content);
		fop.flush();
		fop.close();
 
	}
}
