package com.bcnx.web.app.service.auditor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		File file = new File("sample/verify.log.150113");
		java.sql.Date date = str2Date("150113");
		BcnxSettleAuditor bean = (BcnxSettleAuditor) context.getBean("bcnxSettleAuditor");
		//BcnxSettleService bean1 = (BcnxSettleService) context.getBean("bcnxSettleService");
		try {
			List<BcnxSettle> list = bean.doWork(file, date);
			System.out.println(list.size());
			assertNotNull(list);
		} catch (IOException e) {
			fail("Exxception occured");
		}
	}
	private java.sql.Date str2Date(String str){
		SimpleDateFormat format = new SimpleDateFormat("yyMMDD");
		try {
			java.util.Date d = format.parse(str);
			return new java.sql.Date(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
