package com.bcnx.web.app.service.batch;

import java.sql.Date;

import com.bcnx.web.app.service.BcnxSettleService;
import com.bcnx.web.app.service.BcnxTxnService;
import com.bcnx.web.app.service.task.FileTransfer;

public class BatchTemplate {
	protected BcnxSettleService bcnxSettleService;
	protected BcnxTxnService bcnxTxnService;
	protected FileTransfer fileTransfer;
	public void setFileTransfer(FileTransfer fileTransfer){
		this.fileTransfer = fileTransfer;
	}
	public void setBcnxSettleService(BcnxSettleService bcnxSettleService){
		this.bcnxSettleService = bcnxSettleService;
	}
	public void setBcnxTxnService(BcnxTxnService bcnxTxnService){
		this.bcnxTxnService = bcnxTxnService;
	}
	public Date getMaxDate(){
		return bcnxSettleService.getMaxDate();
	}
}
