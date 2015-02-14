package com.bcnx.web.app.service.task;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.bcnx.web.app.service.auditor.BcnxSettleAuditor;
import com.bcnx.web.app.service.auditor.UtilityService;
import com.bcnx.web.app.service.entity.BcnxSettle;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class BatchJobSettle extends BatchJobTemplate {
	private static final Logger logger = Logger.getLogger(BatchJobSettle.class);
	private BcnxSettleAuditor bcnxSettleAuditor;
	private String expression;
	public void setExpression(String expression){
		this.expression=expression;
	}
	public void setBcnxSettleAuditor(BcnxSettleAuditor bcnxSettleAuditor){
		this.bcnxSettleAuditor = bcnxSettleAuditor;
	}
	@Override
	public List<String> get(String sDate) {
		super.fileTransfer.setExpression(expression);
		try {
			return super.fileTransfer.download(sDate);
		} catch (IOException | JSchException | SftpException e) {
			logger.debug("Exception occured while try to download settle", e);
			return null;
		}
	}
	List<BcnxSettle> settles;
	@Override
	public void read(List<String> fileList) {
		for(String fileName : fileList){
			String filePath = super.localPath+"/"+fileName;
			File file = new File(filePath);
			String name = file.getName().substring(11);
			Date date = UtilityService.str2Date2(name);
			try {
				settles = bcnxSettleAuditor.doWork(file, date);
			} catch (IOException e) {
				logger.debug("Exception occured while try to get settle records", e);
			}
		}
	}
	@Override
	public void post() {
		super.bcnxSettleService.saveAll(settles);
	}

}
