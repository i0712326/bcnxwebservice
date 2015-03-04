package com.bcnx.web.app.service.report;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class NetSettlementReportTest {
	@SuppressWarnings("resource")
	@Test
	public void testPrintNetSettlementReport() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		NetSettlementReport bean = (NetSettlementReport) context.getBean("netSettlementReport");
		bean.printNetSettlementReport();
	}

}
