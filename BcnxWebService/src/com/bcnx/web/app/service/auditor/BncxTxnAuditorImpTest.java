package com.bcnx.web.app.service.auditor;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BncxTxnAuditorImpTest {

	@Test
	public void testToBcnxTxn() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		BcnxTxnAuditor bean = (BcnxTxnAuditor) context.getBean("bcnxTxnAuditor");
		//BcnxTxnService service = (BcnxTxnService) context.getBean("bcnxTxnService");
		File file = new File("sample/router.audit.150214");
		try {
			bean.toList(file);
			bean.refine();
			assertTrue(true);
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
