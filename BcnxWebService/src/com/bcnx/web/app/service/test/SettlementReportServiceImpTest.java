package com.bcnx.web.app.service.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bcnx.web.app.service.report.SettlementReportService;
import com.bcnx.web.app.utility.UtilityService;

public class SettlementReportServiceImpTest {

	@SuppressWarnings("resource")
	@Test
	public void testPublishSettlement() throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		SettlementReportService service = (SettlementReportService) context.getBean("settlementReportService");
		String d = "2015-02-13";
		Date date = UtilityService.str2Date(d);
		String iin = "198901";
		String path = "D:\\output\\Reports";
		service.publishSettlement(path, date, iin);
		assertTrue(true);
	}

}
