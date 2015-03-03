package com.bcnx.web.app.service.task.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bcnx.web.app.service.report.NetSettlementReport;

public class BatchJobGenReport extends QuartzJobBean {
	private NetSettlementReport netSettlementReport;
	public void setNetSettlementReport(NetSettlementReport netSettlementReport){
		this.netSettlementReport = netSettlementReport;
	}
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		netSettlementReport.printNetSettlementReport();
	}

}
