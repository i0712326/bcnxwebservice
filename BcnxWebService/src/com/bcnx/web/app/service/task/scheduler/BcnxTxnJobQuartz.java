package com.bcnx.web.app.service.task.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bcnx.web.app.service.task.BatchJobTxn;

public class BcnxTxnJobQuartz extends QuartzJobBean {
	private BatchJobTxn batchJobTxn;
	public void setBatchJobTxn(BatchJobTxn batchJobTxn){
		this.batchJobTxn = batchJobTxn;
	}
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		batchJobTxn.doWork();
	}

}
