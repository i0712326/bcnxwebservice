package com.bcnx.web.app.service.task;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.bcnx.web.app.service.auditor.BcnxTxnAuditor;
import com.bcnx.web.app.service.entity.BcnxTxn;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class BatchJobTxn extends BatchJobTemplate {
	private static final Logger logger = Logger.getLogger(BatchJobTxn.class);
	private String expression;
	private BcnxTxnAuditor bcnxTxnAuditor;
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public void setBcnxTxnAuditor(BcnxTxnAuditor bcnxTxnAuditor){
		this.bcnxTxnAuditor = bcnxTxnAuditor;
	}
	@Override
	public List<String> get(String date) {
		super.fileTransfer.setExpression(expression);
		try {
			return super.fileTransfer.download(date);
		} catch (IOException | JSchException | SftpException e) {
			logger.debug("Exception occured while try to download router audit file", e);
			return null;
		}
	}
	List<BcnxTxn> bcnxTxns;
	@Override
	public void read(List<String> fileList) {
		for(String filePath : fileList){
			filePath = super.localPath+"/"+filePath;
			File file = new File(filePath);
			try {
				 bcnxTxns = bcnxTxnAuditor.toList(file);
			} catch (IOException e) {
				logger.debug("Exception occured while try to process router audit records", e);
			}
		}
	}

	@Override
	public void post() {
		//bcnxTxnAuditor.refine();
		super.bcnxTxnService.saveAll(bcnxTxns);
	}

}
