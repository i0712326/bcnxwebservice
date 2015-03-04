package com.bcnx.web.app.service.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.bcnx.web.app.context.BcnxApplicationContext;
import com.bcnx.web.app.service.BcnxSettleService;
import com.bcnx.web.app.service.DisputeTxnService;
import com.bcnx.web.app.service.ReasonCodeService;
import com.bcnx.web.app.service.UserService;
import com.bcnx.web.app.service.entity.DisputeTxn;
import com.bcnx.web.app.service.entity.Member;
import com.bcnx.web.app.service.entity.User;
import com.bcnx.web.app.utility.UtilityService;

public class DisputeTemplate {
	
	private static final String CPRQ = "500001";
	private static final String CHB  = "600001";
	private static final String CPRP = "500002";
	private static final String ADJ  = "700001";
	private static final String RPS  = "800001";
	
	protected static UserService 	   userService 		 = (UserService) BcnxApplicationContext.getBean("userService");
	protected static BcnxSettleService bcnxSettleService = (BcnxSettleService) BcnxApplicationContext.getBean("bcnxSettleService");
	protected static ReasonCodeService reasonCodeService = (ReasonCodeService) BcnxApplicationContext.getBean("reasonCodeService");
	protected static DisputeTxnService disputeTxnService = (DisputeTxnService) BcnxApplicationContext.getBean("disputeTxnService");
	
	protected boolean checkIssuer(DisputeTxn txn, User user){
		String iss = txn.getIss();
		Member mem = user.getMember();
		boolean issChk = mem.getIin().equals(iss);
		boolean procChk = txn.getProcc().equals(CPRQ)||txn.getProcc().equals(CHB);
		return issChk&&procChk;
	}
	protected boolean checkAcquirer(DisputeTxn txn, User user){
		String acq = txn.getAcq();
		Member mem = user.getMember();
		boolean acqChk = mem.getIin().equals(acq);
		boolean procChk = txn.getProcc().equals(CPRP)||txn.getProcc().equals(ADJ)||txn.getProcc().equals(RPS);
		return acqChk&&procChk;
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
	protected String getFileData(List<InputPart> inputParts, String path){
		String fileName = "unkwonk";
		for (InputPart inputPart : inputParts) {
			 try {
				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);
				//convert the uploaded file to input stream
				InputStream inputStream = inputPart.getBody(InputStream.class,null);
				byte [] bytes = IOUtils.toByteArray(inputStream);
				//constructs upload file path
				String filePath = path +"/"+ fileName;
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
	// check file name
	protected boolean checkName(String fileName, User user, String rrn, String stan){
		String name=user.getMember().getIin()+rrn+stan+".zip";
		return fileName.equals(name);
	}
	// check valid date
	protected boolean checkValidDate(Date org){
		Date date = UtilityService.getCurrentDate();
		LocalDate start = new LocalDate(org);
		LocalDate end = new LocalDate(date);
		int valid = Days.daysBetween(start, end).getDays();
		return valid>30;
		
	}
}
