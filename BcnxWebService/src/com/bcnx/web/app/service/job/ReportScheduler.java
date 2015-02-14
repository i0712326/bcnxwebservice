package com.bcnx.web.app.service.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ReportScheduler extends QuartzJobBean {
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
	}

}
