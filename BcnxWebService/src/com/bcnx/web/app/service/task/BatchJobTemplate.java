package com.bcnx.web.app.service.task;

import java.sql.Date;
import java.util.List;

import com.bcnx.web.app.service.BcnxSettleService;
import com.bcnx.web.app.service.BcnxTxnService;
import com.bcnx.web.app.utility.UtilityService;

public abstract class BatchJobTemplate implements BatchJob{
	protected BcnxSettleService bcnxSettleService;
	protected BcnxTxnService bcnxTxnService;
	protected FileTransfer fileTransfer;
	protected String localPath;
	public void setLocalPath(String localPath){
		this.localPath = localPath;
	}
	public void setFileTransfer(FileTransfer fileTransfer){
		this.fileTransfer = fileTransfer;
	}
	public void setBcnxSettleService(BcnxSettleService bcnxSettleService){
		this.bcnxSettleService = bcnxSettleService;
	}
	public void setBcnxTxnService(BcnxTxnService bcnxTxnService){
		this.bcnxTxnService = bcnxTxnService;
	}
	public abstract List<String> get(String sDate);
	public abstract void read(List<String> fileList);
	public abstract void post();
	@Override
	public void doWork(){
		Date date = bcnxSettleService.getMaxDate();
		String sdate = UtilityService.date2Str(date);
		List<String> fileList = get(sdate);
		read(fileList);
		post();
	}
}
