package com.bcnx.web.app.service.batch;

import java.sql.Date;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.bcnx.web.app.service.task.FileTransfer;
import com.bcnx.web.app.utility.UtilityService;

public class BcnxFileFetch extends BatchTemplate implements Tasklet {
	private FileTransfer fileTransfer;
	private String expression;
	private String localPath;
	public void setExpression(String expression){
		this.expression = expression;
	}
	public void setLocalPath(String localPath){
		this.localPath = localPath;
	}
	public void setFileTransfer(FileTransfer fileTransfer){
		this.fileTransfer = fileTransfer;
	}
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		Date date = super.getMaxDate();
		String sdate = UtilityService.date2Str(date);
		fileTransfer.setExpression(expression);
		fileTransfer.setLocalPath(localPath);
		fileTransfer.download(sdate);
		return RepeatStatus.CONTINUABLE;
	}
}
