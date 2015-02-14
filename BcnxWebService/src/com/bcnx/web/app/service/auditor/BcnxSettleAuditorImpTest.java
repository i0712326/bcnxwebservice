package com.bcnx.web.app.service.auditor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bcnx.web.app.service.entity.BcnxSettle;

public class BcnxSettleAuditorImpTest {
	
	@Test
	public void testDoWork() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		File file = new File("sample/verify.log.150210");
		java.sql.Date date = UtilityService.str2Date("150210");
		BcnxSettleAuditor bean = (BcnxSettleAuditor) context.getBean("bcnxSettleAuditor");
		try {
			List<BcnxSettle> list = bean.doWork(file, date);
			System.out.println(list.size());
			assertNotNull(list);
		} catch (IOException e) {
			fail("Exxception occured");
		}
	}
}
