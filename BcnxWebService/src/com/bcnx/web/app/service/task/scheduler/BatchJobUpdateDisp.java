package com.bcnx.web.app.service.task.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bcnx.web.app.service.task.BatchDispUpdate;

public class BatchJobUpdateDisp extends QuartzJobBean {
	private BatchDispUpdate batchDispUpdate;
	public void setBatchDispUpdate(BatchDispUpdate batchDispUpdate){
		this.batchDispUpdate = batchDispUpdate;
	}
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		batchDispUpdate.doWork();
	}

}
