package com.bcnx.web.app.service.report;

import java.io.IOException;
import java.sql.Date;

public interface SettlementReportService {
	public void publishSettlement(String path,Date date, String id) throws IOException;
	public double getNet();
}
