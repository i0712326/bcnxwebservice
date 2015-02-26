package com.bcnx.web.app.service.report;

import java.io.IOException;
import java.sql.Date;

import com.bcnx.web.app.service.entity.SettleBcnx;

public interface SettlementReportService {
	public void publishSettlement(String path,Date date, String id) throws IOException;
	public SettleBcnx getSettleBcnx();
}
