package com.bcnx.web.app.service.batch;

import java.sql.Date;
import java.util.List;

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
	private List<String> fileList;
	public void setFileTransfer(FileTransfer fileTransfer){
		this.fileTransfer = fileTransfer;
	}
	public void setBcnxSettleService(BcnxSettleService bcnxSettleService){
		this.bcnxSettleService = bcnxSettleService;
	}
	public List<String> getFileList(){
		return fileList;
	}
	@Override
	public RepeatStatus execute(StepContribution step, ChunkContext context)
			throws Exception {
		Date date = bcnxSettleService.getMaxDate();
		String sdate = UtilityService.date2Str(date);
		fileTransfer.setExpression("verify.log.*");
		fileList = fileTransfer.download(sdate);
		return RepeatStatus.FINISHED;
	}

}
