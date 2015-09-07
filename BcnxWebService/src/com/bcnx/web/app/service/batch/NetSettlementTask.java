package com.bcnx.web.app.service.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.bcnx.web.app.service.report.NetSettlementReport;

public class NetSettlementTask implements Tasklet {
	private NetSettlementReport netSettlementReport;
	public void setNetSettlementReport(NetSettlementReport netSettlementReport) {
		this.netSettlementReport = netSettlementReport;
	}
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		netSettlementReport.printNetSettlementReport();
		return RepeatStatus.CONTINUABLE;
	}

}
