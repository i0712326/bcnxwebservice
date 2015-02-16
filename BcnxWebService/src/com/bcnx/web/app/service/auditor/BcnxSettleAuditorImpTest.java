package com.bcnx.web.app.service.auditor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bcnx.web.app.service.BcnxSettleService;
import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.utility.UtilityService;

public class BcnxSettleAuditorImpTest {
	
	@Test
	public void testDoWork() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		File file = new File("sample/verify.log.150113");
		java.sql.Date date = UtilityService.str2Date2("150113");
		BcnxSettleAuditor bean = (BcnxSettleAuditor) context.getBean("bcnxSettleAuditor");
		BcnxSettleService service = (BcnxSettleService) context.getBean("bcnxSettleService");
		try {
			List<BcnxSettle> list = bean.toList(file, date);
			System.out.println(list.size());
			service.saveAll(list);
			assertNotNull(list);
		} catch (IOException e) {
			fail("Exxception occured");
		}
	}
}
