package com.bcnx.web.app.service.auditor;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bcnx.web.app.service.BcnxTxnService;
import com.bcnx.web.app.service.entity.BcnxTxn;

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
