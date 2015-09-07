package com.bcnx.web.app.service.batch;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class BatchQuartzJobBean extends QuartzJobBean {
	private static final Logger logger = Logger.getLogger(BatchQuartzJobBean.class);
	private Job job;
	private JobLauncher jobLauncher;
	public void setJob(Job job) {
		this.job = job;
	}
	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
			jobParametersBuilder.addLong("time", System.currentTimeMillis());
			JobParameters jobParameters = jobParametersBuilder.toJobParameters();
			JobExecution execution = jobLauncher.run(job, jobParameters);
			logger.debug("Execution Exit Status : "+execution.getStatus());
		} catch (JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			logger.debug("Exception occured while try to execute batch job", e);
		}
	}

}
