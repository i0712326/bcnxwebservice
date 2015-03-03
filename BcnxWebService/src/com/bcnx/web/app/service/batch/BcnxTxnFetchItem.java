package com.bcnx.web.app.service.batch;

import java.sql.Date;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.bcnx.web.app.service.BcnxSettleService;
import com.bcnx.web.app.service.task.FileTransfer;
import com.bcnx.web.app.utility.UtilityService;

public class BcnxTxnFetchItem implements Tasklet {
	private FileTransfer fileTransfer;
	private BcnxSettleService bcnxSettleService;
	public void setFileTransfer(FileTransfer fileTransfer){
		this.fileTransfer = fileTransfer;
	}
	public void setBcnxSettleService(BcnxSettleService bcnxSettleService){
		this.bcnxSettleService = bcnxSettleService;
	}
	@Override
	public RepeatStatus execute(StepContribution step, ChunkContext context)
			throws Exception {
		Date date = bcnxSettleService.getMaxDate();
		String sdate = UtilityService.date2Str(date);
		fileTransfer.download(sdate);
		return RepeatStatus.FINISHED;
	}

}
