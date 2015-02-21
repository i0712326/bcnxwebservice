package com.bcnx.web.app.service.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bcnx.web.app.service.task.BatchJob;

public class BcnxSettleScheduler extends QuartzJobBean {
	private BatchJob batchJob;
	public void setBatchJob(BatchJob batchJob){
		this.batchJob = batchJob;
	}
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		batchJob.doWork();
	}
}
