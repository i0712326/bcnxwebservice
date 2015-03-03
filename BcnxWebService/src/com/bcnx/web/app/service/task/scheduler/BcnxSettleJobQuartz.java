package com.bcnx.web.app.service.task.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bcnx.web.app.service.task.BatchJobSettle;

public class BcnxSettleJobQuartz extends QuartzJobBean {
	private BatchJobSettle batchJobSettle;
	public void setBatchJobSettle(BatchJobSettle batchJobSettle){
		this.batchJobSettle = batchJobSettle;
	}
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		batchJobSettle.doWork();
	}

}
